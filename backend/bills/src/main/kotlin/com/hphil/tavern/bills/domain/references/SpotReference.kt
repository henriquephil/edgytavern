package com.hphil.tavern.bills.domain.references

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
open class SpotReference(
    @Column(name = "spot_hash_id")
    open val hashId: String,
    @Column(name = "spot_name")
    open val name: String,
    @Column(name = "spot_number")
    open val number: Int
)