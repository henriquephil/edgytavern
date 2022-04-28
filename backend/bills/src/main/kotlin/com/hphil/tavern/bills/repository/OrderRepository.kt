package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findAllByBill(bill: Bill): List<Order>
    fun countByBill(bill: Bill): Int
}