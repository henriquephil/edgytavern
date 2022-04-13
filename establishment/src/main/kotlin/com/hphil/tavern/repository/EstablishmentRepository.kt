package com.hphil.tavern.repository

import com.hphil.tavern.domain.Establishment
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EstablishmentRepository: PanacheRepository<Establishment> {
}