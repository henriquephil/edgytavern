package com.hphil.tavern.bills.domain

import com.hphil.tavern.bills.domain.references.SpotReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
open class OrderLot(
    @ManyToOne
    open val bill: Bill,
    @Embedded
    open val spot: SpotReference
) {
    @Id
    @GeneratedValue
    open var id: Long? = null
    open val createdAt: LocalDateTime = LocalDateTime.now()
}