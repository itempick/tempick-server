package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBoardData
import com.tempick.tempickserver.domain.entitiy.Board
import com.tempick.tempickserver.domain.entitiy.Category
import com.tempick.tempickserver.domain.repository.BoardRepository
import com.tempick.tempickserver.domain.repository.CategoryRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.just
import io.mockk.runs

class AdminBoardServiceTest : StringSpec({
    val boardRepository = mockk<BoardRepository>()
    val categoryRepository = mockk<CategoryRepository>()
    val adminBoardService = AdminBoardService(boardRepository, categoryRepository)

    "보드 생성 성공 테스트" {
        // Given
        val categoryId = 1L
        val boardName = "Test Board"
        val boardData = AdminBoardData(
            name = boardName,
            categoryId = categoryId
        )

        val category = Category(
            id = categoryId,
            name = "Test Category",
            sequence = 1
        )

        val expectedBoard = Board(
            id = 1L,
            name = boardName,
            category = category
        )

        every { categoryRepository.findActiveCategory(categoryId) } returns category
        every { boardRepository.save(any()) } returns expectedBoard

        // When
        val result = adminBoardService.create(boardData)

        // Then
        result shouldBe expectedBoard
        verify { categoryRepository.findActiveCategory(categoryId) }
        verify { boardRepository.save(any()) }
    }

    "존재하지 않는 카테고리로 보드 생성 시 예외 발생 테스트" {
        // Given
        val categoryId = 999L
        val boardData = AdminBoardData(
            name = "Test Board",
            categoryId = categoryId
        )

        every { categoryRepository.findActiveCategory(categoryId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBoardService.create(boardData)
        }

        exception.message shouldBe ErrorType.CATEGORY_NOT_FOUND.message
        verify { categoryRepository.findActiveCategory(categoryId) }
    }

    "보드 ID로 조회 성공 테스트" {
        // Given
        val boardId = 1L
        val category = Category(
            id = 1L,
            name = "Test Category",
            sequence = 1
        )
        val expectedBoard = Board(
            id = boardId,
            name = "Test Board",
            category = category
        )

        every { boardRepository.findActiveBoardById(boardId) } returns expectedBoard

        // When
        val result = adminBoardService.getBoardById(boardId)

        // Then
        result shouldBe expectedBoard
        verify { boardRepository.findActiveBoardById(boardId) }
    }

    "존재하지 않는 보드 ID로 조회 시 예외 발생 테스트" {
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

    "모든 보드 조회 성공 테스트" {
        // Given
        val category1 = Category(id = 1L, name = "Category 1", sequence = 1)
        val category2 = Category(id = 2L, name = "Category 2", sequence = 2)

        val board1 = Board(id = 1L, name = "Board 1", category = category1)
        val board2 = Board(id = 2L, name = "Board 2", category = category2)
        val deletedBoard = Board(id = 3L, name = "Deleted Board", category = category1)
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

    "보드 삭제 성공 테스트" {
        // Given
        val boardId = 1L
        val category = Category(id = 1L, name = "Test Category", sequence = 1)
        val board = Board(id = boardId, name = "Test Board", category = category)

        every { boardRepository.findActiveBoardById(boardId) } returns board
        every { board.delete() } just runs

        // When
        adminBoardService.delete(boardId)

        // Then
        verify { boardRepository.findActiveBoardById(boardId) }
        verify { board.delete() }
    }

    "존재하지 않는 보드 삭제 시 예외 발생 테스트" {
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
})
