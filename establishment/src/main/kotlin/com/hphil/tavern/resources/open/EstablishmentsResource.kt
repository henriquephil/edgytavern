package com.hphil.tavern.resources.open

import com.hphil.tavern.domain.Establishment
import com.hphil.tavern.repository.EstablishmentRepository
import io.quarkus.security.Authenticated
import org.hashids.Hashids
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class EstablishmentsResource {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository
    @Inject
    lateinit var hashids: Hashids

    @GET
    fun getEstablishments(@QueryParam("active") active: Boolean): List<EstablishmentResponse> {
        return establishmentRepository.list("active = ?1", active)
                .map { EstablishmentResponse(hashids.encode(it.id!!), it.name) }
    }

    @GET
    @Path("/{establishmentHash}")
    fun getEstablishmentByHash(@Context ctx: SecurityContext,
                               @PathParam("establishmentHash") establishmentHash: String): EstablishmentResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        return establishmentRepository.find("id = ?1 and active = true", establishmentId)
                .firstResultOptional<Establishment>()
                .map { EstablishmentResponse(hashids.encode(it.id!!), it.name) }
                .orElse(null)
    }

    class EstablishmentResponse(val hashId: String, val name: String)
}