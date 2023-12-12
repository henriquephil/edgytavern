package com.hphil.tavern.establishment.repository.table

import com.hphil.tavern.establishment.enums.OrderItemStatus
import com.hphil.tavern.establishment.utils.json
import com.hphil.tavern.establishment.repository.reference.AssetReference
import org.jetbrains.exposed.dao.id.LongIdTable

object OrderItemTable : LongIdTable("order_item") {
    val orderLot = reference("order_lot_id", OrderLotTable)
    val asset = json("asset", AssetReference::class)
    val quantity = integer("quantity")
    val status = enumerationByName("status", 31, OrderItemStatus::class)
//    val finalValue = decimal("finalValue", 12, 4)
}
