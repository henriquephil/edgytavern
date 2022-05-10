package com.hphil.tavern.bills.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
open class Bill(
    open val userUid: String,
    open val userName: String,
    @ManyToOne
    open val register: Register
) {
    @Id
    @GeneratedValue
    open val id: Long? = null
    @OneToMany(mappedBy = "bill")
    open val orderLots: List<OrderLot> = emptyList()
    open var open: Boolean = true
    open val started: LocalDateTime = LocalDateTime.now()
    open var ended: LocalDateTime? = null

    fun close() {
        open = false
        ended = LocalDateTime.now()
    }
}
