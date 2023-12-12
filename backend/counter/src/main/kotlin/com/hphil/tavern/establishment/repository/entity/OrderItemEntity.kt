package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.enums.OrderItemStatus
import com.hphil.tavern.establishment.repository.reference.AssetReference
import com.hphil.tavern.establishment.repository.table.BillTable
import com.hphil.tavern.establishment.repository.table.OrderItemTable
import com.hphil.tavern.establishment.repository.table.OrderLotTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import java.math.BigDecimal

class OrderItemEntity(id: EntityID<Long>) : LongEntity(id) {
    var orderLot by OrderLotEntity referencedOn OrderItemTable.orderLot
    var asset by OrderItemTable.asset
    var quantity by OrderItemTable.quantity
    var status by OrderItemTable.status

    val finalValue: BigDecimal get() = asset.finalPrice * quantity.toBigDecimal()

    companion object : LongEntityClass<OrderItemEntity>(OrderItemTable) {
        fun new(orderLot: OrderLotEntity, assetReference: AssetReference, quantity: Int) = new {
            this.orderLot = orderLot
            this.asset = assetReference
            this.quantity = quantity
            this.status = OrderItemStatus.RECEIVED
        }

        // by order lot
        fun findAllByOrderLot(order: OrderLotEntity) = find {
            OrderItemTable.orderLot.eq(order.id)
        }.toList()

        // by bill
        fun findAllByBill(bill: BillEntity) = find {
            OrderItemTable.orderLot.inSubQuery(
                OrderLotEntity.searchQuery(OrderLotTable.bill.eq(bill.id))
            )
        }.toList()

        fun countNotDeliveredByBill(bill: BillEntity) = find {
            OrderItemTable.orderLot.inSubQuery(
                OrderLotEntity.searchQuery(OrderLotTable.bill.eq(bill.id))
            ) and (OrderItemTable.status.inList(listOf(OrderItemStatus.RECEIVED,  OrderItemStatus.PREPARATION, OrderItemStatus.DISPATCHED)))
        }.count()

        // by register
        fun findByRegisterAndId(register: RegisterEntity, orderItemId: Long) = find {
            OrderItemTable.orderLot.inSubQuery(
                OrderLotEntity.searchQuery(
                    OrderLotTable.bill.inSubQuery(
                        BillEntity.searchQuery(BillTable.register.eq(register.id))
                    )
                )
            ) and (OrderItemTable.id eq orderItemId)
        }.first()

    }
}
