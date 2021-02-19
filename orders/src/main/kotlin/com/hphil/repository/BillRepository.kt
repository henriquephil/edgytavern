package com.hphil.repository

import com.hphil.domain.Bill
import io.quarkus.hibernate.orm.panache.PanacheRepository
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BillRepository: PanacheRepository<Bill> {
    fun findOpen(userUid: String): Optional<Bill> = find("user_uid = ?1 and open = true", userUid).firstResultOptional<Bill>()
    fun openBillsAt(establishmentHashId: String) = list("establishment_hash = ?1 and open = true", establishmentHashId)
}