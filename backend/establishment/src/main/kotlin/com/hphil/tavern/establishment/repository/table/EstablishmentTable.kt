package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object EstablishmentTable : LongIdTable("establishment") {
    val name = text("name")
    val ownerId = text("owner_id")
    val active = bool("active")
    val created = datetime("created")
}
