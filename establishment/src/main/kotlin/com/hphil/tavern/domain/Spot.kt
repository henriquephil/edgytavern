package com.hphil.tavern.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
open class Spot(
        @ManyToOne
        open val establishment: Establishment = Establishment(),
        open var name: String = "") {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open var active: Boolean = true
}