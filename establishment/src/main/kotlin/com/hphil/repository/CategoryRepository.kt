package com.hphil.repository

import com.hphil.domain.Category
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CategoryRepository: PanacheRepository<Category> {
}