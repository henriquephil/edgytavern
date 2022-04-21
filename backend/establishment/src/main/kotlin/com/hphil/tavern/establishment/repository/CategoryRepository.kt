package com.hphil.tavern.establishment.repository

import com.hphil.tavern.establishment.domain.Category
import com.hphil.tavern.establishment.domain.Establishment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findAllByEstablishment(establishment: Establishment): List<Category>
}