package com.hphil.tavern.domain

import javax.persistence.*

@Entity
open class Spot(
        @ManyToOne
        open val establishment: Establishment = Establishment(),
        @Column(name = "group_name")
        open var group: String = "",
        open var name: String = "") {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open var qrCode: String? = null
    open var active: Boolean = true
}