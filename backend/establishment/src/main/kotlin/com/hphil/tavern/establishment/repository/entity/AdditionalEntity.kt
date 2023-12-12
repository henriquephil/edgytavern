package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.AdditionalTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.math.BigDecimal


class AdditionalEntity(id: EntityID<Long>) : LongEntity(id) {
    var asset by AdditionalTable.asset
    var name by AdditionalTable.name
    var price by AdditionalTable.price

    fun update(name: String, price: BigDecimal) {
        this.name = name
        this.price = price
    }

    companion object : LongEntityClass<AdditionalEntity>(AdditionalTable) {
        fun new(asset: AssetEntity, name: String, price: BigDecimal) = new {
            this.asset = asset.id
            this.name = name
            this.price = price
        }

        fun findByAsset(asset: AssetEntity) = find {
            AdditionalTable.asset.eq(asset.id)
        }.toList()

    }
}
