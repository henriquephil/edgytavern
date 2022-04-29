package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Additional
import com.hphil.tavern.bills.domain.Asset
import com.hphil.tavern.bills.domain.Category
import com.hphil.tavern.bills.domain.Ingredient
import com.hphil.tavern.bills.repository.AssetRepository
import com.hphil.tavern.bills.repository.CategoryRepository
import com.hphil.tavern.bills.repository.EstablishmentRepository
import com.hphil.tavern.bills.util.MergeUtil.updateChildSet
import com.hphil.tavern.bills.util.RemovalIndicator
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.security.Principal

@RestController
@RequestMapping("/managed/assets")
class ManagedAssetsController(
    val establishmentRepository: EstablishmentRepository,
    val categoryRepository: CategoryRepository,
    val assetRepository: AssetRepository
) : ManagedEstablishmentTrait {


    @PostMapping
    @Transactional
    fun add(@RequestBody request: AddAssetRequest, principal: Principal) {
        assetRepository.save(
            Asset(
                getEstablishment(establishmentRepository, principal),
                request.name,
                parseCategory(request.categoryId),
                request.price,
                request.description,
                request.ingredients.map { Ingredient(it.name, it.removable) }.toMutableSet(),
                request.additionals.map { Additional(it.name, it.price) }.toMutableSet()
            )
        )
    }

    @GetMapping
    fun findAll(principal: Principal, @RequestParam("categoryId") categoryId: Long?): AllAssetsResponse {
        return AllAssetsResponse(
            assetRepository.findAll { root, _, builder ->
                builder.and(
                    builder.equal(root.get<Asset>("establishment"), getEstablishment(establishmentRepository, principal)),
                    categoryId.let { builder.equal(root.get<Asset>("category").get<Category>("id"), categoryId) }
                )
            }
                .map { AssetDto(it) }
        )
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@RequestBody request: UpdateAssetRequest, @PathVariable id: Long, principal: Principal) {
        val asset = assetRepository.findById(id).orElseThrow()
        validateManager(asset.establishment, principal)
        asset.name = request.name
        asset.category = parseCategory(request.categoryId)
        asset.price = request.price
        asset.description = request.description
        asset.active = request.active
        updateChildSet(
            asset.ingredients,
            request.ingredients,
            { Ingredient(it.name, it.removable) },
            { o, n ->
                o.name = n.name
                o.removable = n.removable
            }
        )
        updateChildSet(
            asset.additionals,
            request.additionals,
            { Additional(it.name, it.price) },
            { o, n ->
                o.name = n.name
                o.price = n.price
            }
        )
    }

    private fun parseCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId).orElseThrow { error("Category does not exist") }
    }
}

class AddAssetRequest(
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val description: String?,
    val ingredients: Set<AddIngredient> = HashSet(),
    val additionals: Set<AddAdditional> = HashSet()
)

class AddIngredient(val name: String, val removable: Boolean)
class AddAdditional(val name: String, val price: BigDecimal)

class UpdateAssetRequest(
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val description: String?,
    val active: Boolean,
    val ingredients: Set<UpdateIngredient> = HashSet(),
    val additionals: Set<UpdateAdditional> = HashSet()
)

class UpdateIngredient(
    val id: Long?,
    override val markedForRemoval: Boolean,
    val name: String,
    val removable: Boolean
) : RemovalIndicator<Long> {
    override fun getKey() = id
}

class UpdateAdditional(
    val id: Long?,
    override val markedForRemoval: Boolean,
    val name: String,
    val price: BigDecimal
) : RemovalIndicator<Long> {
    override fun getKey() = id
}

class AllAssetsResponse(val assets: List<AssetDto>)
class AssetDto(
    val id: Long,
    val active: Boolean,
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val description: String?,
    val ingredients: Set<IngredientDto>,
    val additionals: Set<AdditionalDto>
) {
    constructor(asset: Asset) : this(
        asset.id!!,
        asset.active,
        asset.name,
        asset.category.id!!,
        asset.price,
        asset.description,
        asset.ingredients.map { IngredientDto(it) }.toSet(),
        asset.additionals.map { AdditionalDto(it) }.toSet()
    )
}

class IngredientDto(
    val id: Long?,
    val name: String,
    val removable: Boolean
) {
    constructor(ingredient: Ingredient) : this(ingredient.id, ingredient.name, ingredient.removable)
}

class AdditionalDto(
    val id: Long?,
    val name: String,
    val price: BigDecimal
) {
    constructor(additional: Additional) : this(additional.id, additional.name, additional.price)
}
