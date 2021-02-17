package com.hphil.repository

import com.hphil.domain.Asset
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetRepository: PanacheRepository<Asset> {
}