package com.hphil.resources

import com.hphil.domain.Additional
import com.hphil.domain.Asset
import com.hphil.domain.Category
import com.hphil.domain.Ingredient
import com.hphil.repository.AssetRepository
import com.hphil.repository.CategoryRepository
import com.hphil.repository.EstablishmentRepository
import com.hphil.util.MergeUtil
import com.hphil.util.MergeUtil.updateChildSet
import com.hphil.util.PanacheQueryFilter
import io.quarkus.security.Authenticated
import java.math.BigDecimal
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/establishment/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class EstablishmentAssetsResource : ManagerEstablishmentTrait {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository

    @Inject
    lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var assetRepository: AssetRepository

    @POST
    @Transactional
    fun add(@Context ctx: SecurityContext, request: AddAssetRequest) {
        assetRepository.persist(
                Asset(getEstablishment(establishmentRepository, ctx),
                        request.name,
                        parseCategory(request.categoryId),
                        request.price,
                        request.description,
                        request.ingredients.map { Ingredient(it.name, it.removable) }.toMutableSet(),
                        request.additionals.map { Additional(it.name, it.price) }.toMutableSet()
                )
        )
    }

    @GET
    fun findAll(@Context ctx: SecurityContext, @QueryParam("categoryId") categoryId: Long?): AllAssetsResponse {
        val params = PanacheQueryFilter.where(
                Pair("establishment_id", getEstablishment(establishmentRepository, ctx).id),
                Pair("category_id", categoryId)
        )
        return AllAssetsResponse(
                assetRepository.list(params.first, params.second).map { AssetDto(it) }
        )
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@Context ctx: SecurityContext, @PathParam("id") id: Long, request: UpdateAssetRequest) {
        val asset = assetRepository.findById(id)
        validateManager(asset.establishment, ctx)
        asset.name = request.name
        asset.category = parseCategory(request.categoryId)
        asset.price = request.price
        asset.description = request.description
        asset.active = request.active
        asset.ingredients.sortedBy { it.id }
        updateChildSet(asset.ingredients, Ingredient::id, request.ingredients, UpdateIngredient::id,
                { Ingredient(it.name, it.removable) },
                { o, n ->
                    o.name = n.name
                    o.removable = n.removable
                }
        )
        updateChildSet(asset.additionals, Additional::id, request.additionals, UpdateAdditional::id,
                { Additional(it.name, it.price) },
                { o, n ->
                    o.name = n.name
                    o.price = n.price
                }
        )
    }

    private fun parseCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId) ?: error("Category does not exist")
    }

    class AddAssetRequest(
            val name: String,
            val categoryId: Long,
            val price: BigDecimal,
            val description: String?,
            val ingredients: Set<AddIngredient> = HashSet(),
            val additionals: Set<AddAdditional> = HashSet())

    class AddIngredient(val name: String, val removable: Boolean)
    class AddAdditional(val name: String, val price: BigDecimal)

    class UpdateAssetRequest(
            val name: String,
            val categoryId: Long,
            val price: BigDecimal,
            val description: String?,
            val active: Boolean,
            val ingredients: Set<UpdateIngredient> = HashSet(),
            val additionals: Set<UpdateAdditional> = HashSet())

    class UpdateIngredient(val id: Long?, override val markedForRemoval: Boolean, val name: String, val removable: Boolean): MergeUtil.RemovalIndicator
    class UpdateAdditional(val id: Long?, override val markedForRemoval: Boolean, val name: String, val price: BigDecimal): MergeUtil.RemovalIndicator

    class AllAssetsResponse(val assets: List<AssetDto>)
    class AssetDto(val id: Long, val active: Boolean, val name: String, val categoryId: Long, val price: BigDecimal, val description: String?,
                   val ingredients: Set<IngredientDto>, val additionals: Set<AdditionalDto>) {
        constructor(asset: Asset) : this(asset.id!!, asset.active, asset.name, asset.category.id!!, asset.price, asset.description,
                asset.ingredients.map { IngredientDto(it) }.toSet(), asset.additionals.map { AdditionalDto(it) }.toSet())
    }
    class IngredientDto(val id: Long?, val name: String, val removable: Boolean) {
        constructor(ingredient: Ingredient) : this(ingredient.id, ingredient.name, ingredient.removable)
    }
    class AdditionalDto(val id: Long?, val name: String, val price: BigDecimal) {
        constructor(additional: Additional) : this(additional.id, additional.name, additional.price)
    }
}
