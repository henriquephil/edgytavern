package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.initTestDatabase
import com.hphil.tavern.establishment.repository.entity.*
import com.hphil.tavern.establishment.util.getAdminEstablishment
import io.javalin.http.Context
import io.javalin.validation.Validator
import io.mockk.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertTrue

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class AdminAssetsControllerTest {
    companion object {
        val adminAssetsController = AdminAssetsController()

        val ctx = mockk<Context>(relaxed = true)

        private val ownerId = UUID.randomUUID().toString()

        lateinit var establishment1: EstablishmentEntity
        lateinit var categoryHamburguers: CategoryEntity

        @JvmStatic
        @BeforeClass
        fun before() {
            initTestDatabase()
            transaction {
                establishment1 = EstablishmentEntity.new("Test", ownerId)
                categoryHamburguers = CategoryEntity.new(establishment1, "Hamburguers")
            }

            every { ctx.getAdminEstablishment() } returns establishment1
        }
    }

    @Test
    fun `1 - POST to create asset returns ok`() {
        mockkObject(AssetEntity)
        mockkObject(IngredientEntity)
        mockkObject(AdditionalEntity)

        // given
        every { ctx.bodyAsClass(AddAssetRequest::class.java) } returns AddAssetRequest(
            "Test",
            categoryHamburguers.id.value,
            BigDecimal.TEN,
            "description",
            setOf(AddIngredient("ingredient", true)),
            setOf(AddAdditional("additional", BigDecimal.ONE))
        )

        // when
        adminAssetsController.create(ctx)

        // then
        verify {
            AssetEntity.new(
                establishment1,
                "Test",
                match { it.id == categoryHamburguers.id },
                BigDecimal.TEN,
                "description"
            )
        }
        verify { IngredientEntity.new(any(), "ingredient", true) }
        verify { AdditionalEntity.new(any(), "additional", BigDecimal.ONE) }
    }

    @Test
    fun `2 - GET asset created without categoryId`() {
        val getAssetResponse = slot<List<AssetHeaderDto>>()

        // given
        every { ctx.queryParamAsClass("categoryId", Long::class.java) } returns Validator.create(
            Long::class.java,
            null,
            "categoryId"
        )
        every { ctx.json(capture(getAssetResponse)) } answers { ctx }

        // when
        adminAssetsController.get(ctx)

        // then
        assertTrue { getAssetResponse.captured.isNotEmpty() }
        assertEquals("Test", getAssetResponse.captured[0].name)
        assertEquals(10.0, getAssetResponse.captured[0].price.toDouble(), 0.0)
    }

    @Test
    fun `3 - GET asset created with existing categoryId`() {
        val getAssetResponse = slot<List<AssetHeaderDto>>()

        // given
        every { ctx.queryParamAsClass("categoryId", Long::class.java) } returns Validator.create(
            Long::class.java,
            categoryHamburguers.id.value.toString(),
            "categoryId"
        )
        every { ctx.json(capture(getAssetResponse)) } answers { ctx }

        // when
        adminAssetsController.get(ctx)

        // then
        assertTrue { getAssetResponse.captured.isNotEmpty() }
        assertEquals("Test", getAssetResponse.captured[0].name)
        assertEquals(10.0, getAssetResponse.captured[0].price.toDouble(), 0.0)
    }

    @Test
    fun `4 - GET asset created with non existing categoryId`() {
        val getAssetResponse = slot<List<AssetHeaderDto>>()

        // given
        every { ctx.queryParamAsClass("categoryId", Long::class.java) } returns Validator.create(
            Long::class.java,
            "666",
            "categoryId"
        )
        every { ctx.json(capture(getAssetResponse)) } answers { ctx }

        // when
        adminAssetsController.get(ctx)

        // then
        assertTrue { getAssetResponse.captured.isEmpty() }
    }
}