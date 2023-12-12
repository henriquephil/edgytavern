package com.hphil.tavern.establishment.controller.admin

import com.fasterxml.jackson.databind.JsonNode
import com.hphil.tavern.establishment.EstablishmentApplication
import com.hphil.tavern.establishment.FullProcessIT
import com.hphil.tavern.establishment.client.AuthClient
import com.hphil.tavern.establishment.client.UserInfo
import com.hphil.tavern.establishment.controller.admin.*
import com.hphil.tavern.establishment.initTestDatabase
import com.hphil.tavern.establishment.repository.entity.CategoryEntity
import com.hphil.tavern.establishment.repository.entity.EstablishmentEntity
import com.hphil.tavern.establishment.repository.table.*
import com.hphil.tavern.establishment.util.getAdminEstablishment
import io.javalin.http.Context
import io.javalin.json.JavalinJackson
import io.javalin.json.jsonMapper
import io.javalin.testtools.HttpClient
import io.javalin.testtools.JavalinTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.math.BigDecimal
import java.util.*
import kotlin.reflect.KClass
import kotlin.test.assertEquals

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FullProcessIT {
    companion object {
        private val app = EstablishmentApplication().app

        private val ownerId = UUID.randomUUID().toString()

        private val establishmentName: String = UUID.randomUUID().toString()
        private val establishmentOwnerId: String = UUID.randomUUID().toString()

        private lateinit var establishment: EstablishmentEntity
        private const val CATEGORY_HAMBURGUER = "Hamburguer"
        private const val CATEGORY_BEER = "Beer"
        private const val CATEGORY_DRINKS = "Drinks"
        private val categories = mutableMapOf<String, Long>()

        @JvmStatic
        @BeforeClass
        fun before() {
            initTestDatabase()

            transaction {
                establishment = EstablishmentEntity.new("AssetsTest", ownerId)
                CategoryEntity.new(establishment, CATEGORY_HAMBURGUER).also { categories[it.name] = it.id.value }
                CategoryEntity.new(establishment, CATEGORY_BEER).also { categories[it.name] = it.id.value }
                CategoryEntity.new(establishment, CATEGORY_DRINKS).also { categories[it.name] = it.id.value }
            }

            mockkObject(AuthClient)
            val userInfo = UserInfo(establishmentOwnerId, "mock", "mock")
            every { AuthClient.getUserInfo(any()) }.returns(userInfo)
        }
    }

    @Test
    fun `1 - Create multiple assets`() = JavalinTest.test(app) { _, client ->
        listOf(
            AddAssetRequest(
                "Double bacon",
                categories[CATEGORY_HAMBURGUER]!!,
                BigDecimal.TEN,
                "Two 160 grams hamburguer, chesse and bacon",
                setOf(AddIngredient("bread", true), AddIngredient("chesse", true), AddIngredient("bacon", false)),
                setOf(
                    AddAdditional("cheese", BigDecimal.ONE),
                    AddAdditional("bacon", BigDecimal.ONE),
                    AddAdditional("picles", BigDecimal.ONE),
                    AddAdditional("onions", BigDecimal.ONE)
                )
            ),
            AddAssetRequest(
                "Cheesful",
                categories[CATEGORY_HAMBURGUER]!!,
                BigDecimal.TEN,
                "160 grams hamburguer and a lot of chesse",
                setOf(),
                setOf(
                    AddAdditional("cheese", BigDecimal.ONE),
                    AddAdditional("bacon", BigDecimal.ONE),
                    AddAdditional("picles", BigDecimal.ONE),
                    AddAdditional("onions", BigDecimal.ONE)
                )
            ),



            AddAssetRequest(
                "Double bacon",
                categories[CATEGORY_HAMBURGUER]!!,
                BigDecimal.TEN,
                "2x 300 grams burguer, chesse and bacon",
                setOf(AddIngredient("bread", true), AddIngredient("chesse", true), AddIngredient("bacon", false)),
                setOf(
                    AddAdditional("cheese", BigDecimal.ONE),
                    AddAdditional("bacon", BigDecimal.ONE),
                    AddAdditional("picles", BigDecimal.ONE),
                    AddAdditional("onions", BigDecimal.ONE)
                )
            ),
            AddAssetRequest(
                "Double bacon",
                categories[CATEGORY_HAMBURGUER]!!,
                BigDecimal.TEN,
                "2x 300 grams burguer, chesse and bacon",
                setOf(AddIngredient("bread", true), AddIngredient("chesse", true), AddIngredient("bacon", false)),
                setOf(
                    AddAdditional("cheese", BigDecimal.ONE),
                    AddAdditional("bacon", BigDecimal.ONE),
                    AddAdditional("picles", BigDecimal.ONE),
                    AddAdditional("onions", BigDecimal.ONE)
                )
            ),
            AddAssetRequest(
                "Double bacon",
                categories[CATEGORY_HAMBURGUER]!!,
                BigDecimal.TEN,
                "2x 300 grams burguer, chesse and bacon",
                setOf(AddIngredient("bread", true), AddIngredient("chesse", true), AddIngredient("bacon", false)),
                setOf(
                    AddAdditional("cheese", BigDecimal.ONE),
                    AddAdditional("bacon", BigDecimal.ONE),
                    AddAdditional("picles", BigDecimal.ONE),
                    AddAdditional("onions", BigDecimal.ONE)
                )
            ),
            AddAssetRequest(
                "Double bacon",
                categories[CATEGORY_HAMBURGUER]!!,
                BigDecimal.TEN,
                "2x 300 grams burguer, chesse and bacon",
                setOf(AddIngredient("bread", true), AddIngredient("chesse", true), AddIngredient("bacon", false)),
                setOf(
                    AddAdditional("cheese", BigDecimal.ONE),
                    AddAdditional("bacon", BigDecimal.ONE),
                    AddAdditional("picles", BigDecimal.ONE),
                    AddAdditional("onions", BigDecimal.ONE)
                )
            ),
        )
        assertEquals(200, client.post("/admin/assets", CreateEstablishmentRequest(establishmentName)).code)
    }

}

fun <T : Any> HttpClient.getAs(url: String, clazz: KClass<T>): T {
    return get(url).body!!.let { app.jsonMapper().fromJsonString(it.string(), clazz.java) }
}

fun HttpClient.getAsJsonNode(url: String): JsonNode {
    return get(url).body!!.let { (app.jsonMapper() as JavalinJackson).mapper.readTree(it.string()) }
}
