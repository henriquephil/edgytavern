package com.hphil.tavern.bills.domain

import com.hphil.tavern.bills.domain.references.SpotReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
open class Order(
    @ManyToOne
    open val bill: Bill,
    @OneToOne
    @JoinTable
    open val spot: SpotReference
) {
    @Id
    @GeneratedValue
    open var id: Long? = null

//    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
//    open val items: MutableList<OrderItem> = mutableListOf()
    open val time: LocalDateTime = LocalDateTime.now()
}