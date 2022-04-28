package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.client.EstablishmentClient
import com.hphil.tavern.bills.domain.Order
import com.hphil.tavern.bills.domain.OrderItem
import com.hphil.tavern.bills.domain.references.*
import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderItemRepository
import com.hphil.tavern.bills.repository.OrderRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.security.Principal

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
@RestController
@RequestMapping("/orders")
class OrdersController(
    private val billRepository: BillRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val establishmentClient: EstablishmentClient
) : CustomerTrait {

    @GetMapping
    fun getOrders(principal: Principal): MyOrdersResponse {
        val bill = fetchOpenBill(billRepository, principal)
        return MyOrdersResponse(
            orderItemRepository.findAllByOrderBill(bill).map {
                OrderItemDto(it.asset.name, it.quantity, it.totalPrice, it.status.toString())
            }
        )
    }

    @PostMapping
    @Transactional
    fun addOrder(@RequestBody requestIncoming: IncomingOrderRequest, principal: Principal) {
        val bill = fetchOpenBill(billRepository, principal)
        val spot = establishmentClient.getSpotByHash(bill.establishmentHash, requestIncoming.spotHash)
            ?: error("Spot does not exist")
        val order = orderRepository.save(
            Order(bill, SpotReference(requestIncoming.spotHash, spot.name, spot.number))
        )
        requestIncoming.items.forEach { mapNewAssets(it, order) }
        // TODO notify
    }

    private fun mapNewAssets(item: IncomingOrderItemDto, order: Order) {
        val asset = establishmentClient.getAssetByHash(order.bill.establishmentHash, item.assetHashId)
            ?: error("Asset ${item.assetHashId} does not exist")
        assert(asset.ingredients.map { it.hashId }.containsAll(item.removedIngredientsHashIds))
        assert(asset.additionals.map { it.hashId }.containsAll(item.additionalsHashIds))

        val orderItem = OrderItem(
            order,
            AssetReference(
                asset.hashId, asset.name, CategoryReference(asset.category.hashId, asset.category.name), asset.price,
                item.removedIngredientsHashIds.map { ri ->
                    val ingredient = asset.ingredients.find { it.hashId == ri }
                        ?: error("Removable ingredient with hashId $ri does not exist")
                    IngredientReference(ingredient.hashId, ingredient.name)
                },
                item.additionalsHashIds.map { ad ->
                    val additional = asset.additionals.find { it.hashId == ad }
                        ?: error("Additional ingredient with hashId $ad does not exist")
                    AdditionalReference(additional.hashId, additional.name, additional.price)
                }
            ),
            item.quantity
        )
        // no surprises of different values from what client previewed and what is being persisted
        assert(item.totalPrice == orderItem.totalPrice) { "The evaluated final price differs from value expected by client" }
        orderItemRepository.save(orderItem)
    }
}

class MyOrdersResponse(val orderedItems: List<OrderItemDto>)
class OrderItemDto(val assetName: String, val quantity: Int, val totalPrice: BigDecimal, val status: String)

class IncomingOrderRequest(val spotHash: String, val items: Set<IncomingOrderItemDto>)
class IncomingOrderItemDto(
    val assetHashId: String,
    val removedIngredientsHashIds: List<String>,
    val additionalsHashIds: List<String>,
    val quantity: Int,
    val totalPrice: BigDecimal
)
