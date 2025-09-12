package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Comment
import com.tempick.tempickserver.utils.DateTimeUtils

data class AdminPostCommentResponse(
    val id: Long,
    val authorNickname: String,
    val content: String,
    val createdAt: String,
) {
    companion object {
        fun from(comment: Comment): AdminPostCommentResponse {
            return AdminPostCommentResponse(
                id = comment.id,
                authorNickname = comment.user.nickname,
                content = comment.text,
                createdAt = DateTimeUtils.toDisplayDatetime(comment.createdAt)
            )
        }
    }
}
