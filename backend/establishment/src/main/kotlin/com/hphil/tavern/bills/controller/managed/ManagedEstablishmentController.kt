package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Establishment
import com.hphil.tavern.bills.repository.EstablishmentRepository
import com.hphil.tavern.bills.services.security.UserInfo
import org.hashids.Hashids
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/managed")
class ManagedEstablishmentController(
    private val establishmentRepository: EstablishmentRepository,
    private val hashids: Hashids
) : ManagedEstablishmentTrait {

    @PostMapping
    @Transactional
    fun create(@RequestBody request: CreateEstablishmentRequest, userInfo: UserInfo): EstablishmentResponse {
        establishmentRepository.findByOwnerId(userInfo.id)
            ?.also { error("User already has an establishment") } // TODO allow more establishments per user
        return establishmentRepository.save(Establishment(request.name, userInfo.id))
            .let { EstablishmentResponse(hashids.encode(it.id!!), it.name, it.active) }
    }

    @GetMapping
    fun getManagedEstablishment(userInfo: UserInfo): EstablishmentResponse? {
        return establishmentRepository.findByOwnerId(userInfo.id)
            ?.let { EstablishmentResponse(hashids.encode(it.id!!), it.name, it.active) }
    }

    @PutMapping
    @Transactional
    fun update(@RequestBody request: UpdateEstablishmentRequest, userInfo: UserInfo) {
        val establishment = getEstablishment(establishmentRepository, userInfo)
        establishment.name = request.name
        establishment.active = request.active
    }
}

class CreateEstablishmentRequest(val name: String)
class UpdateEstablishmentRequest(val name: String, val active: Boolean)
class EstablishmentResponse(val hashId: String, val name: String, val active: Boolean)
