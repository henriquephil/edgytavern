package com.hphil.tavern.bills.client.responses

import java.math.BigDecimal

class AssetResponse(
    val hashId: String,
    val name: String,
    val category: AssetCategoryDto,
    val price: BigDecimal,
    val description: String?,
    val ingredients: List<AssetIngredientDto>,
    val additionals: List<AssetAdditionalDto>
)

class AssetCategoryDto(val hashId: String, val name: String)
class AssetIngredientDto(val hashId: String, val name: String)
class AssetAdditionalDto(val hashId: String, val name: String, val price: BigDecimal)