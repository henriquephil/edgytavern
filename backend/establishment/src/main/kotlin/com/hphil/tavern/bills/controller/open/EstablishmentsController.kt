package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.repository.EstablishmentRepository
import org.hashids.Hashids
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class EstablishmentsController(
    private val establishmentRepository: EstablishmentRepository,
    private val hashids: Hashids
) {

    @GetMapping("/{establishmentHash}")
    fun getEstablishmentByHash(@PathVariable establishmentHash: String): EstablishmentResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        return establishmentRepository.findByIdAndActive(establishmentId, true)
            ?.let { EstablishmentResponse(establishmentHash, it.name) }
    }

    class EstablishmentResponse(val hashId: String, val name: String)
}