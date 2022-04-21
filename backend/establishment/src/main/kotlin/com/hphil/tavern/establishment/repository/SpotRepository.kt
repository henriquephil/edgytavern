package com.hphil.tavern.establishment.repository

import com.hphil.tavern.establishment.domain.Establishment
import com.hphil.tavern.establishment.domain.Spot
import org.springframework.data.jpa.repository.JpaRepository

interface SpotRepository : JpaRepository<Spot, Long> {
    fun findAllByEstablishment(establishment: Establishment): List<Spot>
}