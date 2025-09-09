
package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminCategoryData
import com.tempick.tempickserver.domain.entitiy.Category
import com.tempick.tempickserver.domain.repository.CategoryRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AdminCategoryTest : StringSpec({
    val categoryRepository = mockk<CategoryRepository>()
    val adminCategoryService = AdminCategoryService(categoryRepository)

    "카테고리 생성 성공 테스트" {
        val categoryData = AdminCategoryData(
            id = 0L,
            name = "공지",
            sequence = 1
        )

        val expectedCategory = Category(
            id = 1L,
            name = categoryData.name,
            sequence = categoryData.sequence
        )

        every { categoryRepository.existsCategoryBySequence(categoryData.sequence) } returns false
        every { categoryRepository.save(any()) } returns expectedCategory

        val result = adminCategoryService.create(categoryData)

        result shouldBe expectedCategory
        verify { categoryRepository.existsCategoryBySequence(categoryData.sequence) }
        verify { categoryRepository.save(any()) }
    }

    "카테고리 생성 시 중복된 sequence가 있으면 예외 발생 테스트" {
        val categoryData = AdminCategoryData(
            id = 0L,
            name = "공지",
            sequence = 1
        )

        every { categoryRepository.existsCategoryBySequence(categoryData.sequence) } returns true

        val exception = shouldThrow<CoreException> {
            adminCategoryService.create(categoryData)
        }

        exception.message shouldBe ErrorType.CATEGORY_SEQUENCE_ALREADY_EXISTS.message
        verify { categoryRepository.existsCategoryBySequence(categoryData.sequence) }
    }

    "카테고리 업데이트 성공 테스트" {
        val categoryId = 1L
        val categoryData = AdminCategoryData(
            id = categoryId,
            name = "업데이트된 공지",
            sequence = 2
        )

        val existingCategory = Category(
            id = categoryId,
            name = "공지",
            sequence = 1
        )

        every { categoryRepository.findActiveCategory(categoryId) } returns existingCategory
        every { categoryRepository.existsCategoryBySequenceAndIdNot(categoryData.sequence, categoryId) } returns false

        val result = adminCategoryService.update(categoryData)

        result shouldBe existingCategory
        verify { categoryRepository.findActiveCategory(categoryId) }
        verify { categoryRepository.existsCategoryBySequenceAndIdNot(categoryData.sequence, categoryId) }
    }

    "카테고리 업데이트 시 중복된 sequence가 있으면 예외 발생 테스트" {
        val categoryId = 1L
        val categoryData = AdminCategoryData(
            id = categoryId,
            name = "업데이트된 공지",
            sequence = 2
        )

        val existingCategory = Category(
            id = categoryId,
            name = "공지",
            sequence = 1
        )

        every { categoryRepository.findActiveCategory(categoryId) } returns existingCategory
        every { categoryRepository.existsCategoryBySequenceAndIdNot(categoryData.sequence, categoryId) } returns true

        val exception = shouldThrow<CoreException> {
            adminCategoryService.update(categoryData)
        }

        exception.message shouldBe ErrorType.CATEGORY_SEQUENCE_ALREADY_EXISTS.message
        verify { categoryRepository.findActiveCategory(categoryId) }
        verify { categoryRepository.existsCategoryBySequenceAndIdNot(categoryData.sequence, categoryId) }
    }

    "존재하지 않는 카테고리 업데이트 시 예외 발생 테스트" {
        val categoryId = 999L
        val categoryData = AdminCategoryData(
            id = categoryId,
            name = "존재하지 않는 카테고리",
            sequence = 1
        )

        every { categoryRepository.findActiveCategory(categoryId) } returns null

        val exception = shouldThrow<CoreException> {
            adminCategoryService.update(categoryData)
        }

        exception.message shouldBe ErrorType.CATEGORY_NOT_FOUND.message
    }

    "카테고리 삭제 성공 테스트" {
        val categoryId = 1L
        val existingCategory = Category(
            id = categoryId,
            name = "공지",
            sequence = 1
        )

        every { categoryRepository.findActiveCategory(categoryId) } returns existingCategory

        adminCategoryService.delete(categoryId)

        verify { categoryRepository.findActiveCategory(categoryId) }
    }

    "존재하지 않는 카테고리 삭제 시 예외 발생 테스트" {
        val categoryId = 999L

        every { categoryRepository.findActiveCategory(categoryId) } returns null

        val exception = shouldThrow<CoreException> {
            adminCategoryService.delete(categoryId)
        }

        exception.message shouldBe ErrorType.CATEGORY_NOT_FOUND.message
    }

    "모든 카테고리 조회 성공 테스트" {
        val categories = listOf(
            Category(id = 1L, name = "공지", sequence = 1),
            Category(id = 2L, name = "거래", sequence = 2)
        )

        every { categoryRepository.findAllActiveCategories() } returns categories

        val result = adminCategoryService.getAllCategories()

        result shouldBe categories
        verify { categoryRepository.findAllActiveCategories() }
    }
})
