package com.hphil.tavern.resources.open

import com.hphil.tavern.domain.Asset
import com.hphil.tavern.repository.AssetRepository
import io.quarkus.security.Authenticated
import org.hashids.Hashids
import java.math.BigDecimal
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/{establishmentHash}/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class AssetsResource {
    @Inject
    lateinit var assetRepository: AssetRepository
    @Inject
    lateinit var hashids: Hashids

    @GET
    @Path("/{assetHash}")
    fun getAssetByHash(@Context ctx: SecurityContext,
                       @PathParam("establishmentHash") establishmentHash: String,
                       @PathParam("assetHash") assetHash: String): AssetResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        val assetId = hashids.decode(assetHash)[0]
        // using establishment.id as filter to assert that the received hash is valid
        return assetRepository.find("establishment.id = ?1 and establishment.active = true and id = ?2 and active = true", establishmentId, assetId)
                .firstResultOptional<Asset>()
                .map { asset ->
                    AssetResponse(assetHash,
                            asset.name,
                            AssetCategoryResponse(hashids.encode(asset.category.id!!), asset.category.name),
                            asset.price,
                            asset.description,
                            asset.ingredients.map { AssetIngredientResponse(hashids.encode(it.id!!), it.name) },
                            asset.additionals.map { AssetAdditionalResponse(hashids.encode(it.id!!), it.name, it.price) })
                }
                .orElse(null)
    }

    class AssetResponse(val hashId: String,
                        val name: String,
                        val category: AssetCategoryResponse,
                        val price: BigDecimal,
                        val description: String?,
                        val ingredients: List<AssetIngredientResponse>,
                        val additionals: List<AssetAdditionalResponse>)

    class AssetCategoryResponse(val hashId: String, val name: String)
    class AssetIngredientResponse(val hashId: String, val name: String)
    class AssetAdditionalResponse(val hashId: String, val name: String, val price: BigDecimal)
}