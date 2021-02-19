package com.hphil.repository

import com.hphil.domain.Bill
import com.hphil.domain.OrderItem
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderItemRepository: PanacheRepository<OrderItem> {
    fun findByEstablishmentAndId(establishmentHash: String, orderItemId: Long) =
            find("""from OrderItem oi
                |left join fetch oi.order o
                |left join fetch o.bill b
                |where b.establishmentHash = ?1 and oi.id = ?2 
            """.trimMargin(), establishmentHash, orderItemId)
}