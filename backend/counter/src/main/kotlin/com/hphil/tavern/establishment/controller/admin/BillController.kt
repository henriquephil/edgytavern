package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.enums.OrderItemStatus
import com.hphil.tavern.establishment.repository.entity.BillEntity
import com.hphil.tavern.establishment.repository.entity.OrderItemEntity
import com.hphil.tavern.establishment.repository.entity.OrderLotEntity
import com.hphil.tavern.establishment.services.getAdminEstablishment
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime

fun adminGetBill(ctx: Context) {
    val id = ctx.pathParamAsClass("billId", Long::class.java).get()
    val establishment = ctx.getAdminEstablishment()
    transaction {
        val bill = BillEntity.findById(establishment.hashId, id)
        ctx.json(mapManagerBillResponse(bill))
    }
}

fun getRegisterBills(ctx: Context) {
    val registerId: Long = ctx.pathParamAsClass("registerId", Long::class.java).get()
    val establishment = ctx.getAdminEstablishment()
    transaction {
        val bills = BillEntity.findAllByRegister(establishment.hashId, registerId)
        ctx.json(bills.map { mapManagerBillResponse(it) })
    }
}

private fun mapManagerBillResponse(bill: BillEntity) =
    ManagerBillDto(
        bill.id.value,
        bill.tabCode,
        bill.open,
        bill.started.atZone(ZoneId.systemDefault()),
        bill.ended?.atZone(ZoneId.systemDefault()),
        OrderLotEntity.findByBill(bill).map { order ->
            OrderDto(
                order.spot.name,
                order.createdAt.atZone(ZoneId.systemDefault()),
                OrderItemEntity.findAllByOrderLot(order).map { item ->
                    OrderItemDto(
                        item.id.value,
                        item.status,
                        item.asset.name,
                        item.asset.category.name,
                        item.asset.removedIngredients.map { it.name },
                        item.asset.requestedAdditionals.map { it.name },
                        item.asset.finalPrice,
                        item.quantity,
                        item.finalValue
                    )
                }
            )
        }
    )

private class ManagerBillDto(
    val id: Long,
    val tabCode: String,
    var open: Boolean,
    val started: ZonedDateTime,
    var ended: ZonedDateTime?,
    val orders: List<OrderDto>
) {
    val total: BigDecimal
        get() = orders.flatMap { it.items }
            .filter { it.status != OrderItemStatus.CANCELED }
            .map { it.totalPrice }
            .fold(BigDecimal.ZERO, BigDecimal::add)
}

private class OrderDto(
    val spotName: String,
    val createdAt: ZonedDateTime,
    val items: List<OrderItemDto>
)

private class OrderItemDto(
    val id: Long,
    val status: OrderItemStatus,
    val assetName: String,
    val assetCategoryName: String,
    val removedIngredients: List<String>,
    val requestedAdditionals: List<String>,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val totalPrice: BigDecimal
)



