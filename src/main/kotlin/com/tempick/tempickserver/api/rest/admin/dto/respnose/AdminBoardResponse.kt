package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Board

data class AdminBoardResponse(
    val boardId: Long,
    val categoryName: String,
    val boardName: String,
) {
    companion object {
        fun from(board: Board): AdminBoardResponse {
            return AdminBoardResponse(
                boardId = board.id,
                categoryName = board.category.name,
                boardName = board.name
            )
        }
    }
}