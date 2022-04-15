package com.hphil.tavern.orders.repository

import com.hphil.tavern.orders.domain.Order
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderRepository: PanacheRepository<Order> {
}