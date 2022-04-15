package com.hphil.tavern.resources.manager

import com.hphil.tavern.domain.Spot
import com.hphil.tavern.repository.EstablishmentRepository
import com.hphil.tavern.repository.SpotRepository
import com.hphil.tavern.services.AsyncFileStorage
import io.quarkus.security.Authenticated
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.hashids.Hashids
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext


@Path("/managed/spots")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class EstablishmentSpotsResource : ManagedEstablishmentTrait {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository
    @Inject
    lateinit var spotRepository: SpotRepository
    @Inject
    lateinit var hashIds: Hashids
    @Inject
    lateinit var asyncFileStorage: AsyncFileStorage

    @Inject
    @ConfigProperty(name = "app.baseUrl", defaultValue = "http://localhost:3000")
    private val url = "http://localhost:3000";

    @POST
    @Transactional
    fun addSpot(@Context ctx: SecurityContext, request: AddSpotRequest) {
        val spot = Spot(
            establishment = getEstablishment(establishmentRepository, ctx),
            group = request.group,
            name = request.name
        )
        spotRepository.persistAndFlush(spot)
        spotImage(spot)
    }

    private fun spotImage(spot: Spot) {
        val establishmentHash = hashIds.encode(spot.establishment.id!!)
        val spotHash = hashIds.encode(spot.id!!)
        spot.qrCode = "$establishmentHash.$spotHash"

        asyncFileStorage.generateAndStoreQrCodeImage("$url/e=$establishmentHash&s=$spotHash", spot.qrCode!!)
    }

    @GET
    fun findAll(@Context ctx: SecurityContext): AllSpotResponse {
        return AllSpotResponse(spotRepository
            .list("establishment_id", getEstablishment(establishmentRepository, ctx))
            .map { SpotDto(it) }
        )
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@Context ctx: SecurityContext, @PathParam("id") id: Long, request: UpdateSpotRequest) {
        val spot = spotRepository.findById(id)
        validateManager(spot.establishment, ctx)

        spot.group = request.group
        spot.name = request.name
        spot.active = request.active
    }

}

class AddSpotRequest(val group: String, val name: String)

class AllSpotResponse(val spots: List<SpotDto>)
class SpotDto(val id: Long, val group: String, val name: String, val qrCode: String?) {
    constructor(spot: Spot) : this(spot.id!!, spot.group, spot.name, spot.qrCode)
}

class UpdateSpotRequest(val group: String, val name: String, val active: Boolean)
