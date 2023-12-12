package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object BillTable : LongIdTable("bill") {
    val tabCode = text("tab")
    val register = reference("register_id", RegisterTable)
    val open = bool("open")
    val started = datetime("started")
    val ended = datetime("ended").nullable()
    val session = uuid("session").nullable()
}