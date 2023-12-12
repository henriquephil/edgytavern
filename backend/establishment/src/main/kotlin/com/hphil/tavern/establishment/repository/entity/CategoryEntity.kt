package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.CategoryTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

class CategoryEntity(id: EntityID<Long>) : LongEntity(id) {
    var establishment by CategoryTable.establishment
    var name by CategoryTable.name
    var active by CategoryTable.active
    var order by CategoryTable.order

    companion object : LongEntityClass<CategoryEntity>(CategoryTable) {
        fun new(establishment: EstablishmentEntity, name: String) = CategoryEntity.new {
            this.establishment = establishment.id
            this.name = name
            this.active = true
            this.order = 1
        }

        fun findAllByEstablishment(establishment: EstablishmentEntity) = find {
            CategoryTable.establishment.eq(establishment.id)
        }.toList()

        fun findByEstablishmentAndId(establishment: EstablishmentEntity, categoryId: Long) = find {
            CategoryTable.establishment.eq(establishment.id) and (CategoryTable.id eq categoryId)
        }.single()

    }
}