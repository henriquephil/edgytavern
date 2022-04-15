package com.hphil.client.responses

import java.math.BigDecimal

class AssetResponse(val hashId: String,
                    val name: String,
                    val category: AssetCategoryResponse,
                    val price: BigDecimal,
                    val description: String?,
                    val ingredients: List<AssetIngredientResponse>,
                    val additionals: List<AssetAdditionalResponse>) {
    class AssetCategoryResponse(val hashId: String, val name: String)
    class AssetIngredientResponse(val hashId: String, val name: String)
    class AssetAdditionalResponse(val hashId: String, val name: String, val price: BigDecimal)
}
