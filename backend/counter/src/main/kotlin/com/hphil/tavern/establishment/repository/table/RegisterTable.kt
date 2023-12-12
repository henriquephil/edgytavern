package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object RegisterTable : LongIdTable("register") {
    val establishmentHash = text("establishment_hash")
    val open = bool("open")
    val started = datetime("started")
    val ended = datetime("ended").nullable()
}