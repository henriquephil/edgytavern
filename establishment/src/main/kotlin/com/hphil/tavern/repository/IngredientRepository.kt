package com.hphil.tavern.repository

import com.hphil.tavern.domain.Ingredient
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class IngredientRepository: PanacheRepository<Ingredient>