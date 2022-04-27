package com.hphil.tavern.establishment.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
open class Spot(
    @ManyToOne
    open val group: SpotGroup = SpotGroup(),
    open var number: Int = 0
) {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open var qrCode: String? = null
}