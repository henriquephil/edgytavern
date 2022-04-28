package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.domain.Spot
import com.hphil.tavern.bills.repository.SpotRepository
import org.hashids.Hashids
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/{establishmentHash}/spots")
class SpotsResource(
    private var spotRepository: SpotRepository,
    private var hashids: Hashids
) {

    @GetMapping("/{spotHash}")
    fun getSpotByHash(principal: Principal,
                      @PathVariable establishmentHash: String,
                      @PathVariable spotHash: String): SpotResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        val spotId = hashids.decode(spotHash)[0]
        // using establishment.id as filter to assert that the received hash is valid
        return spotRepository.findByEstablishmentAndSpotIds(establishmentId, spotId)
                ?.let { SpotResponse(it) }
    }

    class SpotResponse(val name: String, val number: Int){
        constructor(spot: Spot) : this(spot.group.name, spot.number)
    }
}