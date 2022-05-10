package com.hphil.tavern.bills.services

import com.hphil.tavern.bills.client.ManagedEstablishmentClient
import com.hphil.tavern.bills.domain.Register
import com.hphil.tavern.bills.repository.RegisterRepository
import org.springframework.stereotype.Service

@Service
class RegisterService(
    private val registerRepository: RegisterRepository,
    private val managedEstablishmentClient: ManagedEstablishmentClient
) {
    fun getRegisterByEstablishmentHash(): Register {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        return registerRepository.findByEstablishmentHashAndOpenTrue(establishment.hashId) ?: error("No register found")
    }

    fun getRegisterByEstablishmentHash(id: Long?): Register {
        if (id == null) {
            return getRegisterByEstablishmentHash()
        }
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        return registerRepository.findByEstablishmentHashAndId(establishment.hashId, id)
    }
}