package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBoardData
import com.tempick.tempickserver.domain.entitiy.Board
import com.tempick.tempickserver.domain.enums.Permission
import com.tempick.tempickserver.domain.repository.BoardRepository
import com.tempick.tempickserver.domain.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBoardService(
    private val boardRepository: BoardRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun create(adminBoardData: AdminBoardData): Board {
        if (boardRepository.existsBoardByName(adminBoardData.name)) {
            throw CoreException(ErrorType.DUPLICATE_BOARD_NAME)
        }

        val category = categoryRepository.findActiveCategory(adminBoardData.categoryId)
            ?: throw CoreException(ErrorType.CATEGORY_NOT_FOUND)

        return boardRepository.save(Board(name = adminBoardData.name, category = category))
    }

    @Transactional(readOnly = true)
    fun getBoardById(boardId: Long): Board {
        return boardRepository.findActiveBoardById(boardId)
            ?: throw CoreException(ErrorType.BOARD_NOT_FOUND)
    }

    @Transactional(readOnly = true)
    fun getAllBoards(): List<Board> {
        return boardRepository.findAll()
            .asSequence()
            .filter { !it.checkDeleted() }
            .toList()
    }

    @Transactional
    fun delete(boardId: Long) {
        val board = boardRepository.findActiveBoardById(boardId)
            ?: throw CoreException(ErrorType.BOARD_NOT_FOUND)

        board.delete()
    }

    @Transactional
    fun update(adminBoardData: AdminBoardData): Board {
        val board = boardRepository.findActiveBoardById(adminBoardData.id)
            ?: throw CoreException(ErrorType.BOARD_NOT_FOUND)

        if (!board.validateCategoryChange(adminBoardData.categoryId)) {
            throw CoreException(ErrorType.BOARD_CATEGORY_CANNOT_BE_CHANGED)
        }

        board.update(
            name = adminBoardData.name,
            permission = Permission.valueOf(adminBoardData.permission.name.uppercase()),
            isMainExposed = adminBoardData.isMainExposed
        )

        return board
    }
}
