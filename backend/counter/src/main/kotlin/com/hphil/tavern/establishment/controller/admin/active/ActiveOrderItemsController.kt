package com.hphil.tavern.establishment.controller.admin.active

import com.hphil.tavern.establishment.enums.OrderItemStatus
import com.hphil.tavern.establishment.repository.entity.OrderItemEntity
import com.hphil.tavern.establishment.repository.entity.OrderLotEntity
import com.hphil.tavern.establishment.repository.reference.AssetReference
import com.hphil.tavern.establishment.services.getCurrentRegister
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId
import java.time.ZonedDateTime

fun shiftOrderItem(ctx: Context) {
    val orderItemId = ctx.pathParamAsClass("orderItemId", Long::class.java).get()
    transaction {
        val register = ctx.getCurrentRegister()
        val orderItem = OrderItemEntity.findByRegisterAndId(register, orderItemId)
        // what about findById and validate it later to make the query simpler?
        orderItem.status = orderItem.status.next
    }
}

fun cancelOrderItem(ctx: Context) {
    val orderItemId = ctx.pathParamAsClass("orderItemId", Long::class.java).get()
    transaction {
        val register = ctx.getCurrentRegister()
        val orderItem = OrderItemEntity.findByRegisterAndId(register, orderItemId)
        orderItem.status = OrderItemStatus.CANCELED
    }
}

fun getActiveRegisterOrderItemList(ctx: Context) {
    val since = ctx.queryParamAsClass("since", ZonedDateTime::class.java).allowNullable().get()
    transaction {
        val register = ctx.getCurrentRegister()
        val orderItems = when (since) {
            null -> OrderLotEntity.findAllByRegister(register)
            else -> OrderLotEntity.findAllByRegisterAndCreatedAtGreaterThan(register, since.toLocalDateTime())
        }
        ctx.json(RecentOrdersResponse(
            orderItems.flatMap { lot ->
                OrderItemEntity.findAllByOrderLot(lot).map { RecentOrdersDto(lot, it) }
            }
        ))
    }
}

private class RecentOrdersResponse(val items: List<RecentOrdersDto>) {
    val timestamp: ZonedDateTime = ZonedDateTime.now()
}

private class RecentOrdersDto(
    val id: Long,
    val asset: AssetDto,
    val quantity: Int,
    val status: String,
    val spotName: String,
    val spotNumber: Int,
    val createdAt: ZonedDateTime,
    val tabCode: String
) {
    constructor(lot: OrderLotEntity, item: OrderItemEntity) : this(
        item.id.value,
        AssetDto(item.asset),
        item.quantity,
        item.status.name,
        lot.spot.name,
        lot.spot.number,
        lot.createdAt.atZone(ZoneId.systemDefault()),
        lot.bill.tabCode
    )
}

private class AssetDto(
    val name: String,
    val additionals: List<String>,
    val removed: List<String>
) {
    constructor(asset: AssetReference) : this(
        asset.name,
        asset.requestedAdditionals.map { it.name },
        asset.removedIngredients.map { it.name }
    )
}