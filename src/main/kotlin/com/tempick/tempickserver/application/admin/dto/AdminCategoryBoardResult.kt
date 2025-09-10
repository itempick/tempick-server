package com.tempick.tempickserver.application.admin.dto

import com.tempick.tempickserver.domain.entitiy.Category

data class AdminCategoryBoardResult (
    val id: Long,
    val name: String,
    var sequence: Int,
    var board: List<AdminBoardData>
) {
    companion object {
        fun from(category: Category): AdminCategoryBoardResult {
            return AdminCategoryBoardResult(
                id = category.id,
                name = category.name,
                sequence = category.sequence,
                board = category.boards
                    .asSequence()
                    .filter { !it.checkDeleted() }
                    .map { board ->
                        AdminBoardData(
                            id = board.id,
                            name = board.name,
                            categoryId = category.id,
                            permission = board.permission,
                            isMainExposed = board.isMainExposed
                        )
                    }
                    .toList()
            )
        }
    }
}
