package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.SpotTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and

class SpotEntity(id: EntityID<Long>) : LongEntity(id) {
    var establishment by SpotTable.establishment
    var group by SpotTable.group
    var quantity by SpotTable.quantity

    fun update(group: String, quantity: Int) {
        this.group = group
        this.quantity = quantity
    }

    companion object : LongEntityClass<SpotEntity>(SpotTable) {
        fun new(establishment: EstablishmentEntity, group: String, quantity: Int) = new {
            this.establishment = establishment.id
            this.group = group
            this.quantity = quantity
        }

        fun findByEstablishment(establishment: EstablishmentEntity) = find {
            SpotTable.establishment.eq(establishment.id)
        }.toList()

        fun findByEstablishmentAndId(establishment: EstablishmentEntity, id: Long) = find {
            SpotTable.establishment.eq(establishment.id) and (SpotTable.id eq id)
        }.single()
    }
}