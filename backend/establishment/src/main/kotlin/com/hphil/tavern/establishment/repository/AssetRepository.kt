package com.hphil.tavern.establishment.repository

import com.hphil.tavern.establishment.domain.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface AssetRepository : JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset>