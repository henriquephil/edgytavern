package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Establishment
import com.hphil.tavern.bills.repository.EstablishmentRepository
import org.hashids.Hashids
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/managed")
class ManagedEstablishmentController(
    private val establishmentRepository: EstablishmentRepository,
    private val hashids: Hashids
) : ManagedEstablishmentTrait {

    @PostMapping
    @Transactional
    fun create(@RequestBody request: CreateEstablishmentRequest, principal: Principal): EstablishmentResponse {
        establishmentRepository.findByOwnerUsername(principal.name)
            ?.also { error("User already has an establishment") } // TODO allow more establishments per user
        return establishmentRepository.save(Establishment(request.name, principal.name))
            .let { EstablishmentResponse(hashids.encode(it.id!!), it.name, it.active) }
    }

    @GetMapping
    fun getManagedEstablishment(principal: Principal): EstablishmentResponse? {
//        return EstablishmentResponse(hashids.encode(establishment.id!!), establishment.name, establishment.active)
        return establishmentRepository.findByOwnerUsername(principal.name)
            ?.let { EstablishmentResponse(hashids.encode(it.id!!), it.name, it.active) }
    }

    @PutMapping
    @Transactional
    fun update(@RequestBody request: UpdateEstablishmentRequest, principal: Principal) {
        val establishment = getEstablishment(establishmentRepository, principal)
        establishment.name = request.name
        establishment.active = request.active
    }
}

class CreateEstablishmentRequest(val name: String)
class UpdateEstablishmentRequest(val name: String, val active: Boolean)
class EstablishmentResponse(val hashId: String, val name: String, val active: Boolean)
