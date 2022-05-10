package com.hphil.tavern.bills.domain

import com.hphil.tavern.bills.domain.references.AssetReference
import com.vladmihalcea.hibernate.type.json.JsonType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.math.BigDecimal
import javax.persistence.*

@TypeDef(name = "json", typeClass = JsonType::class)
@Entity
open class OrderItem(
    @ManyToOne
    open val orderLot: OrderLot,
    @Type(type = "json")
    @Column(columnDefinition = "json")
    open val asset: AssetReference,
    open val quantity: Int
) {
    @Id
    @GeneratedValue
    open var id: Long? = null

    @Enumerated(EnumType.STRING)
    open var status: OrderItemStatus = OrderItemStatus.RECEIVED
    open val finalValue: BigDecimal = asset.finalPrice * quantity.toBigDecimal()
}

enum class OrderItemStatus {
    RECEIVED,
    PREPARATION,
    DISPATCHED,
    DELIVERED
}

