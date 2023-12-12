package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.AssetTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import java.math.BigDecimal

class AssetEntity(id: EntityID<Long>) : LongEntity(id) {
    var establishment by AssetTable.establishment
    var name by AssetTable.name
    var category by AssetTable.category
    var price by AssetTable.price
    var description by AssetTable.description
    var active by AssetTable.active
    var order by AssetTable.order

    fun update(name: String, category: CategoryEntity, price: BigDecimal, description: String?, active: Boolean) {
        this.name = name
        this.category = category.id
        this.price = price
        this.description = description
        this.active = active
    }

    companion object : LongEntityClass<AssetEntity>(AssetTable) {
        fun new(
            establishment: EstablishmentEntity, name: String, category: CategoryEntity,
            price: BigDecimal, description: String?
        ) = new {
            this.establishment = establishment.id
            this.name = name
            this.category = category.id
            this.price = price
            this.description = description
            this.active = true
            this.order = 1
        }

        fun findByEstablishmentAndCategoryId(establishment: EstablishmentEntity, categoryId: Long?) = find {
            AssetTable.establishment.eq(establishment.id) and (categoryId?.let { (AssetTable.category.eq(categoryId)) } ?: Op.TRUE) // TODO improve
        }.toList()

        fun findByEstablishmentAndId(establishment: EstablishmentEntity, assetId: Long) = find {
            AssetTable.establishment.eq(establishment.id) and (AssetTable.id eq assetId)
        }.single()
    }
}
