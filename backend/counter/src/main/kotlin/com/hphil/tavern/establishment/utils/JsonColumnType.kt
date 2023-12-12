package com.hphil.tavern.establishment.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.IColumnType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import kotlin.reflect.KClass

fun <T : Any> Table.json(
    name: String,
    klass: KClass<T>,
    jsonMapper: ObjectMapper = jacksonObjectMapper(),
    nullable: Boolean = true): Column<T> {
    return registerColumn(name, JsonColumnType(klass, jsonMapper, nullable))
}

class JsonColumnType<out T : Any>(
    private val klass: KClass<T>,
    private val jsonMapper: ObjectMapper,
    override var nullable: Boolean
) : IColumnType {
//
//    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
//        val obj = PGobject()
//        obj.type = "json"
//        if (value != null)
//            obj.value = jsonMapper.writeValueAsString(value)
//        stmt[index] = obj
//    }

    override fun valueFromDB(value: Any): Any = when(value) {
        is String -> jsonMapper.readValue(value, klass.java)
        is PGobject -> jsonMapper.readValue(value.value, klass.java)
        else -> error("unexpected type")
    }

    override fun notNullValueToDB(value: Any): Any {
        val obj = PGobject()
        obj.type = "json"
        obj.value = jsonMapper.writeValueAsString(value)
        return obj
    }

    override fun sqlType() = "json"
}
