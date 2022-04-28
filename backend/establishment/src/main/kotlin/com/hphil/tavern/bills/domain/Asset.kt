package com.hphil.tavern.bills.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
open class Asset(
    @ManyToOne
    open val establishment: Establishment = Establishment(),
    open var name: String = "",
    @ManyToOne
    open var category: Category = Category(),
    open var price: BigDecimal = BigDecimal.ZERO,
    open var description: String? = "",
    @OneToMany(cascade = [(CascadeType.ALL)])
    open val ingredients: MutableSet<Ingredient> = HashSet(),
    @OneToMany(cascade = [(CascadeType.ALL)])
    open val additionals: MutableSet<Additional> = HashSet()
) {
    @Id
    @GeneratedValue
    open val id: Long? = null
    open var active = true
}