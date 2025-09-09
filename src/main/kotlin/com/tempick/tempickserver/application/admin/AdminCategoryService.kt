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
        validateSequenceForCreate(categoryData.sequence)
        return categoryRepository.save(categoryData.toEntity())
    }


    @Transactional
    fun delete(categoryId: Long) {
        categoryRepository.findActiveCategory(categoryId)
            ?.run { delete() } ?: throw CoreException(ErrorType.CATEGORY_NOT_FOUND)
    }

    @Transactional
    fun update(categoryData: AdminCategoryData): Category {
        val existingCategory = categoryRepository.findActiveCategory(categoryData.id)
            ?: throw CoreException(ErrorType.CATEGORY_NOT_FOUND)

        validateSequenceForUpdate(categoryData.sequence, categoryData.id)

        return existingCategory.update(categoryData.name, categoryData.sequence)
    }


    private fun validateSequenceForCreate(sequence: Int) {
        if (categoryRepository.existsCategoryBySequence(sequence)) {
            throw CoreException(ErrorType.CATEGORY_SEQUENCE_ALREADY_EXISTS)
        }
    }

    private fun validateSequenceForUpdate(sequence: Int, categoryId: Long) {
        if (categoryRepository.existsCategoryBySequenceAndIdNot(sequence, categoryId)) {
            throw CoreException(ErrorType.CATEGORY_SEQUENCE_ALREADY_EXISTS)
        }
    }

}