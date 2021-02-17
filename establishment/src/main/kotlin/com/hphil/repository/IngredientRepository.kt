package com.hphil.repository

import com.hphil.domain.Ingredient
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class IngredientRepository: PanacheRepository<Ingredient>