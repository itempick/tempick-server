package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Post
import com.tempick.tempickserver.utils.DateTimeUtils

data class AdminPostListItemResponse(
    val id: Long,
    val boardName: String,
    val title: String,
    val commentCount: Int,
    val authorNickname: String,
    val createdAt: String,
) {
    companion object {
        fun from(post: Post): AdminPostListItemResponse {
            val activeComments = post.comments.count { !it.isDeleted }
            return AdminPostListItemResponse(
                id = post.id,
                boardName = post.board.name,
                title = post.title,
                commentCount = activeComments,
                authorNickname = post.user.nickname,
                createdAt = DateTimeUtils.toDisplayDatetime(post.createdAt),
            )
        }
    }
}
