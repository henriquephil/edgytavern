package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable

object AssetTable : LongIdTable("asset") {
    val establishment = reference("establishment_id", EstablishmentTable)
    val name = text("name")
    val category = reference("category_id", CategoryTable)
    val price = decimal("price", 16, 4)
    val description = text("description").nullable()
    val active = bool("active")
    val order = integer("order").default(0)
}
