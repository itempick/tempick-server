package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.application.admin.dto.AdminCategoryBoardResult
import io.swagger.v3.oas.annotations.media.Schema

data class AdminCategoryBoardResponse(
    @field:Schema(description = "카테고리 ID", example = "1")
    val id: Long,

    @field:Schema(description = "카테고리 이름", example = "거래 게시판")
    val name: String,

    @field:Schema(description = "카테고리 표시 순서", example = "1")
    val sequence: Int,

    @field:Schema(description = "게시판 목록")
    val boards: List<AdminBoardResponse>
) {
    companion object {
        fun from(categoryBoardResult: AdminCategoryBoardResult): AdminCategoryBoardResponse {
            return AdminCategoryBoardResponse(
                id = categoryBoardResult.id,
                name = categoryBoardResult.name,
                sequence = categoryBoardResult.sequence,
                boards = categoryBoardResult.board.map { boardData ->
                    AdminBoardResponse(
                        id = boardData.id,
                        categoryName = categoryBoardResult.name,
                        boardName = boardData.name,
                        permission = boardData.permission.ko,
                        isMainExposed = boardData.isMainExposed
                    )
                }
            )
        }
    }
}