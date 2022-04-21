package com.hphil.tavern.establishment.controller

import com.hphil.tavern.establishment.domain.Spot
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import com.hphil.tavern.establishment.repository.SpotRepository
import com.hphil.tavern.establishment.services.AsyncFileStorage
import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.websocket.server.PathParam

@RestController
@RequestMapping("/managed/spots")
class ManagedSpotsController(
    val establishmentRepository: EstablishmentRepository,
    val spotRepository: SpotRepository,
    val hashIds: Hashids,
    val asyncFileStorage: AsyncFileStorage
) : ManagedEstablishmentTrait {

    @Value("\${app.baseUrl}")
    private val url = "http://localhost:3000";

    @PostMapping
    @Transactional
    fun addSpot(@RequestBody request: AddSpotRequest, principal: Principal) {
        val spot = Spot(
            getEstablishment(establishmentRepository, principal),
            request.group,
            request.name
        )
        spotImage(spotRepository.saveAndFlush(spot))
    }

    private fun spotImage(spot: Spot) {
        val establishmentHash = hashIds.encode(spot.establishment.id!!)
        val spotHash = hashIds.encode(spot.id!!)
        spot.qrCode = "$establishmentHash.$spotHash"

        asyncFileStorage.generateAndStoreQrCodeImage("$url/e=$establishmentHash&s=$spotHash", spot.qrCode!!)
    }

    @GetMapping
    fun findAll(principal: Principal): AllSpotResponse {
        return AllSpotResponse(
            spotRepository
                .findAllByEstablishment(getEstablishment(establishmentRepository, principal))
                .map { SpotDto(it) }
        )
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@RequestBody request: UpdateSpotRequest, @PathParam("id") id: Long, principal: Principal) {
        val spot = spotRepository.findById(id).orElseThrow()
        validateManager(spot.establishment, principal)

        spot.group = request.group
        spot.name = request.name
        spot.active = request.active
    }

}

class AddSpotRequest(val group: String, val name: String)

class AllSpotResponse(val spots: List<SpotDto>)
class SpotDto(val id: Long, val group: String, val name: String, val qrCode: String?) {
    constructor(spot: Spot) : this(spot.id!!, spot.group, spot.name, spot.qrCode)
}

class UpdateSpotRequest(val group: String, val name: String, val active: Boolean)
