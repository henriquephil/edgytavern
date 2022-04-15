package com.hphil.tavern.orders.resources.manager

import com.hphil.tavern.domain.Establishment
import com.hphil.tavern.orders.repository.OrderRepository
import io.quarkus.security.Authenticated
import org.hashids.Hashids
import java.time.LocalDateTime
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class OrdersResource {
    @Inject
    lateinit var orderRepository: OrderRepository
    @Inject
    lateinit var hashids: Hashids

    @GET
    fun getSince(@QueryParam("since") since: LocalDateTime): OrdersResponse {

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

    class OrdersResponse()
}