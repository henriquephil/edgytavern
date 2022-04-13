package com.hphil.tavern.domain

import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Additional(open var name: String = "", open var price: BigDecimal = BigDecimal.ZERO){
    @Id
    @GeneratedValue
    open val id: Long? = null
}