package com.hphil.tavern.bills.domain.references

import java.math.BigDecimal

open class AdditionalReference(
    open val hashId: String,
    open val name: String,
    open val price: BigDecimal
)
