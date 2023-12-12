package com.hphil.tavern.establishment.util

import com.hphil.tavern.establishment.repository.entity.EstablishmentEntity
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction


const val CTX_ADMIN_ESTABLISHMENT = "adminEstablishment"
fun loadAdminEstablishment(ctx: Context): EstablishmentEntity? {
    val userInfo = ctx.getUserInfo()
    return transaction {
        EstablishmentEntity.findByOwnerId(userInfo.id)
            ?.also {
                ctx.attribute(CTX_ADMIN_ESTABLISHMENT, it)
            }
    }
}

fun Context.getAdminEstablishment() =
    attribute(CTX_ADMIN_ESTABLISHMENT)
        ?: loadAdminEstablishment(this)
        ?: error("Establishment not found")