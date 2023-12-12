package com.hphil.tavern.establishment.controller.admin

import com.hphil.tavern.establishment.repository.entity.CategoryEntity
import com.hphil.tavern.establishment.repository.table.CategoryTable
import com.hphil.tavern.establishment.util.getAdminEstablishment
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import org.jetbrains.exposed.sql.transactions.transaction

class AdminCategoriesController {
    fun create(ctx: Context) {
        val addCategoryRequest = ctx.bodyAsClass(AddCategoryRequest::class.java)
        transaction {
            val establishment = ctx.getAdminEstablishment()
            CategoryEntity.new(establishment, addCategoryRequest.name)
        }
    }

    fun get(ctx: Context) {
        transaction {
            val establishment = ctx.getAdminEstablishment()
            ctx.json(
                CategoryEntity.findAllByEstablishment(establishment)
                    .map { CategoryDto(it) }
            )
        }
    }

    fun update(ctx: Context) {
        val request = ctx.bodyAsClass(UpdateCategoryRequest::class.java)
        val categoryId = ctx.pathParamAsClass("categoryId", Long::class.java).get()
        transaction {
            val category = CategoryEntity.findByEstablishmentAndId(ctx.getAdminEstablishment(), categoryId)
            category.name = request.name
            category.active = request.active
        }
    }

    fun delete(ctx: Context) {
        val categoryId = ctx.pathParamAsClass("categoryId", Long::class.java).get()
        CategoryTable.delete(ctx.getAdminEstablishment().id.value, categoryId)
    }
}

class AddCategoryRequest(val name: String)
class UpdateCategoryRequest(val name: String, val active: Boolean)
class CategoryDto(val id: Long, val name: String, val active: Boolean, val order: Int) {
    constructor(category: CategoryEntity) : this(category.id.value, category.name, category.active, category.order)
}
