package com.hphil.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
open class Order (
        @ManyToOne
        open val bill: Bill = Bill(),
        @OneToOne
        @JoinTable
        open val spot: Spot) {
    @Id
    @GeneratedValue
    open var id: Long? = null
    @OneToMany(mappedBy = "order")
    open val items: MutableList<OrderItem> = mutableListOf()
    open val time: LocalDateTime = LocalDateTime.now()
}