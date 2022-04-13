package com.hphil.tavern.resources.manager

import com.hphil.tavern.domain.Spot
import com.hphil.tavern.repository.EstablishmentRepository
import com.hphil.tavern.repository.SpotRepository
import io.quarkus.security.Authenticated
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
class EstablishmentSpotsResource: ManagedEstablishmentTrait {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository
    @Inject
    lateinit var spotRepository: SpotRepository

    @POST
    @Transactional
    fun addSpot(@Context ctx: SecurityContext, request: AddSpotRequest) {
        spotRepository.persist(
                Spot(getEstablishment(establishmentRepository, ctx), request.name)
        )
    }

    @GET
    fun findAll(@Context ctx: SecurityContext): AllSpotResponse {
        return AllSpotResponse(
                spotRepository.list("establishment_id", getEstablishment(establishmentRepository, ctx))
                        .map { SpotDto(it) }
        )
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@Context ctx: SecurityContext, @PathParam("id") id: Long, request: UpdateSpotRequest) {
        val spot = spotRepository.findById(id)
        validateManager(spot.establishment, ctx)
        spot.name = request.name
        spot.active = request.active
    }

    class AddSpotRequest(val name: String)

    class AllSpotResponse(val spots: List<SpotDto>)
    class SpotDto(val id: Long, val name: String) {
        constructor(spot: Spot) : this(spot.id!!, spot.name)
    }

    class UpdateSpotRequest(val name: String, val active: Boolean)
}
