package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.Register
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface BillRepository: JpaRepository<Bill, Long> {
    fun findByUserUidAndOpenTrue(userUid: String): Bill?
    fun findAllByRegister(register: Register): List<Bill>
    fun findByRegisterAndId(register: Register, billId: Long): Bill
    fun findAllByRegisterAndStartedGreaterThanOrEndedGreaterThan(register: Register, lastUpdate: LocalDateTime, lastUpdate1: LocalDateTime): List<Bill>
}