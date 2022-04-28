package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Establishment
import com.hphil.tavern.bills.repository.EstablishmentRepository
import java.security.Principal

interface ManagedEstablishmentTrait {

    fun getEstablishment(establishmentRepository: EstablishmentRepository, principal: Principal): Establishment {
        return establishmentRepository.findByOwnerUsername(principal.name) ?: error("Establishment does not exists")
    }

    fun validateManager(establishment: Establishment, principal: Principal) {
        if (establishment.ownerUsername != principal.name) {
            error("User has no authority over this establishment")
        }
    }
}