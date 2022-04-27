package com.hphil.tavern.establishment.repository

import com.hphil.tavern.establishment.domain.Establishment
import com.hphil.tavern.establishment.domain.SpotGroup
import org.springframework.data.jpa.repository.JpaRepository

interface SpotGroupRepository : JpaRepository<SpotGroup, Long> {
    fun findAllByEstablishment(establishment: Establishment): List<SpotGroup>
}