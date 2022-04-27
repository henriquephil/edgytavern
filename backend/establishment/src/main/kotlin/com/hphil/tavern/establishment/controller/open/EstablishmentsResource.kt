package com.hphil.tavern.establishment.controller.open

import com.hphil.tavern.establishment.repository.EstablishmentRepository
import org.hashids.Hashids
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class EstablishmentsResource(
    private val establishmentRepository: EstablishmentRepository,
    private val hashids: Hashids
) {

    @GetMapping("/{establishmentHash}")
    fun getEstablishmentByHash(@PathVariable establishmentHash: String): EstablishmentResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        return establishmentRepository.findByIdAndActive(establishmentId, true)
            ?.let { EstablishmentResponse(it.name) }
    }

    class EstablishmentResponse(val name: String)
}