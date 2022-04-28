package com.hphil.tavern.bills.domain

import com.hphil.tavern.bills.util.Identifiable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Ingredient(open var name: String = "", open var removable: Boolean = false): Identifiable<Long> {
    @Id
    @GeneratedValue
    open val id: Long? = null

    override fun getKey(): Long? = id
}