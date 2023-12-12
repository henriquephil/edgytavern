package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.TabTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TabEntity(id: EntityID<Long>) : LongEntity(id) {
    var establishment by TabTable.establishment
    var code by TabTable.code
    var active by TabTable.active

    companion object : LongEntityClass<TabEntity>(TabTable) {
        fun new(establishment: EstablishmentEntity, code: String) = TabEntity.new {
            this.establishment = establishment.id
            this.code = code
            this.active = true
        }

    }
}
