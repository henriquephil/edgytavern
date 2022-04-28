package com.hphil.tavern.bills.domain

import com.hphil.tavern.bills.domain.references.AssetReference
import org.hibernate.annotations.Type
import java.math.BigDecimal
import javax.persistence.*

@Entity
open class OrderItem(
    @ManyToOne
    open val order: Order,
    @Type(type = "json")
    open val asset: AssetReference,
    open val quantity: Int
) {
    @Id
    @GeneratedValue
    open var id: Long? = null

    @Enumerated(EnumType.STRING)
    open var status: OrderItemStatus = OrderItemStatus.RECEIVED
    val totalPrice: BigDecimal = asset.finalPrice * quantity.toBigDecimal()
}

enum class OrderItemStatus {
    RECEIVED,
    PREPARATION,
    DISPATCHED,
    DELIVERED
}

