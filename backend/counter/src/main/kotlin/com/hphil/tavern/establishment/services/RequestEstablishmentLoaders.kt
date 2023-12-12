package com.hphil.tavern.establishment.services

import com.hphil.tavern.establishment.client.getManagedEstablishment
import com.hphil.tavern.establishment.client.responses.EstablishmentResponse
import com.hphil.tavern.establishment.client.responses.ManagedEstablishmentResponse
import com.hphil.tavern.establishment.repository.entity.RegisterEntity
import io.javalin.http.Context

const val KEY_ADMIN_ESTABLISHMENT = "adminEstablishment"
const val KEY_REGISTER = "register"

fun loadAdminEstablishment(ctx: Context) {
    ctx.attribute(KEY_ADMIN_ESTABLISHMENT, getManagedEstablishment(ctx))
}

fun Context.getAdminEstablishment() = attribute<EstablishmentResponse>(KEY_ADMIN_ESTABLISHMENT) ?: error("No establishment found")

fun loadActiveRegister(ctx: Context) {
    ctx.attribute<ManagedEstablishmentResponse>(KEY_ADMIN_ESTABLISHMENT)?.let { establishment ->
        RegisterEntity.findByEstablishmentHashAndOpenTrue(establishment.hashId)?.let { register ->
            ctx.attribute(KEY_REGISTER, register)
        }
    }
}

fun Context.getCurrentRegister() = attribute<RegisterEntity>(KEY_REGISTER) ?: error("No running register found")
