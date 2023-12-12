package com.hphil.tavern.establishment

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.hphil.tavern.establishment.controller.admin.AdminAssetsController
import com.hphil.tavern.establishment.controller.admin.AdminCategoriesController
import com.hphil.tavern.establishment.controller.admin.AdminEstablishmentController
import com.hphil.tavern.establishment.controller.admin.AdminSpotsController
import com.hphil.tavern.establishment.repository.table.*
import com.mashape.unirest.http.ObjectMapper
import com.mashape.unirest.http.Unirest
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.jsonMapper
import io.javalin.json.toJsonString
import org.hashids.Hashids
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.transactions.transaction

class EstablishmentApplication {
    val app: Javalin

    init {
        val hashids = Hashids("salt", 10)

        val adminEstablishmentController = AdminEstablishmentController(hashids)
        val adminCategoriesController = AdminCategoriesController()
        val adminAssetsController = AdminAssetsController()
        val adminSpotsController = AdminSpotsController(hashids)

        app = Javalin.create {
//            it.jsonMapper(JavalinGson())
        }
            .routes {
                path("admin") {
                    post(adminEstablishmentController::create)
                    get(adminEstablishmentController::get)
                    put(adminEstablishmentController::update)
                    path("categories") {
                        post(adminCategoriesController::create)
                        get(adminCategoriesController::get)
                        path("<categoryId>") {
                            put(adminCategoriesController::update)
                            delete(adminCategoriesController::delete)
                        }
                    }
                    path("assets") {
                        post(adminAssetsController::create)
                        get(adminAssetsController::get)
                        path("<assetId>") {
                            put(adminAssetsController::update)
                            get(adminAssetsController::getById)
                        }
                    }
                    path("spots") {
                        post(adminSpotsController::create)
                        get(adminSpotsController::get)
                        path("<spotId>") {
                            put(adminSpotsController::update)
                            get(adminSpotsController::getById)
                            delete(adminSpotsController::delete)
                        }
                    }
                }
            }
        // TEST with javalin-bundle
//    val mapper = app.jsonMapper()
//    Unirest.setObjectMapper(object : ObjectMapper {
//        override fun <T> readValue(value: String, klass: Class<T>): T = mapper.fromJsonString(value, klass)
//        override fun writeValue(value: Any): String = mapper.toJsonString(value)
//    })
    val mapper = jacksonObjectMapper()
    mapper.registerModule(JavaTimeModule())
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    Unirest.setObjectMapper(object : ObjectMapper {
        override fun <T> readValue(value: String?, klass: Class<T>?): T = mapper.readValue(value, klass)
        override fun writeValue(value: Any?): String? = mapper.writeValueAsString(value)
    })
    }

    fun start(): Javalin = app.start(8081)
}

fun main() {
    Database.connect(
        datasource = HikariDataSource(HikariConfig("/datasource.properties")),
        databaseConfig = DatabaseConfig { sqlLogger = StdOutSqlLogger }) // TODO env setup
    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            EstablishmentTable,
            CategoryTable,
            AssetTable,
            AdditionalTable,
            IngredientTable,
            SpotTable
        )
    }

    EstablishmentApplication()
        .start()
}
