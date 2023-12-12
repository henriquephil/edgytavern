package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.EstablishmentTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime

class EstablishmentEntity(id: EntityID<Long>) : LongEntity(id) {
    var name by EstablishmentTable.name
    var ownerId by EstablishmentTable.ownerId
    var active by EstablishmentTable.active
    var created by EstablishmentTable.created

    companion object : LongEntityClass<EstablishmentEntity>(EstablishmentTable) {
        fun new(name: String, ownerId: String) = new {
            this.name = name
            this.ownerId = ownerId
            this.active = true
            this.created = LocalDateTime.now()
        }

        fun findByOwnerId(ownerId: String) = find {
            EstablishmentTable.ownerId eq ownerId
        }.firstOrNull()
    }
}