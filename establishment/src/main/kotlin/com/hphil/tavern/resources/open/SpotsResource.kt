package com.hphil.tavern.resources.open

import com.hphil.tavern.domain.Spot
import com.hphil.tavern.repository.SpotRepository
import io.quarkus.security.Authenticated
import org.hashids.Hashids
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/{establishmentHash}/spots")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class SpotsResource {
    @Inject
    lateinit var spotRepository: SpotRepository
    @Inject
    lateinit var hashids: Hashids

    @GET
    @Path("/{spotHash}")
    fun getSpotByHash(@Context ctx: SecurityContext,
                      @PathParam("establishmentHash") establishmentHash: String,
                      @PathParam("spotHash") spotHash: String): SpotResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        val spotId = hashids.decode(spotHash)[0]
        // using establishment.id as filter to assert that the received hash is valid
        return spotRepository.find("establishment.id = ?1 and establishment.active = true and id = ?2 and active = true", establishmentId, spotId)
                .firstResultOptional<Spot>()
                .map { SpotResponse(it) }
                .orElse(null)
    }

    class SpotResponse(val name: String){
        constructor(spot: Spot) : this(spot.name)
    }
}