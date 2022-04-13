package com.hphil.tavern.repository

import com.hphil.tavern.domain.Additional
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AdditionalRepository: PanacheRepository<Additional>