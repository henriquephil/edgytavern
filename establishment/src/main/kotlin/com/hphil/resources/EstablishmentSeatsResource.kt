package com.hphil.resources

import com.hphil.domain.Seat
import com.hphil.repository.EstablishmentRepository
import com.hphil.repository.SeatRepository
import io.quarkus.security.Authenticated
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/establishment/seats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class EstablishmentSeatsResource: ManagerEstablishmentTrait {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository

    @Inject
    lateinit var seatRepository: SeatRepository

    @POST
    @Transactional
    fun addSeat(@Context ctx: SecurityContext, request: AddSeatRequest) {
        seatRepository.persist(
                Seat(getEstablishment(establishmentRepository, ctx), request.name)
        )
    }

    @GET
    fun findAll(@Context ctx: SecurityContext): AllSeatsResponse {
        return AllSeatsResponse(
                seatRepository.list("establishment_id", getEstablishment(establishmentRepository, ctx))
                        .map { SeatDto(it) }
        )
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@Context ctx: SecurityContext, @PathParam("id") id: Long, request: UpdateSeatRequest) {
        val seat = seatRepository.findById(id)
        validateManager(seat.establishment, ctx)
        seat.name = request.name
        seat.active = request.active
    }

    class AddSeatRequest(val name: String)

    class AllSeatsResponse(val seats: List<SeatDto>)
    class SeatDto(val id: Long, val name: String) {
        constructor(seat: Seat) : this(seat.id!!, seat.name)
    }

    class UpdateSeatRequest(val name: String, val active: Boolean)
}
