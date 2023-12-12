package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable

object IngredientTable : LongIdTable("ingredient") {
    val asset = reference("asset_id", AssetTable)
    val name = text("name")
    val removable = bool("removable")
}
