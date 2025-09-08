package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminCategoryData
import com.tempick.tempickserver.domain.entitiy.Category
import com.tempick.tempickserver.domain.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminCategoryService(
    private val categoryRepository: CategoryRepository
) {

    @Transactional(readOnly = true)
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAllActiveCategories()
            .asSequence()
            .filter { !it.checkDeleted() }
            .toList()
    }

    @Transactional
    fun create(categoryData: AdminCategoryData): Category {
        return categoryRepository.save(categoryData.toEntity())
    }

    @Transactional
    fun delete(categoryId: Long) {
        categoryRepository.findActiveCategory(categoryId)
            ?.run { delete() } ?: throw CoreException(ErrorType.CATEGORY_NOT_FOUND)
    }

    @Transactional
    fun update(categoryData: AdminCategoryData): Category {
        return categoryRepository.findActiveCategory(categoryData.id)
            ?.run { update(categoryData.name, categoryData.sequence) }
            ?: throw CoreException(ErrorType.CATEGORY_NOT_FOUND)
    }
}