package com.hphil.tavern.establishment.controller

import com.hphil.tavern.establishment.domain.Establishment
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import org.hashids.Hashids
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.transaction.Transactional

@RestController
@RequestMapping("/managed")
class ManagedEstablishment(
    private val establishmentRepository: EstablishmentRepository,
    private val hashids: Hashids
) : ManagedEstablishmentTrait {

    @PostMapping
    @Transactional
    fun create(@RequestBody request: CreateEstablishmentRequest, principal: Principal) {
        establishmentRepository.findByOwnerUsername(principal.name)
            ?.also { error("User already has an establishment") } // TODO allow more establishments per user
        establishmentRepository.save(Establishment(request.name, principal.name))
    }

    @GetMapping
    fun getManagedEstablishment(establishment: Establishment): EstablishmentResponse? {
        return EstablishmentResponse(hashids.encode(establishment.id!!), establishment.name, establishment.active)
//        return establishmentRepository.findByOwnerUsername(principal.name)
//            ?.let { EstablishmentResponse(hashids.encode(it.id!!), it.name, it.active) }
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
