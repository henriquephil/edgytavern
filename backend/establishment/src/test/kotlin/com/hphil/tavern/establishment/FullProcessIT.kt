package com.hphil.tavern.establishment

import com.hphil.tavern.establishment.client.AuthClient
import com.hphil.tavern.establishment.client.UserInfo
import com.hphil.tavern.establishment.controller.admin.*
import com.hphil.tavern.establishment.repository.entity.CategoryEntity
import com.hphil.tavern.establishment.repository.entity.EstablishmentEntity
import com.hphil.tavern.establishment.repository.table.*
import com.hphil.tavern.establishment.util.getAdminEstablishment
import io.javalin.http.Context
import io.javalin.json.jsonMapper
import io.javalin.testtools.HttpClient
import io.javalin.testtools.JavalinTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
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

        private val establishmentName: String = UUID.randomUUID().toString()
        private val establishmentOwnerId: String = UUID.randomUUID().toString()

        @JvmStatic
        @BeforeClass
        fun before() {
            initTestDatabase()

            mockkObject(AuthClient)
            val userInfo = UserInfo(establishmentOwnerId, "mock", "mock")
            every { AuthClient.getUserInfo(any()) }.returns(userInfo)
        }
    }

    @Test
    fun `create establishment data`() = JavalinTest.test(app) { _, client ->
        createAndGetEstablishment(client)

        val category = createAndGetCategories(client)

        createAndGetAssets(client, category.id)

        createAndGetSpot(client)
    }

    private fun createAndGetEstablishment(client: HttpClient): EstablishmentResponse {
        assertEquals(200, client.post("/admin", CreateEstablishmentRequest(establishmentName)).code)

        val establishment = client.getAs("/admin", EstablishmentResponse::class)
        assertEquals(establishmentName, establishment.name)

        return establishment
    }

    private fun createAndGetCategories(client: HttpClient): CategoryDto {
        assertEquals(200, client.post("/admin/categories", AddCategoryRequest("hamburguer")).code)

        val categories = client.getAs("/admin/categories", Array<CategoryDto>::class)
        assert(categories.size == 1)

        return categories[0]
    }

    private fun createAndGetAssets(client: HttpClient, categoryId: Long): AssetDto {
        val request = AddAssetRequest(
            "double bacon",
            categoryId,
            BigDecimal.TEN,
            "2x 300 grams burguer, chesse and bacon",
            setOf(AddIngredient("bread", true), AddIngredient("chesse", true), AddIngredient("bacon", false)),
            setOf(
                AddAdditional("cheese", BigDecimal.ONE),
                AddAdditional("bacon", BigDecimal.ONE),
                AddAdditional("picles", BigDecimal.ONE),
                AddAdditional("onions", BigDecimal.ONE)
            )
        )
        assertEquals(200, client.post("/admin/assets", request).code)

        val assets = client.getAs("/admin/assets", Array<AssetHeaderDto>::class)
        assertEquals(1, assets.size)

        val asset = client.getAs("/admin/assets/${assets[0].id}", AssetDto::class)
        assertEquals("double bacon", asset.name)
        assertEquals(3, asset.ingredients.size)
        assertEquals(4, asset.additionals.size)

        return asset
    }

    private fun createAndGetSpot(client: HttpClient) {
        assertEquals(200, client.post("/admin/spots", AddSpotRequest("porch", 6)).code)

        val spots = client.getAs("/admin/spots", Array<SpotGroupDto>::class)
        assertEquals(1, spots.size)

        val spot = client.getAs("/admin/spots/${spots[0].id}", SpotDto::class)
        assertEquals("porch", spot.group)
        assertEquals(6, spot.qrCodes.size)
    }

}

fun <T : Any> HttpClient.getAs(url: String, clazz: KClass<T>): T {
    return get(url).body!!.let { app.jsonMapper().fromJsonString(it.string(), clazz.java) }
}
