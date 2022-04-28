package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Establishment
import com.hphil.tavern.bills.domain.SpotGroup
import org.springframework.data.jpa.repository.JpaRepository

interface SpotGroupRepository : JpaRepository<SpotGroup, Long> {
    fun findAllByEstablishment(establishment: Establishment): List<SpotGroup>
}