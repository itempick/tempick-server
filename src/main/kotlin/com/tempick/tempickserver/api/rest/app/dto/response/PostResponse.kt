package com.tempick.tempickserver.api.rest.app.dto.response

import com.tempick.tempickserver.domain.entitiy.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val viewCount: Int,
    val author: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(post: Post): PostResponse {
            return PostResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                viewCount = post.viewCount,
                author = post.user.nickname,
                createdAt = post.createdAt
            )
        }
    }
}
