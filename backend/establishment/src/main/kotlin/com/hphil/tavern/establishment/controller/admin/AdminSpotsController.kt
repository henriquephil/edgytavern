package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.repository.entity.SpotEntity
import com.hphil.tavern.establishment.repository.table.SpotTable
import com.hphil.tavern.establishment.util.getAdminEstablishment
import io.javalin.http.Context
import org.hashids.Hashids
import org.jetbrains.exposed.sql.transactions.transaction

class AdminSpotsController(private val hashids: Hashids) {
    fun create(ctx: Context) {
        val request = ctx.bodyAsClass(AddSpotRequest::class.java)
        transaction {
            val establishment = ctx.getAdminEstablishment()
            SpotEntity.new(establishment, request.group, request.quantity)
        }
    }

    fun get(ctx: Context) {
        transaction {
            val establishment = ctx.getAdminEstablishment()
            ctx.json(
                SpotEntity.findByEstablishment(establishment)
                    .map { SpotGroupDto(it.id.value, it.group, it.quantity) }
            )
        }
    }

    fun getById(ctx: Context) {
        val spotId = ctx.pathParamAsClass("spotId", Long::class.java).get()
        transaction {
            val spot = SpotEntity.findByEstablishmentAndId(ctx.getAdminEstablishment(), spotId)
            ctx.json(
                SpotDto(
                    spot.id.value, spot.group,
                    (1..spot.quantity).map {
                        SpotQrCodeDto(it, hashids.encode(spot.id.value, it.toLong()))
                    }
                )

            )
        }
    }

    fun update(ctx: Context) {
        val spotId = ctx.pathParamAsClass("spotId", Long::class.java).get()
        val request = ctx.bodyAsClass(UpdateSpotGroupRequest::class.java)
        transaction {
            val spot = SpotEntity.findByEstablishmentAndId(ctx.getAdminEstablishment(), spotId)
            spot.update(request.group, request.quantity)
        }
    }

    fun delete(ctx: Context) {
        val spotId = ctx.pathParamAsClass("spotId", Long::class.java).get()
        SpotTable.delete(ctx.getAdminEstablishment().id.value, spotId)
    }
}

class AddSpotRequest(val group: String, val quantity: Int)
class SpotGroupDto(val id: Long, val group: String, val quantity: Int)
class SpotDto(val id: Long, val group: String, val qrCodes: List<SpotQrCodeDto>)
class SpotQrCodeDto(val sequence: Int, val qrCode: String)
class UpdateSpotGroupRequest(val group: String, val quantity: Int)
