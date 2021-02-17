package com.hphil.repository

import com.hphil.domain.Seat
import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SeatRepository: PanacheRepository<Seat> {
}