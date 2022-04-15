package com.hphil.tavern.resources.manager

import com.hphil.tavern.domain.Category
import com.hphil.tavern.repository.CategoryRepository
import com.hphil.tavern.repository.EstablishmentRepository
import io.quarkus.security.Authenticated
import java.util.stream.Collectors
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.SecurityContext

@Path("/managed/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
class EstablishmentCategoriesResource: ManagedEstablishmentTrait {
    @Inject
    lateinit var establishmentRepository: EstablishmentRepository
    @Inject
    lateinit var categoryRepository: CategoryRepository

    @POST
    @Transactional
    fun add(@Context ctx: SecurityContext, request: AddCategoryRequest) {
        categoryRepository.persist(
                Category(getEstablishment(establishmentRepository, ctx), request.name)
        )
    }

    @GET
    fun findAll(@Context ctx: SecurityContext): AllCategoriesResponse {
        val establishment = getEstablishment(establishmentRepository, ctx)
        return AllCategoriesResponse(
                categoryRepository.stream("establishment_id", establishment.id).map { CategoryDto(it) }.collect(Collectors.toList())
        )
    }

    @PUT
    @Path("/{id}")
    @Transactional
    fun update(@Context ctx: SecurityContext, @PathParam("id") id: Long, request: UpdateCategoryRequest) {
        val category = categoryRepository.findById(id)
        validateManager(category.establishment, ctx)
        category.name = request.name
        category.active = request.active
    }
}

class AddCategoryRequest(val name: String)
class UpdateCategoryRequest(val name: String, val active: Boolean)
class AllCategoriesResponse(val categories: List<CategoryDto>)
class CategoryDto(val id: Long, val name: String, val active: Boolean, val order: Int) {
    constructor(category: Category) : this(category.id!!, category.name, category.active, 0 /* TODO */)
}
