package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Post

data class AdminPostDetailResponse(
    val id: Long,
    val title: String,
    val exposePriority: Int,
    val tagName: String?,
    val tagColor: String?,
    val content: String,
    val board: BoardResponse,
) {
    class BoardResponse(
        val id: Long,
        val name: String,
    )

    companion object {
        fun from(post: Post): AdminPostDetailResponse {
            return AdminPostDetailResponse(
                id = post.id,
                title = post.title,
                exposePriority = post.exposePriority,
                tagName = post.tag?.tagName,
                tagColor = post.tag?.tagColor,
                content = post.content,
                board = BoardResponse(
                    id = post.board.id,
                    name = post.board.name,
                )
            )
        }
    }
}
