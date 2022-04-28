package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BillRepository: JpaRepository<Bill, Long> {
    @Query("FROM Bill WHERE user_uid = ?1 and open = true")
    fun findOpen(userUid: String): Bill?
    @Query("FROM Bill WHERE establishment_hash = ?1 and open = true")
    fun openBillsAt(establishmentHashId: String): List<Bill>
    fun findByEstablishmentHashAndId(hashId: String, billId: Long): Bill
}