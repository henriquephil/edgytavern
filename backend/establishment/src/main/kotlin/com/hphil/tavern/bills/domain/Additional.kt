package com.hphil.tavern.bills.domain

import com.hphil.tavern.bills.util.Identifiable
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Additional(open var name: String = "", open var price: BigDecimal = BigDecimal.ZERO): Identifiable<Long> {
    @Id
    @GeneratedValue
    open val id: Long? = null

    override fun getKey(): Long? = id
}