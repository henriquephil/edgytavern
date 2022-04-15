package com.hphil.tavern.resources.manager

import com.hphil.tavern.domain.*
import com.hphil.tavern.repository.EstablishmentRepository
import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal
import io.quarkus.security.Authenticated
import org.hashids.Hashids
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/managed")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class EstablishmentResource: ManagedEstablishmentTrait {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository
    @Inject
    lateinit var hashids: Hashids

//    @Inject
//    lateinit var identity: SecurityIdentity // another way of getting the user

    @POST
    @Transactional
    fun create(@Context ctx: SecurityContext, request: CreateEstablishmentRequest) {
        assert(request.name.isNotEmpty()) { "Name not set" }
        val user = (ctx.userPrincipal as OidcJwtCallerPrincipal).subject
        establishmentRepository.find("managerUid", user).firstResultOptional<Establishment>()
                .ifPresent { error("User already has an establishment") }
        establishmentRepository.persist(Establishment(request.name, user))
    }

    @GET
    fun getManagedEstablishment(@Context ctx: SecurityContext): EstablishmentResponse? {
        val user = (ctx.userPrincipal as OidcJwtCallerPrincipal).subject
        return establishmentRepository.find("managerUid", user).firstResultOptional<Establishment>()
                .map { EstablishmentResponse(hashids.encode(it.id!!), it.name, it.active) }
                .orElse(null)
    }

    @PUT
    @Transactional
    fun update(@Context ctx: SecurityContext, request: UpdateEstablishmentRequest) {
        val establishment = getEstablishment(establishmentRepository, ctx)
        establishment.name = request.name
        establishment.active = request.active
    }
}

class CreateEstablishmentRequest(val name: String)
class UpdateEstablishmentRequest(val name: String, val active: Boolean)
class EstablishmentResponse(val hashId: String, val name: String, val active: Boolean)
