package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.RegisterTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime

class RegisterEntity(id: EntityID<Long>) : LongEntity(id) {
    var establishmentHash by RegisterTable.establishmentHash
    var open by RegisterTable.open
    var started by RegisterTable.started
    var ended by RegisterTable.ended

    fun close() {
        assert(open) { "Register already closed" }
        open = false
        ended = LocalDateTime.now()
    }

    companion object : LongEntityClass<RegisterEntity>(RegisterTable) {
        fun new(establishmentHash: String) = new {
            this.establishmentHash = establishmentHash
            this.open = true
            this.started = LocalDateTime.now()
        }

        fun findByEstablishmentHashAndOpenTrue(establishmentHashId: String) = find {
            (RegisterTable.establishmentHash eq establishmentHashId) and (RegisterTable.open eq true)
        }.firstOrNull()

        fun findAllByEstablishmentHashAndOpenFalse(establishmentHashId: String) = find {
            (RegisterTable.establishmentHash eq establishmentHashId) and (RegisterTable.open eq false)
        }.toList()

    }
}
