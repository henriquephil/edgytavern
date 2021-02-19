package com.hphil.repository

import com.hphil.domain.Order
import com.hphil.domain.Bill
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderRepository: PanacheRepository<Order> {
    fun findAll(bill: Bill) = list("bill_id", bill.id)
}