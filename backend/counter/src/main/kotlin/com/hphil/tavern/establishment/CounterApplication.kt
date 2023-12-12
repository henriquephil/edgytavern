package com.hphil.tavern.establishment

import com.hphil.tavern.establishment.controller.admin.adminRoutes
import com.hphil.tavern.establishment.controller.customer.customerRoutes
import com.hphil.tavern.establishment.services.BillService
import com.hphil.tavern.establishment.services.QueuePublisher
import com.hphil.tavern.establishment.services.accessManager
import com.hphil.tavern.establishment.utils.setAppAttributes
import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.json.jsonMapper
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.StdOutSqlLogger

fun main() {
    Database.connect(
        datasource = HikariDataSource(HikariConfig("/datasource.properties")),
        databaseConfig = DatabaseConfig { sqlLogger = StdOutSqlLogger }) // TODO env setup
//    val mapper = jacksonObjectMapper()
//    mapper.registerModule(JavaTimeModule())
//    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//    Unirest.setObjectMapper(object : ObjectMapper {
//        override fun <T> readValue(value: String?, klass: Class<T>?): T = mapper.readValue(value, klass)
//        override fun writeValue(value: Any?): String? = mapper.writeValueAsString(value)
//    })

    // TODO implement request logger


    val queuePublisher = QueuePublisher()
    val billService = BillService(queuePublisher)

    val app = Javalin.create {
        it.accessManager(::accessManager)
//        it.jsonMapper(JavalinJackson(mapper)) MAYBE it is not needed with javalin-bundle dependency
    }
        .setAppAttributes(queuePublisher, billService)
        .routes {
            adminRoutes()
            customerRoutes()
        }
        .before { println("${it.method()} ${it.path()} ${it.body()}") }
        .start(8082)

    // TEST with javalin-bundle
//    val mapper = app.jsonMapper()
//    Unirest.setObjectMapper(object : ObjectMapper {
//        override fun <T> readValue(value: String, klass: Class<T>): T = mapper.fromJsonString(value, klass)
//        override fun writeValue(value: Any): String = mapper.toJsonString(value)
//    })
}
