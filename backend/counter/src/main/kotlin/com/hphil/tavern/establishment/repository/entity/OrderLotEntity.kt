package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.reference.SpotReference
import com.hphil.tavern.establishment.repository.table.BillTable
import com.hphil.tavern.establishment.repository.table.OrderLotTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class OrderLotEntity(id: EntityID<Long>) : LongEntity(id) {
    var bill by BillEntity referencedOn OrderLotTable.bill
    var spot by OrderLotTable.spot
    var createdAt by OrderLotTable.createdAt

    companion object : LongEntityClass<OrderLotEntity>(OrderLotTable) {
        fun new(bill: BillEntity, spotReference: SpotReference) = new {
            this.bill = bill
            spot = spotReference
            createdAt = LocalDateTime.now()
        }

        // by bill
        fun countByBill(bill: BillEntity) = find { OrderLotTable.bill eq bill.id }.count()
        fun findByBill(bill: BillEntity) = find { OrderLotTable.bill eq bill.id }.toList()

        // by register
        fun findAllByRegister(register: RegisterEntity) = find {
            OrderLotTable.bill.inSubQuery(
                BillTable.slice(BillTable.id).select(BillTable.register.eq(register.id))
            )
        }.toList()

        fun findAllByRegisterAndCreatedAtGreaterThan(register: RegisterEntity, since: LocalDateTime) = find {
            OrderLotTable.bill.inSubQuery(
                BillEntity.searchQuery(BillTable.register.eq(register.id))
            ) and (OrderLotTable.createdAt greaterEq since)
        }.toList()
    }
}
