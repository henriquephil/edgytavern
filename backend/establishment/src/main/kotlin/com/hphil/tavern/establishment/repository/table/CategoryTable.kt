package com.hphil.tavern.establishment.repository.table

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere

object CategoryTable : LongIdTable("category") {
    val establishment = reference("establishment_id", EstablishmentTable)
    val name = text("name")
    val active = bool("active")
    val order = integer("order")

    fun delete(establishmentId: Long, categoryId: Long) = deleteWhere {
        (establishment eq establishmentId) and (id eq categoryId)
    }
}
