package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Spot
import com.hphil.tavern.bills.domain.SpotGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SpotRepository : JpaRepository<Spot, Long> {
    fun countByGroup(group: SpotGroup): Int
    fun deleteByGroupAndNumber(group: SpotGroup, number: Int)
    @Query("FROM Spot WHERE group.establishment.id = ?1 and group.establishment.active = true and id = ?2 and group.active = true")
    fun findByEstablishmentAndSpotIds(establishmentId: Long, spotId: Long): Spot?
}