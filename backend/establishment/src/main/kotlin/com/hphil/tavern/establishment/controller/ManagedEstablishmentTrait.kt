package com.hphil.tavern.establishment.controller

import com.hphil.tavern.establishment.domain.Establishment
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import java.security.Principal

interface ManagedEstablishmentTrait {

    fun getEstablishment(establishmentRepository: EstablishmentRepository, principal: Principal): Establishment {
        return establishmentRepository.findByOwnerUsername(principal.name) ?: error("Establishment does not exists")
    }

    fun validateManager(establishment: Establishment, principal: Principal) {
        if (establishment.ownerUsername != principal.name) {
            error("User has no authority over this establishment");
        }
    }
}