package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable

object TabTable : LongIdTable("tab") {
    val establishment = reference("establishment_id", EstablishmentTable)
    val code = text("code")
    val active = bool("active")
}
