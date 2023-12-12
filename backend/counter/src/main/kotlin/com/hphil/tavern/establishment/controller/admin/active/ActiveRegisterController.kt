package com.hphil.tavern.establishment.controller.admin.active

import com.hphil.tavern.establishment.repository.entity.BillEntity
import com.hphil.tavern.establishment.repository.entity.RegisterEntity
import com.hphil.tavern.establishment.services.getAdminEstablishment
import com.hphil.tavern.establishment.services.getCurrentRegister
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.ZoneId
import java.time.ZonedDateTime


fun openRegister(ctx: Context) {
    transaction {
        val establishment = ctx.getAdminEstablishment()
        RegisterEntity.findByEstablishmentHashAndOpenTrue(establishment.hashId)
            ?.let { error("There is already an ongoing register") }

        val register = RegisterEntity.new(establishment.hashId)
        ctx.json(OpenRegisterDto(register.id.value, register.started.atZone(ZoneId.systemDefault())))
    }
}

fun getOpenRegister(ctx: Context) {
    val establishment = ctx.getAdminEstablishment()
    transaction {
        RegisterEntity.findByEstablishmentHashAndOpenTrue(establishment.hashId)?.let {
            ctx.json(OpenRegisterDto(it.id.value, it.started.atZone(ZoneId.systemDefault())))
        }
    }
}

fun closeRegister(ctx: Context) {
    transaction {
        val register = ctx.getCurrentRegister()
        assert(BillEntity.countOpenByRegister(register) == 0L) { "open bills pending" }
        register.close()
    }
}

private class OpenRegisterDto(val id: Long, val started: ZonedDateTime)
