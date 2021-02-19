package com.hphil.resources

import com.hphil.client.EstablishmentClient
import com.hphil.domain.*
import com.hphil.repository.BillRepository
import com.hphil.repository.OrderRepository
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal
import io.quarkus.security.Authenticated
import org.hashids.Hashids
import java.math.BigDecimal
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

/**
 * Every action available here is supposed to be accesses by the customer
 *
 * The first thing needed by the customer is to start a new Bill at some establishment
 * He will be able to do that with the QRCode link containing the hashes for his location
 *
 * With an open Bill, he can start browsing the catalog/menu and adding orders to some spot
 * The customer can cancel his Bill, if no order has been ordered
 *
 * He will be able de read only his active bill
 *
 */
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("customer")
class CustomerResource {
    @Inject
    lateinit var billRepository: BillRepository

    @Inject
    lateinit var orderRepository: OrderRepository

    @Inject
    lateinit var hashids: Hashids

    @Inject
    lateinit var establishmentClient: EstablishmentClient

    @POST
    @Path("/bill")
    @Transactional
    fun openBill(@Context ctx: SecurityContext, request: OpenBillRequest): OpenBillResponse {
        val principal = (ctx.userPrincipal as OidcJwtCallerPrincipal)
        billRepository.findOpen(principal.subject).ifPresent { error("There is already an active bill") }
        // fetch the spot to assert these are real hashes
        val establishment = establishmentClient.getEstablishmentByHash(request.establishmentHash)
                ?: error("Establishment does not exist")
        val bill = Bill(principal.subject, principal.name, request.establishmentHash)
        billRepository.persist(bill)
        return OpenBillResponse(hashids.encode(bill.id!!))
        // TODO notify
    }

    private fun fetchOpenBill(ctx: SecurityContext): Bill =
            billRepository.findOpen((ctx.userPrincipal as OidcJwtCallerPrincipal).subject)
                    .orElseThrow { error("User has no active bill") }

    @DELETE // Maybe there would be better something like POST /leave
    @Path("/bill")
    @Transactional
    fun cancelBill(@Context ctx: SecurityContext) {
        val bill = fetchOpenBill(ctx)
        if (orderRepository.count("bill_id", bill.id) > 0)
            error("Bill with orders can only be closed by the manager")
        billRepository.delete(bill)
        // TODO notify
    }

    @GET
    @Path("/bill")
    fun myBill(@Context ctx: SecurityContext): SpotBillResponse {
        val bill = fetchOpenBill(ctx)
        return SpotBillResponse(
                orderRepository.findAll(bill).flatMap { it.items }.map {
                    SpotBillOrderItemDto(it.asset.name, it.quantity, it.unitPrice, it.status.toString())
                }
        )
    }

    @POST
    @Path("/bill/orders")
    @Transactional
    fun addOrder(@Context ctx: SecurityContext, request: OrderRequest) {
        val bill = fetchOpenBill(ctx)
        val spot = establishmentClient.getSpotByHash(bill.establishmentHash, request.spotHash)
                ?: error("Spot does not exist")
        val order = Order(bill, Spot(request.spotHash, spot.name))
        order.items.addAll(request.items.map { item ->
            val asset = establishmentClient.getAssetByHash(bill.establishmentHash, item.assetHashId)
                    ?: error("Asset ${item.assetHashId} does not exist")
            assert(asset.ingredients.map { it.hashId }.containsAll(item.removedIngredientsHashIds))
            assert(asset.additionals.map { it.hashId }.containsAll(item.additionalsHashIds))
            var unitPrice = asset.price
            val requestedAdditionals = item.additionalsHashIds.map { ad ->
                val additional = asset.additionals.find { it.hashId == ad }!!
                unitPrice += additional.price;
                Additional(additional.hashId, additional.name)
            }
            val orderItem = OrderItem(
                    order,
                    Asset(asset.hashId, asset.name, Category(asset.category.hashId, asset.category.name)),
                    item.removedIngredientsHashIds.map { ri ->
                        val ingredient = asset.ingredients.find { it.hashId == ri }!!
                        Ingredient(ingredient.hashId, ingredient.name)
                    },
                    requestedAdditionals,
                    item.quantity,
                    unitPrice
            )
            // validate final price, so no surprises of different values from client
            assert(item.finalPrice == orderItem.finalPrice) { "The evaluated final price differs from value expected by client" }
            orderItem
        })
        orderRepository.persist(order)
        // TODO notify
    }

    class SpotBillResponse(val orderedItems: List<SpotBillOrderItemDto>)
    class SpotBillOrderItemDto(val assetName: String, val quantity: Int, val finalPrice: BigDecimal, val status: String)

    class OpenBillRequest(val establishmentHash: String)
    class OpenBillResponse(val hashId: String)

    class OrderRequest(val spotHash: String, val items: Set<OrderItemDto>)
    class OrderItemDto(val assetHashId: String, val removedIngredientsHashIds: List<String>, val additionalsHashIds: List<String>, val quantity: Int, val finalPrice: BigDecimal)
}
