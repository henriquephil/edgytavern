package com.hphil.tavern.establishment.repository.table

import com.hphil.tavern.establishment.utils.json
import com.hphil.tavern.establishment.repository.reference.SpotReference
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object OrderLotTable : LongIdTable("order_lot") {
    val bill = reference("bill_id", BillTable)
    val spot = json("spot", SpotReference::class)
    val createdAt = datetime("created_at")
}