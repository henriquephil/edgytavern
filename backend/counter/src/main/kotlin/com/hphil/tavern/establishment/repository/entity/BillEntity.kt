package com.hphil.tavern.establishment.repository.entity

import com.hphil.tavern.establishment.repository.table.BillTable
import com.hphil.tavern.establishment.repository.table.RegisterTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import java.time.LocalDateTime
import java.util.*

class BillEntity(id: EntityID<Long>) : LongEntity(id) {
    var register by RegisterEntity referencedOn BillTable.register
    var tabCode by BillTable.tabCode
    var open by BillTable.open
    var started by BillTable.started
    var ended by BillTable.ended
    var session by BillTable.session

    fun close() {
        assert(open) { "Bill already closed" }
        open = false
        ended = LocalDateTime.now()
        releaseSession()
    }

    fun releaseSession() {
        assert(open) { "Can't release a closed bill" }
        session = null
    }

    fun beginSession(): UUID {
        assert(open && session == null) { "Bill unavailable" }
        session = UUID.randomUUID()
        return session!!
    }

    companion object : LongEntityClass<BillEntity>(BillTable) {
        fun new(tabCode: String, register: RegisterEntity) = new {
            this.tabCode = tabCode
            open = true
            started = LocalDateTime.now()
            this.register = register
        }

        // by customer session
        fun findBySessionAndOpenTrue(session: UUID) = find {
            (BillTable.session eq session) and (BillTable.open eq true)
        }.firstOrNull()

        // by register
        fun findByTabCodeAndOpenTrue(register: RegisterEntity, tabCode: String) = find {
            (BillTable.register.eq(register.id)) and (BillTable.tabCode eq tabCode) and (BillTable.open eq true)
        }.firstOrNull()

        fun findById(register: RegisterEntity, id: Long) = find {
            (BillTable.register.eq(register.id)) and (BillTable.id eq id)
        }.first()

        fun findAllByRegister(register: RegisterEntity) = find {
            BillTable.register.eq(register.id)
        }.toList()

        fun countOpenByRegister(register: RegisterEntity) = find {
            BillTable.register.eq(register.id) and (BillTable.open eq true)
        }.count()

        // by establishment
        fun findById(establishmentHash: String, id: Long) = find {
            BillTable.register.inSubQuery(
                RegisterEntity.searchQuery(RegisterTable.establishmentHash eq establishmentHash)
            ) and (BillTable.id eq id)
        }.first()

        fun findAllByRegister(establishmentHash: String, registerId: Long) = find {
            BillTable.register.inSubQuery(
                RegisterEntity.searchQuery(RegisterTable.establishmentHash eq establishmentHash)
            ) and (BillTable.register.eq(registerId))
        }.toList()

    }
}