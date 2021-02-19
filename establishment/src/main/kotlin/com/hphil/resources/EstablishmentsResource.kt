package com.hphil.resources

import com.hphil.domain.Establishment
import com.hphil.repository.EstablishmentRepository
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
    fun getEstablishments(@Context ctx: SecurityContext,
                               @PathParam("establishmentHash") establishmentHash: String): EstablishmentResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        return establishmentRepository.find("id = ?1 and active = true", establishmentId)
                .firstResultOptional<Establishment>()
                .map { EstablishmentResponse(it) }
                .orElse(null)
    }

    @GET
    @Path("/{establishmentHash}")
    fun getEstablishmentByHash(@Context ctx: SecurityContext,
                               @PathParam("establishmentHash") establishmentHash: String): EstablishmentResponse? {
        val establishmentId = hashids.decode(establishmentHash)[0]
        return establishmentRepository.find("id = ?1 and active = true", establishmentId)
                .firstResultOptional<Establishment>()
                .map { EstablishmentResponse(it) }
                .orElse(null)
    }

    class EstablishmentResponse(val name: String) {
        constructor(establishment: Establishment) : this(establishment.name)
    }
}