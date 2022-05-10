package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.OrderItem
import com.hphil.tavern.bills.domain.OrderLot
import com.hphil.tavern.bills.domain.Register
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository: JpaRepository<OrderItem, Long> {
    fun findByOrderLotBillRegisterAndId(register: Register, orderItemId: Long): OrderItem
    fun findAllByOrderLotBill(bill: Bill): List<OrderItem>
    fun findAllByOrderLot(orderLot: OrderLot): List<OrderItem>
}