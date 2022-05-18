package com.hphil.tavern.bills.domain

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Establishment(open var name: String = "",
                         open val ownerId: String = "") {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open var active: Boolean = true
    open val created: LocalDate = LocalDate.now()
}