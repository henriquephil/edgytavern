package com.hphil.tavern.bills.controller.managed

import com.hphil.tavern.bills.domain.Category
import com.hphil.tavern.bills.repository.CategoryRepository
import com.hphil.tavern.bills.repository.EstablishmentRepository
import com.hphil.tavern.bills.services.security.UserInfo
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/managed/categories")
class ManagedCategoriesController(
    val establishmentRepository: EstablishmentRepository,
    val categoryRepository: CategoryRepository
) : ManagedEstablishmentTrait {

    @PostMapping
    @Transactional
    fun add(@RequestBody request: AddCategoryRequest, userInfo: UserInfo) {
        categoryRepository.save(
            Category(getEstablishment(establishmentRepository, userInfo), request.name)
        )
    }

    @GetMapping
    fun findAll(userInfo: UserInfo): AllCategoriesResponse {
        val establishment = getEstablishment(establishmentRepository, userInfo)
        return AllCategoriesResponse(
            categoryRepository.findAllByEstablishment(establishment)
                .map { CategoryDto(it) }
        )
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@RequestBody request: UpdateCategoryRequest, @PathVariable id: Long, userInfo: UserInfo) {
        val category = categoryRepository.findById(id).orElseThrow()
        validateManager(category.establishment, userInfo)
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
