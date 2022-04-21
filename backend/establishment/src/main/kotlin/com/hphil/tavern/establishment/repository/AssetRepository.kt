package com.hphil.tavern.establishment.repository

import com.hphil.tavern.establishment.domain.Asset
import com.hphil.tavern.establishment.domain.Establishment
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository


@Repository
interface AssetRepository : JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {

    fun categoryIdIs(categoryId: Long): Specification<Asset> {
        return Specification { asset, query, builder ->
            builder.equal(asset.get<Any>("category.id"), categoryId)
        }
    }
}
