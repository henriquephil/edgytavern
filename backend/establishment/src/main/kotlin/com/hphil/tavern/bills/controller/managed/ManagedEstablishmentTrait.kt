package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Establishment
import com.hphil.tavern.bills.repository.EstablishmentRepository
import com.hphil.tavern.bills.services.security.UserInfo

interface ManagedEstablishmentTrait {

    fun getEstablishment(establishmentRepository: EstablishmentRepository, userInfo: UserInfo): Establishment {
        return establishmentRepository.findByOwnerId(userInfo.id) ?: error("Establishment does not exists")
    }

    fun validateManager(establishment: Establishment, userInfo: UserInfo) {
        if (establishment.ownerId != userInfo.id) {
            error("User has no authority over this establishment")
        }
    }
}