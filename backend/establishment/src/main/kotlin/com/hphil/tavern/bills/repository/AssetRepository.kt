package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface AssetRepository : JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {
    @Query("FROM Asset WHERE establishment.id = ?1 and establishment.active = true and id = ?2 and active = true")
    fun findByEstablishmentAndAssetIds(establishmentId: Long, assetId: Long): Asset?
    fun findAllByEstablishmentId(establishmentId: Long): List<Asset>
}