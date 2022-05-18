package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.client.ManagedEstablishmentClient
import com.hphil.tavern.bills.domain.Register
import com.hphil.tavern.bills.repository.RegisterRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/managed/registers")
class RegistersController(
    val managedEstablishmentClient: ManagedEstablishmentClient,
    val registerRepository: RegisterRepository
) {
    @GetMapping
    fun getAllClosed(): List<RegistersDto> {
        val establishment = managedEstablishmentClient.getManagedEstablishment()
        return registerRepository.findAllByEstablishmentHashAndOpenFalse(establishment.hashId)
            .map { RegistersDto(it) }
    }
}

class RegistersDto(id: Long, establishmentHash: String, open: Boolean, started: LocalDateTime, ended: LocalDateTime?) {
    constructor(register: Register) : this(register.id!!, register.establishmentHash, register.open, register.started, register.ended)
}
