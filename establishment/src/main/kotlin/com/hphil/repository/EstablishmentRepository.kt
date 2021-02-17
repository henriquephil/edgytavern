package com.hphil.repository

import com.hphil.domain.Establishment
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class EstablishmentRepository: PanacheRepository<Establishment> {
}