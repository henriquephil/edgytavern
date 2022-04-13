package com.hphil.tavern.repository

import com.hphil.tavern.domain.Asset
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AssetRepository: PanacheRepository<Asset> {
}