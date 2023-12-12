package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.repository.entity.RegisterEntity
import com.hphil.tavern.establishment.services.getAdminEstablishment
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId
import java.time.ZonedDateTime

fun getAllRegistersClosed(ctx: Context) {
    val establishment = ctx.getAdminEstablishment()
    transaction {
        val registers = RegisterEntity.findAllByEstablishmentHashAndOpenFalse(establishment.hashId)
        ctx.json(registers.map {
            RegistersDto(
                it.id.value,
                it.started.atZone(ZoneId.systemDefault()),
                it.ended?.atZone(ZoneId.systemDefault())
            )
        })
    }
}

private class RegistersDto(
    id: Long,
    started: ZonedDateTime,
    ended: ZonedDateTime?
)
