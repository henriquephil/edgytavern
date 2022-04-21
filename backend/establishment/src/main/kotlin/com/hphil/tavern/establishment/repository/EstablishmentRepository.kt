package com.hphil.tavern.establishment.repository

import com.hphil.tavern.establishment.domain.Establishment
import org.springframework.data.jpa.repository.JpaRepository

interface EstablishmentRepository : JpaRepository<Establishment, Long> {
    fun findByOwnerUsername(user: String): Establishment?
    fun findByIdAndOwnerUsername(id: Long, ownerUsername: String): Establishment?
}