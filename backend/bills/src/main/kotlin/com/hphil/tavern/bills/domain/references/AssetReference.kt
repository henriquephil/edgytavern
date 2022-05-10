package com.hphil.tavern.bills.domain.references

import java.math.BigDecimal

class AssetReference(
    val hashId: String,
    val name: String,
    val category: CategoryReference,
    val basePrice: BigDecimal,
    val removedIngredients: List<IngredientReference>,
    val requestedAdditionals: List<AdditionalReference>
) {
    val finalPrice: BigDecimal = requestedAdditionals.map { it.price }.fold(basePrice, BigDecimal::add)
}