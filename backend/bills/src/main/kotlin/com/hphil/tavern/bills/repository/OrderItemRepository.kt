package com.hphil.tavern.bills.repository

import com.hphil.tavern.bills.domain.Bill
import com.hphil.tavern.bills.domain.Order
import com.hphil.tavern.bills.domain.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository: JpaRepository<OrderItem, Long> {
    @Query("""FROM OrderItem oi
                |LEFT JOIN FETCH oi.order o
                |LEFT JOIN FETCH o.bill b
                |WHERE b.establishmentHash = ?1 AND oi.id = ?2 
            """)
    fun findByEstablishmentHashAndId(establishmentHash: String, orderItemId: Long): OrderItem
    fun findAllByOrderBill(bill: Bill): List<OrderItem>
    fun findAllByOrder(order: Order): List<OrderItem>
}