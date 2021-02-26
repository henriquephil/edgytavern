package com.hphil.resources

import com.hphil.client.ManagementEstablishmentClient
import com.hphil.domain.Bill
import com.hphil.domain.OrderItem
import com.hphil.domain.OrderItemStatus
import com.hphil.repository.BillRepository
import com.hphil.repository.OrderItemRepository
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.annotation.security.RolesAllowed
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

/**
 * Every action available here are supposed to be called by the manager
 *
 * The manager can:
 * . Read all of the active bills, orders and order items in his establishment
 * . Read an specific bill, detailed
 * . Read an specific OrderItem, detailed
 * . Progress the ordered item trough the operation
 * . Remove an order item
 * . Close the bill
 *
 */
@Path("/manager")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("manager")
class ManagerResource {
    @Inject
    lateinit var billRepository: BillRepository

    @Inject
    lateinit var orderItemRepository: OrderItemRepository

    @Inject
    @field: Default
    @RestClient
    lateinit var managementEstablishmentClient: ManagementEstablishmentClient

    @GET
    @Path("/bills")
    fun getBills(ctx: SecurityContext): ManagerBillsResponse {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        return ManagerBillsResponse(billRepository.openBillsAt(establishment.hashId)
                .map { bill ->
                    BillDto(
                            bill.id!!,
                            bill.userName,
                            bill.orders.flatMap { it.items }.map {
                                SimpleOrderItemDto(it.asset.name, it.quantity, it.finalPrice)
                            }
                    )
                })
    }

    @GET
    @Path("/bills/{id}")
    fun getBill(ctx: SecurityContext, @PathParam("id") id: Long): ManagerBillResponse {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        val bill = billRepository.find("establishment_hash = ?1 and id = ?2", establishment.hashId, id).firstResult<Bill>()
        return ManagerBillResponse(
                bill.id!!,
                bill.userName,
                bill.open,
                bill.started,
                bill.ended,
                bill.orders.map { order ->
                    OrderDto(
                            order.spot.name,
                            order.time,
                            order.items.map { item ->
                                OrderItemDto(
                                        item.id!!,
                                        item.status.toString(),
                                        item.asset.name,
                                        item.asset.category.name,
                                        item.removedIngredients.map { it.name },
                                        item.requestedAdditionals.map { it.name },
                                        item.quantity,
                                        item.unitPrice,
                                        item.finalPrice
                                )
                            }
                    )
                }
        )
    }

    @POST
    @Path("/bills/{billId}/close")
    @Transactional
    fun closeBill(@Context ctx: SecurityContext, @PathParam("billId") billId: Long) {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        val bill = billRepository.find("establishment_hash = ?1 and id = ?2", establishment.hashId, billId).firstResult<Bill>()
        bill.close()
    }

    @POST
    @Path("/orderItems/{orderItemId}/shift")
    @Transactional
    fun shiftItem(@Context ctx: SecurityContext, @PathParam("orderItemId") orderItemId: Long) {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        val orderItem = orderItemRepository.findByEstablishmentAndId(establishment.hashId, orderItemId).firstResult<OrderItem>()
        orderItem.status = mapOf(
                OrderItemStatus.RECEIVED to OrderItemStatus.SEPARATION,
                OrderItemStatus.SEPARATION to OrderItemStatus.PREPARATION,
                OrderItemStatus.PREPARATION to OrderItemStatus.DISPATCHED,
                OrderItemStatus.DISPATCHED to OrderItemStatus.DELIVERED
        )[orderItem.status] ?: error("Item has no other status to go to")
    }

    @DELETE
    @Path("/orderItems/{orderItemId}")
    @Transactional
    fun removeItem(@Context ctx: SecurityContext, @PathParam("orderItemId") orderItemId: Long) {
        val establishment = managementEstablishmentClient.getManagersEstablishment()
        val orderItem = orderItemRepository.findByEstablishmentAndId(establishment.hashId, orderItemId).firstResult<OrderItem>()
        orderItemRepository.delete(orderItem)
    }

    class ManagerBillsResponse(val bills: List<BillDto>)
    class BillDto(val id: Long, val customerName: String, val orders: List<SimpleOrderItemDto>)
    class SimpleOrderItemDto(val assetName: String, val quantity: Int, val finalPrice: BigDecimal)

    class ManagerBillResponse(val id: Long,
                              val customerName: String,
                              var open: Boolean,
                              val started: LocalDateTime,
                              var ended: LocalDateTime?,
                              val orders: List<OrderDto>)

    class OrderDto(val spotName: String,
                   val time: LocalDateTime,
                   val items: List<OrderItemDto>)

    class OrderItemDto(val id: Long,
                       val status: String,
                       val assetName: String,
                       val assetCategoryName: String,
                       val removedIngredients: List<String>,
                       val requestedAdditionals: List<String>,
                       val quantity: Int,
                       val unitPrice: BigDecimal,
                       val finalPrice: BigDecimal)
}
