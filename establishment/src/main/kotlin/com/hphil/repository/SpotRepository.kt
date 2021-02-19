package com.hphil.repository

import com.hphil.domain.Spot
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SpotRepository: PanacheRepository<Spot> {
}