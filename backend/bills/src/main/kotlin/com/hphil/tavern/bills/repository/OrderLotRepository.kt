package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.OrderLot
import com.hphil.tavern.bills.domain.Register
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderLotRepository: JpaRepository<OrderLot, Long> {
    fun countByBill(bill: Bill): Int
    fun findAllByBillRegister(register: Register): List<OrderLot>
    fun findAllByBillRegisterAndCreatedAtGreaterThan(register: Register, lastUpdate: LocalDateTime): List<OrderLot>
}