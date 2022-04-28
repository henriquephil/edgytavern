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
class AssetsResource(
    private val assetRepository: AssetRepository,
    private val hashids: Hashids
) {

    @GetMapping
    fun getAssets(@PathVariable establishmentHash: String): AllAssetsResponse {
        val establishmentId = hashids.decode(establishmentHash)[0]
        return AllAssetsResponse(
            assetRepository.findAllByEstablishmentId(establishmentId)
                .map {
                    SimpleAssetDto(
                        hashids.encode(it.id!!),
                        it.name,
                        AssetCategoryDto(hashids.encode(it.category.id!!), it.category.name),
                        it.price,
                        it.description
                    )
                }
        )
    }

    @GetMapping("/{assetHash}")
    fun getAssetByHash(
        @PathVariable establishmentHash: String,
        @PathVariable assetHash: String
    ): FullAssetResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        val assetId = hashids.decode(assetHash)[0]
        return assetRepository.findByEstablishmentAndAssetIds(establishmentId, assetId)
            ?.let { asset ->
                FullAssetResponse(assetHash,
                    asset.name,
                    AssetCategoryDto(hashids.encode(asset.category.id!!), asset.category.name),
                    asset.price,
                    asset.description,
                    asset.ingredients.map { AssetIngredientDto(hashids.encode(it.id!!), it.name) },
                    asset.additionals.map { AssetAdditionalDto(hashids.encode(it.id!!), it.name, it.price) })
            }
    }

    class AllAssetsResponse(val simpleAssets: List<SimpleAssetDto>)
    class SimpleAssetDto(
        val hashId: String,
        val name: String,
        val category: AssetCategoryDto,
        val price: BigDecimal,
        val description: String?
    )

    class FullAssetResponse(
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