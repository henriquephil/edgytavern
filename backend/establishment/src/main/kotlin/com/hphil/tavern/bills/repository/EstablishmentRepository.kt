package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Establishment
import org.springframework.data.jpa.repository.JpaRepository

interface EstablishmentRepository : JpaRepository<Establishment, Long> {
    fun findByOwnerUsername(user: String): Establishment?
    fun findByIdAndOwnerUsername(id: Long, ownerUsername: String): Establishment?
    fun findByIdAndActive(establishmentId: Long, active: Boolean): Establishment?
}