package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Category
import com.hphil.tavern.bills.domain.Establishment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findAllByEstablishment(establishment: Establishment): List<Category>
}