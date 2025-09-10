package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBoardData
import com.tempick.tempickserver.domain.entitiy.Board
import com.tempick.tempickserver.domain.entitiy.Category
import com.tempick.tempickserver.domain.enums.Permission.ALL
import com.tempick.tempickserver.domain.repository.BoardRepository
import com.tempick.tempickserver.domain.repository.CategoryRepository
import com.tempick.tempickserver.util.TestMockData
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify

class AdminBoardServiceTest : StringSpec({
    val boardRepository = mockk<BoardRepository>()
    val categoryRepository = mockk<CategoryRepository>()
    val adminBoardService = AdminBoardService(boardRepository, categoryRepository)

    "보드를 성공적으로 생성할 수 있다" {
        // Given
        val categoryId = 1L
        val boardName = "Test Board"
        val boardData = AdminBoardData(
            name = boardName,
            categoryId = categoryId,
            permission = ALL,
            isMainExposed = false
        )

        val category = TestMockData.createCategory(id = categoryId)

        val expectedBoard = TestMockData.createBoard(
            id = 1L,
            name = boardName,
            category = category,
            permission = ALL,
            isMainExposed = false
        )

        every { boardRepository.existsBoardByName(boardName) } returns false
        every { categoryRepository.findActiveCategory(categoryId) } returns category
        every { boardRepository.save(any()) } returns expectedBoard

        // When
        val result = adminBoardService.create(boardData)

        // Then
        result shouldBe expectedBoard
        verify { categoryRepository.findActiveCategory(categoryId) }
        verify { boardRepository.save(any()) }
    }

    "존재하지 않는 카테고리로 보드를 생성하면 예외가 발생한다" {
        // Given
        val categoryId = 999L
        val boardData = AdminBoardData(
            name = "Test Board",
            categoryId = categoryId
        )

        every { boardRepository.existsBoardByName(any()) } returns false
        every { categoryRepository.findActiveCategory(categoryId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.create(boardData)
        }

        exception.message shouldBe ErrorType.CATEGORY_NOT_FOUND.message
        verify { categoryRepository.findActiveCategory(categoryId) }
    }

    "이미 존재하는 이름으로 보드를 생성하면 예외가 발생한다" {
        // Given
        val categoryId = 1L
        val boardName = "Existing Board"
        val boardData = AdminBoardData(
            name = boardName,
            categoryId = categoryId
        )

        every { boardRepository.existsBoardByName(boardName) } returns true

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.create(boardData)
        }

        exception.message shouldBe ErrorType.DUPLICATE_BOARD_NAME.message
        verify { boardRepository.existsBoardByName(boardName) }
    }

    "ID로 보드를 성공적으로 조회할 수 있다" {
        // Given
        val boardId = 1L
        val category = TestMockData.createCategory(id = 1L)
        val expectedBoard = TestMockData.createBoard(
            id = boardId,
            name = "Test Board",
            category = category,
            permission = ALL,
            isMainExposed = false
        )

        every { boardRepository.findActiveBoardById(boardId) } returns expectedBoard

        // When
        val result = adminBoardService.getBoardById(boardId)

        // Then
        result shouldBe expectedBoard
        verify { boardRepository.findActiveBoardById(boardId) }
    }

    "존재하지 않는 보드 ID로 조회하면 예외가 발생한다" {
        // Given
        val boardId = 999L

        every { boardRepository.findActiveBoardById(boardId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.getBoardById(boardId)
        }

        exception.message shouldBe ErrorType.BOARD_NOT_FOUND.message
        verify { boardRepository.findActiveBoardById(boardId) }
    }

    "모든 보드를 성공적으로 조회할 수 있다" {
        // Given
        val category1 = TestMockData.createCategory(id = 1L, name = "Category 1", sequence = 1)
        val category2 = TestMockData.createCategory(id = 2L, name = "Category 2", sequence = 2)

        val board1 = TestMockData.createBoard(
            id = 1L, 
            name = "Board 1", 
            category = category1,
            permission = ALL,
            isMainExposed = false
        )
        val board2 = TestMockData.createBoard(
            id = 2L, 
            name = "Board 2", 
            category = category2,
            permission = com.tempick.tempickserver.domain.enums.Permission.ADMIN,
            isMainExposed = true
        )
        val deletedBoard = TestMockData.createBoard(
            id = 3L, 
            name = "Deleted Board", 
            category = category1,
            permission = com.tempick.tempickserver.domain.enums.Permission.PRIVATE,
            isMainExposed = false
        )
        deletedBoard.delete()

        val allBoards = listOf(board1, board2, deletedBoard)
        val activeBoards = listOf(board1, board2)

        every { boardRepository.findAll() } returns allBoards

        // When
        val result = adminBoardService.getAllBoards()

        // Then
        result shouldBe activeBoards
        verify { boardRepository.findAll() }
    }

    "보드를 성공적으로 삭제할 수 있다" {
        // Given
        val boardId = 1L
        val categoryId = 2L
        val category = mockk<Category>()
        val board = mockk<Board>()

        every { boardRepository.findActiveBoardById(boardId) } returns board
        every { board.category } returns category
        every { category.id } returns categoryId
        every { boardRepository.deleteAllBoardsByCategoryId(categoryId) } returns 1
        every { board.delete() } just runs

        // When
        adminBoardService.delete(boardId)

        // Then
        verify { boardRepository.findActiveBoardById(boardId) }
        verify { boardRepository.deleteAllBoardsByCategoryId(categoryId) }
        verify { board.delete() }
    }

    "존재하지 않는 보드를 삭제하려고 하면 예외가 발생한다" {
        // Given
        val boardId = 999L

        every { boardRepository.findActiveBoardById(boardId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.delete(boardId)
        }

        exception.message shouldBe ErrorType.BOARD_NOT_FOUND.message
        verify { boardRepository.findActiveBoardById(boardId) }
    }

    "보드 정보를 성공적으로 업데이트할 수 있다" {
        // Given
        val boardId = 1L
        val boardName = "Updated Board"
        val category = TestMockData.createCategory(id = 1L)
        val board = TestMockData.createBoard(
            id = boardId, 
            name = "Original Board", 
            category = category,
            permission = com.tempick.tempickserver.domain.enums.Permission.ADMIN,
            isMainExposed = false
        )

        val boardData = AdminBoardData(
            id = boardId,
            name = boardName,
            permission = ALL,
            isMainExposed = true
        )

        every { boardRepository.findActiveBoardById(boardId) } returns board

        // When
        val result = adminBoardService.update(boardData)

        // Then
        result.id shouldBe boardId
        result.name shouldBe boardName
        result.permission shouldBe ALL
        result.isMainExposed shouldBe true
        verify { boardRepository.findActiveBoardById(boardId) }
    }

    "존재하지 않는 보드를 업데이트하려고 하면 예외가 발생한다" {
        // Given
        val boardId = 999L
        val boardData = AdminBoardData(
            id = boardId,
            name = "Updated Board",
            permission = ALL,
            isMainExposed = true
        )

        every { boardRepository.findActiveBoardById(boardId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.update(boardData)
        }

        exception.message shouldBe ErrorType.BOARD_NOT_FOUND.message
        verify { boardRepository.findActiveBoardById(boardId) }
    }

    "보드의 카테고리를 변경하려고 하면 예외가 발생한다" {
        // Given
        val boardId = 1L
        val originalCategoryId = 1L
        val newCategoryId = 2L
        val category = TestMockData.createCategory(id = originalCategoryId, name = "Original Category")
        val board = TestMockData.createBoard(
            id = boardId, 
            name = "Original Board", 
            category = category,
            permission = com.tempick.tempickserver.domain.enums.Permission.ADMIN,
            isMainExposed = false
        )

        val boardData = AdminBoardData(
            id = boardId,
            name = "Updated Board",
            categoryId = newCategoryId,
            permission = ALL,
            isMainExposed = true
        )

        every { boardRepository.findActiveBoardById(boardId) } returns board

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.update(boardData)
        }

        exception.message shouldBe ErrorType.BOARD_CATEGORY_CANNOT_BE_CHANGED.message
        verify { boardRepository.findActiveBoardById(boardId) }
    }
})
