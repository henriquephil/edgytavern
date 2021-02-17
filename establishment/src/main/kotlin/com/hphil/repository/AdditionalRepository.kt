package com.hphil.repository

import com.hphil.domain.Additional
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AdditionalRepository: PanacheRepository<Additional>