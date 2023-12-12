package com.hphil.tavern.establishment.controller.customer

import com.hphil.tavern.establishment.client.getAssetByHash
import com.hphil.tavern.establishment.client.getSpotByHash
import com.hphil.tavern.establishment.repository.entity.OrderItemEntity
import com.hphil.tavern.establishment.repository.entity.OrderLotEntity
import com.hphil.tavern.establishment.repository.reference.*
import com.hphil.tavern.establishment.services.QueuePublisher
import com.hphil.tavern.establishment.services.getCustomerBill
import com.hphil.tavern.establishment.utils.getContextAppAttribute
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Every action available here is supposed to be accessed by the customer
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
fun customerGetOrders(ctx: Context) {
    transaction {
        val bill = ctx.getCustomerBill()
        ctx.json(MyOrdersResponse(
            OrderItemEntity.findAllByBill(bill)
                .groupBy { it.orderLot }
                .map { lot ->
                    OrderLotDto(lot.key.createdAt.atZone(ZoneId.systemDefault()), lot.value.map {
                        OrderItemDto(it.asset.name, it.quantity, it.finalValue, it.status.toString())
                    })
                }
        ))
    }
}

fun customerPlaceOrder(ctx: Context) {
    val requestIncoming = ctx.bodyAsClass(IncomingOrderRequest::class.java)
    val queuePublisher = ctx.getContextAppAttribute(QueuePublisher::class)
    transaction {
        val bill = ctx.getCustomerBill()
        val spot = getSpotByHash(ctx, bill.register.establishmentHash, requestIncoming.spotHash)
            ?: error("Spot does not exist")
        val order = OrderLotEntity.new(bill, SpotReference(requestIncoming.spotHash, spot.name, spot.number))
        requestIncoming.items.forEach { saveOderItems(ctx, it, order) }
        queuePublisher.orderReceived(order)
    }
}

private fun saveOderItems(context: Context, item: IncomingOrderItemDto, orderLot: OrderLotEntity) {
    val asset = getAssetByHash(context, orderLot.bill.register.establishmentHash, item.assetHash)
        ?: error("Asset ${item.assetHash} does not exist")

    val assetReference = AssetReference(
        asset.hashId,
        asset.name,
        CategoryReference(asset.category.hashId, asset.category.name),
        asset.price,
        item.removedIngredientsHashes.map { ri ->
            val ingredient = asset.ingredients.find { it.hashId == ri }
                ?: error("Removable ingredient with hashId $ri does not exist")
            IngredientReference(ingredient.hashId, ingredient.name)
        },
        item.additionalsHashes.map { ad ->
            val additional = asset.additionals.find { it.hashId == ad }
                ?: error("Additional ingredient with hashId $ad does not exist")
            AdditionalReference(additional.hashId, additional.name, additional.price)
        }
    )
    assert(item.finalValue == (assetReference.finalPrice * item.quantity.toBigDecimal())) {
        "The evaluated final price differs from value expected by client"
    }

    OrderItemEntity.new(orderLot, assetReference, item.quantity)
}


private class MyOrdersResponse(val items: List<OrderLotDto>) {
    val total: BigDecimal
        get() = items.map { it.total }.fold(BigDecimal.ZERO, BigDecimal::add)
}

private class OrderLotDto(val createdAt: ZonedDateTime, val items: List<OrderItemDto>) {
    val total: BigDecimal
        get() = items.map { it.quantity.toBigDecimal() * it.totalPrice }.fold(BigDecimal.ZERO, BigDecimal::add)
}

private class OrderItemDto(val assetName: String, val quantity: Int, val totalPrice: BigDecimal, val status: String)

private class IncomingOrderRequest(
    val spotHash: String,
    val items: Set<IncomingOrderItemDto>
)

private class IncomingOrderItemDto(
    val assetHash: String,
    val removedIngredientsHashes: List<String>,
    val additionalsHashes: List<String>,
    val quantity: Int,
    val finalValue: BigDecimal
)
