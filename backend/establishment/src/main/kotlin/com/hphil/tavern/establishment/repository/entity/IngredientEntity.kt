package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.IngredientTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class IngredientEntity(id: EntityID<Long>) : LongEntity(id) {

    var asset by IngredientTable.asset
    var name by IngredientTable.name
    var removable by IngredientTable.removable

    fun update(name: String, removable: Boolean) {
        this.name = name
        this.removable = removable
    }

    companion object : LongEntityClass<IngredientEntity>(IngredientTable) {
        fun new(asset: AssetEntity, name: String, removable: Boolean) = new {
            this.asset = asset.id
            this.name = name
            this.removable = removable
        }

        fun findByAsset(asset: AssetEntity) = find {
            IngredientTable.asset.eq(asset.id)
        }.toList()

    }
}