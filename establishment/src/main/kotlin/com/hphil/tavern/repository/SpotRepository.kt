package com.hphil.tavern.repository

import com.hphil.tavern.domain.Spot
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SpotRepository: PanacheRepository<Spot> {
}