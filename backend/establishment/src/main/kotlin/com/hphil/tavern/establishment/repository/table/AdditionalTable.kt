package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable

object AdditionalTable : LongIdTable("additional") {
    val asset = reference("asset_id", AssetTable)
    val name = text("name")
    val price = decimal("price", 16, 4)
}
