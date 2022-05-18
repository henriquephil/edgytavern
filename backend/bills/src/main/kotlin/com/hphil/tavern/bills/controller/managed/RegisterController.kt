package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.client.ManagedEstablishmentClient
import com.hphil.tavern.bills.domain.Register
import com.hphil.tavern.bills.repository.RegisterRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/managed/register")
class RegisterController(
    val managedEstablishmentClient: ManagedEstablishmentClient,
    val registerRepository: RegisterRepository
) {

    @GetMapping
    fun getOpen(): OpenRegisterResponse? {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        return registerRepository.findByEstablishmentHashAndOpenTrue(establishment.hashId)
            ?.let { OpenRegisterResponse(it.id!!, it.started) }
    }

    @Transactional
    @PostMapping
    fun open(): OpenRegisterResponse {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        registerRepository.findByEstablishmentHashAndOpenTrue(establishment.hashId)
            ?.let { error("There is already an ongoing register") }

        val register = registerRepository.save(
            Register(establishment.hashId)
        )
        return OpenRegisterResponse(register.id!!, register.started)
    }

    @Transactional
    @PostMapping("/close")
    fun close() {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        val register = registerRepository.findByEstablishmentHashAndOpenTrue(establishment.hashId) ?: error("Register not running")
        // TODO verify orders not delivered
        register.close()
    }
}

class OpenRegisterResponse(val id: Long, val started: LocalDateTime)