package com.hphil.tavern.bills.controller.open

import com.hphil.tavern.bills.repository.AssetRepository
import org.hashids.Hashids
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/{establishmentHash}/assets")
//@Authenticated
class AssetsController(
    private val assetRepository: AssetRepository,
    private val hashids: Hashids
) {

    @GetMapping
    fun getAssetsGrouped(@PathVariable establishmentHash: String): AllAssetsByCategory {
        val establishmentId = hashids.decode(establishmentHash)[0]
        val groups = assetRepository.findAllByEstablishmentId(establishmentId).groupBy { it.category }
        return AllAssetsByCategory(
            groups.map {
                CategoryWithAssetsDto(hashids.encode(it.key.id!!), it.key.name, it.value.map { asset ->
                    CategoryAssetDto(
                        hashids.encode(asset.id!!),
                        asset.name,
                        asset.price,
                        asset.description,
                        asset.ingredients.map { i -> AssetIngredientDto(hashids.encode(i.id!!), i.name) },
                        asset.additionals.map { a -> AssetAdditionalDto(hashids.encode(a.id!!), a.name, a.price) })
                })
            })
    }

    @GetMapping("/{assetHash}")
    fun getAssetByHash(
        @PathVariable establishmentHash: String,
        @PathVariable assetHash: String
    ): AssetResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        val assetId = hashids.decode(assetHash)[0]
        return assetRepository.findByEstablishmentIdAndAssetIds(establishmentId, assetId)
            ?.let { asset ->
                AssetResponse(assetHash,
                    asset.name,
                    AssetCategoryDto(hashids.encode(asset.category.id!!), asset.category.name),
                    asset.price,
                    asset.description,
                    asset.ingredients.map { AssetIngredientDto(hashids.encode(it.id!!), it.name) },
                    asset.additionals.map { AssetAdditionalDto(hashids.encode(it.id!!), it.name, it.price) })
            }
    }

    class AllAssetsByCategory(val categories: List<CategoryWithAssetsDto>)
    class CategoryWithAssetsDto(val hashId: String, val name: String, val assets: List<CategoryAssetDto>)
    class CategoryAssetDto(
        val hashId: String,
        val name: String,
        val price: BigDecimal,
        val description: String?,
        val ingredients: List<AssetIngredientDto>,
        val additionals: List<AssetAdditionalDto>
    )

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
}