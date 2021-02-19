package com.hphil.domain

import java.math.BigDecimal
import javax.persistence.*

open class OrderItem(
        @ManyToOne
        open val order: Order,
        @OneToOne
        @JoinTable
        open val asset: Asset,
        @OneToMany
        @JoinTable
        open val removedIngredients: List<Ingredient>,
        @OneToMany
        @JoinTable
        open val requestedAdditionals: List<Additional>,
        open val quantity: Int = 0,
        open val unitPrice: BigDecimal) {
    @Id
    @GeneratedValue
    open var id: Long? = null
    @Enumerated(EnumType.STRING)
    open var status: OrderItemStatus = OrderItemStatus.RECEIVED
    val finalPrice: BigDecimal = unitPrice * quantity.toBigDecimal()
}

enum class OrderItemStatus {
    RECEIVED,
    SEPARATION,
    PREPARATION,
    DISPATCHED,
    DELIVERED
}

