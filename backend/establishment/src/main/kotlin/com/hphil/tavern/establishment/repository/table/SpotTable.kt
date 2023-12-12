package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere

object SpotTable : LongIdTable("spot") {
    val establishment = reference("establishment_id", EstablishmentTable)
    val group = text("group")
    val quantity = integer("quantity")
    fun delete(establishmentId: Long, spotId: Long) = deleteWhere {
        (establishment.eq(establishmentId)) and (id eq spotId)
    }
}
