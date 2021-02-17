package com.hphil.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
open class Ingredient(open var name: String = "", open var removable: Boolean = false) {
    @Id
    @GeneratedValue
    open val id: Long? = null
}