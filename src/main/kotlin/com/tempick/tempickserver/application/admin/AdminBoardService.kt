package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBoardData
import com.tempick.tempickserver.domain.entitiy.Board
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
        val category = categoryRepository.findActiveCategory(adminBoardData.categoryId)
            ?: throw CoreException(ErrorType.CATEGORY_NOT_FOUND)

        return boardRepository.save(Board(name = adminBoardData.name, category = category))
    }
}