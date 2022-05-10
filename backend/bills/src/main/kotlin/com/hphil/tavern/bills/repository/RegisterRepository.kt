package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Register
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegisterRepository: JpaRepository<Register, Long> {
    fun findByEstablishmentHashAndOpenTrue(establishmentHash: String): Register?
    fun findAllByEstablishmentHashAndOpenFalse(establishmentHash: String): List<Register>
    fun findByEstablishmentHashAndId(establishmentHash: String, id: Long): Register
}