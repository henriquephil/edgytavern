package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.OrderLot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderLotRepository: JpaRepository<OrderLot, Long> {
    fun findAllByBill(bill: Bill): List<OrderLot>
    fun countByBill(bill: Bill): Int
}