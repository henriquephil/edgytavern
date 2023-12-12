package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.repository.entity.EstablishmentEntity
import com.hphil.tavern.establishment.util.getAdminEstablishment
import com.hphil.tavern.establishment.util.getUserInfo
import io.javalin.http.Context
import org.hashids.Hashids
import org.jetbrains.exposed.sql.transactions.transaction

class AdminEstablishmentController(private val hashids: Hashids) {
    fun create(ctx: Context) {
        val request = ctx.bodyAsClass(CreateEstablishmentRequest::class.java)
        transaction {
            val userInfo = ctx.getUserInfo()
            EstablishmentEntity.findByOwnerId(userInfo.id)
                ?.also { error("User already has an establishment") } // TODO allow more establishments per user
            val establishment = EstablishmentEntity.new(request.name, userInfo.id)
            ctx.json(
                EstablishmentResponse(hashids.encode(establishment.id.value), establishment.name, establishment.active)
            )
        }
    }

    fun get(ctx: Context) {
        transaction {
            val userInfo = ctx.getUserInfo()
            EstablishmentEntity.findByOwnerId(userInfo.id)?.let {
                ctx.json(
                    EstablishmentResponse(hashids.encode(it.id.value), it.name, it.active)
                )
            }
        }
    }

    fun update(ctx: Context) {
        val request = ctx.bodyAsClass(UpdateEstablishmentRequest::class.java)
        transaction {
            val establishment = ctx.getAdminEstablishment()
            establishment.name = request.name
            establishment.active = request.active
        }
    }

}

class CreateEstablishmentRequest(val name: String)
class UpdateEstablishmentRequest(val name: String, val active: Boolean)
class EstablishmentResponse(val hashId: String, val name: String, val active: Boolean)
