package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.client.EstablishmentClient
import com.hphil.tavern.bills.domain.OrderItem
import com.hphil.tavern.bills.domain.OrderLot
import com.hphil.tavern.bills.domain.references.*
import com.hphil.tavern.bills.repository.BillRepository
import com.hphil.tavern.bills.repository.OrderItemRepository
import com.hphil.tavern.bills.repository.OrderLotRepository
import com.hphil.tavern.bills.services.QueuePublisher
import com.hphil.tavern.bills.services.security.UserInfo
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

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
    private val orderLotRepository: OrderLotRepository,
    private val orderItemRepository: OrderItemRepository,
    private val establishmentClient: EstablishmentClient,
    private val queuePublisher: QueuePublisher
) : CustomerTrait {

    @GetMapping
    fun getOrders(userInfo: UserInfo): MyOrdersResponse {
        val bill = fetchOpenBill(billRepository, userInfo)
        return MyOrdersResponse(
            orderItemRepository.findAllByOrderLotBill(bill).map {
                OrderItemDto(it.asset.name, it.quantity, it.finalValue, it.status.toString())
            }
        )
    }

    @PostMapping
    @Transactional
    fun addOrder(@RequestBody requestIncoming: IncomingOrderRequest, userInfo: UserInfo) {
        val bill = fetchOpenBill(billRepository, userInfo)
        val spot = establishmentClient.getSpotByHash(bill.register.establishmentHash, requestIncoming.spotHash)
            ?: error("Spot does not exist")
        val order = orderLotRepository.save(
            OrderLot(bill, SpotReference(requestIncoming.spotHash, spot.name, spot.number))
        )
        requestIncoming.items.forEach { mapNewAssets(it, order) }
        queuePublisher.orderReceived(order)
    }

    private fun mapNewAssets(item: IncomingOrderItemDto, orderLot: OrderLot) {
        val asset = establishmentClient.getAssetByHash(orderLot.bill.register.establishmentHash, item.assetHashId)
            ?: error("Asset ${item.assetHashId} does not exist")

        val orderItem = OrderItem(
            orderLot,
            AssetReference(
                asset.hashId,
                asset.name,
                CategoryReference(asset.category.hashId, asset.category.name),
                asset.price,
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
        assert(item.finalValue == orderItem.finalValue) { "The evaluated final price differs from value expected by client" }
        orderItemRepository.save(orderItem)
    }
}

class MyOrdersResponse(val items: List<OrderItemDto>) {
    val totalPrice = items.map { it.quantity.toBigDecimal() * it.totalPrice }.fold(BigDecimal.ZERO, BigDecimal::add)
}
class OrderItemDto(val assetName: String, val quantity: Int, val totalPrice: BigDecimal, val status: String)

class IncomingOrderRequest(val spotHash: String, val items: Set<IncomingOrderItemDto>)
class IncomingOrderItemDto(
    val assetHashId: String,
    val removedIngredientsHashIds: List<String>,
    val additionalsHashIds: List<String>,
    val quantity: Int,
    val finalValue: BigDecimal
)
