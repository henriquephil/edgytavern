package com.hphil.tavern.establishment.controller.managed

import com.hphil.tavern.establishment.domain.Category
import com.hphil.tavern.establishment.repository.CategoryRepository
import com.hphil.tavern.establishment.repository.EstablishmentRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/managed/categories")
class ManagedCategoriesController(
    val establishmentRepository: EstablishmentRepository,
    val categoryRepository: CategoryRepository
) : ManagedEstablishmentTrait {

    @PostMapping
    @Transactional
    fun add(@RequestBody request: AddCategoryRequest, principal: Principal) {
        categoryRepository.save(
            Category(getEstablishment(establishmentRepository, principal), request.name)
        )
    }

    @GetMapping
    fun findAll(principal: Principal): AllCategoriesResponse {
        val establishment = getEstablishment(establishmentRepository, principal)
        return AllCategoriesResponse(
            categoryRepository.findAllByEstablishment(establishment)
                .map { CategoryDto(it) }
        )
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@RequestBody request: UpdateCategoryRequest, @PathVariable id: Long, principal: Principal) {
        val category = categoryRepository.findById(id).orElseThrow()
        validateManager(category.establishment, principal)
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
