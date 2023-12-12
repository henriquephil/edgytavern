package com.hphil.tavern.establishment

import com.hphil.tavern.establishment.repository.table.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

fun initTestDatabase() {
    if (!TransactionManager.isInitialized()) {
        print("Database is connected")
        Database.connect(
            datasource = HikariDataSource(HikariConfig().apply {
                driverClassName = "org.h2.Driver"
                jdbcUrl = "jdbc:h2:mem:test"
            }),
            databaseConfig = DatabaseConfig { sqlLogger = StdOutSqlLogger }
        )
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
    }
}