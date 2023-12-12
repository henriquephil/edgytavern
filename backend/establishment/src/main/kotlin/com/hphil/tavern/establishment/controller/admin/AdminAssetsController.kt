package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.repository.entity.*
import com.hphil.tavern.establishment.util.UpdatableRequestEntity
import com.hphil.tavern.establishment.util.getAdminEstablishment
import com.hphil.tavern.establishment.util.updateWith
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

class AdminAssetsController {
    fun create(ctx: Context) {
        val request = ctx.bodyAsClass(AddAssetRequest::class.java)
        transaction {
            val establishment = ctx.getAdminEstablishment()
            val asset = AssetEntity.new(
                establishment,
                request.name,
                parseCategory(establishment, request.categoryId),
                request.price,
                request.description
            )
            request.ingredients.forEach {
                IngredientEntity.new(asset, it.name, it.removable)
            }
            request.additionals.forEach {
                AdditionalEntity.new(asset, it.name, it.price)
            }
        }
    }

    fun get(ctx: Context) {
        val categoryId = ctx.queryParamAsClass("categoryId", Long::class.java).allowNullable().get()
        transaction {
            ctx.json(
                AssetEntity.findByEstablishmentAndCategoryId(ctx.getAdminEstablishment(), categoryId)
                    .map { AssetHeaderDto(it) }
            )
        }
    }

    fun update(ctx: Context) {
        val assetId = ctx.pathParamAsClass("assetId", Long::class.java).get()
        val request = ctx.bodyAsClass(UpdateAssetRequest::class.java)

        transaction {
            val establishment = ctx.getAdminEstablishment()
            val asset = AssetEntity.findByEstablishmentAndId(establishment, assetId)
            asset.update(
                request.name,
                parseCategory(establishment, request.categoryId),
                request.price,
                request.description,
                request.active
            )
            IngredientEntity.findByAsset(asset).updateWith(
                request.ingredients,
                { IngredientEntity.new(asset, it.name, it.removable) },
                { e, req -> e.update(req.name, req.removable) }
            )
            AdditionalEntity.findByAsset(asset).updateWith(
                request.additionals,
                { AdditionalEntity.new(asset, it.name, it.price) },
                { e, req -> e.update(req.name, req.price) }
            )
        }
    }

    fun getById(ctx: Context) {
        val assetId = ctx.pathParamAsClass("assetId", Long::class.java).get()
        transaction {
            val asset = AssetEntity.findByEstablishmentAndId(ctx.getAdminEstablishment(), assetId)
            ctx.json(
                AssetDto(
                    asset,
                    IngredientEntity.findByAsset(asset).map { IngredientDto(it) },
                    AdditionalEntity.findByAsset(asset).map { AdditionalDto(it) }
                )
            )
        }
    }
}

private fun parseCategory(establishment: EstablishmentEntity, categoryId: Long) = CategoryEntity.findByEstablishmentAndId(establishment, categoryId) ?: error("Category does not exist")

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
    val ingredients: List<UpdateIngredient>,
    val additionals: List<UpdateAdditional>
)

class UpdateIngredient(
    val id: Long?,
    override val markedForRemoval: Boolean,
    val name: String,
    val removable: Boolean
) : UpdatableRequestEntity<Long> {
    override fun getKey() = id
}

class UpdateAdditional(
    val id: Long?,
    override val markedForRemoval: Boolean,
    val name: String,
    val price: BigDecimal
) : UpdatableRequestEntity<Long> {
    override fun getKey() = id
}

class AssetHeaderDto(
    val id: Long,
    val active: Boolean,
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val description: String?
) {
    constructor(asset: AssetEntity) : this(
        asset.id.value,
        asset.active,
        asset.name,
        asset.category.value,
        asset.price,
        asset.description
    )
}

class AssetDto(
    val id: Long,
    val active: Boolean,
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val description: String?,
    val ingredients: List<IngredientDto>,
    val additionals: List<AdditionalDto>
) {
    constructor(
        asset: AssetEntity,
        ingredients: List<IngredientDto>,
        additionals: List<AdditionalDto>
    ) : this(
        asset.id.value,
        asset.active,
        asset.name,
        asset.category.value,
        asset.price,
        asset.description,
        ingredients,
        additionals
    )
}

class IngredientDto(
    val id: Long?,
    val name: String,
    val removable: Boolean
) {
    constructor(ingredient: IngredientEntity) : this(ingredient.id.value, ingredient.name, ingredient.removable)
}

class AdditionalDto(
    val id: Long?,
    val name: String,
    val price: BigDecimal
) {
    constructor(additional: AdditionalEntity) : this(additional.id.value, additional.name, additional.price)
}
