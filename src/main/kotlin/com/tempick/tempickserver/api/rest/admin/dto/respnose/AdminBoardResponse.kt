package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Board

data class AdminBoardResponse(
    val id: Long,
    val categoryName: String,
    val boardName: String,
    val permission: String?,
    val isMainExposed: Boolean?
) {
    companion object {
        fun from(board: Board): AdminBoardResponse {
            return AdminBoardResponse(
                id = board.id,
                categoryName = board.category.name,
                boardName = board.name,
                permission = board.permission.name,
                isMainExposed = board.isMainExposed
            )
        }
    }
}
