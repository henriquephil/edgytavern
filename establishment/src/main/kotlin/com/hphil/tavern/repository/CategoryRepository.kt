package com.hphil.tavern.repository

import com.hphil.tavern.domain.Category
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CategoryRepository: PanacheRepository<Category> {
}