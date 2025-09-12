package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminUpdatePostRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminPostCommentResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminPostDetailResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminPostListItemResponse
import com.tempick.tempickserver.api.rest.common.dto.response.CustomPage
import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.domain.repository.CommentRepository
import com.tempick.tempickserver.domain.repository.PostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminPostService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) {
    @Transactional(readOnly = true)
    fun getPosts(page: Int, size: Int): CustomPage<AdminPostListItemResponse> {
        val pageable: Pageable = PageRequest.of(if (page > 0) page - 1 else 0, size)
        val postsPage = postRepository.findAllActive(pageable)
        val mapped = postsPage.map { AdminPostListItemResponse.from(it) }
        return CustomPage.of(mapped)
    }

    @Transactional(readOnly = true)
    fun getPostDetail(postId: Long): AdminPostDetailResponse {
        val post = postRepository.findActivePost(postId)
            ?: throw CoreException(ErrorType.POST_NOT_FOUND)
        return AdminPostDetailResponse.from(post)
    }

    @Transactional(readOnly = true)
    fun getPostComments(postId: Long, page: Int, size: Int): CustomPage<AdminPostCommentResponse> {
        val post = postRepository.findActivePost(postId)
            ?: throw CoreException(ErrorType.POST_NOT_FOUND)
        val pageable: Pageable = PageRequest.of(if (page > 0) page - 1 else 0, size)
        val commentsPage = commentRepository.findActiveComments(post.id, pageable)
        val mapped = commentsPage.map { AdminPostCommentResponse.from(it) }
        return CustomPage.of(mapped)
    }

    @Transactional
    fun updatePost(
        postId: Long,
        request: AdminUpdatePostRequest
    ): AdminPostDetailResponse {
        val post = postRepository.findActivePost(postId)
            ?: throw CoreException(ErrorType.POST_NOT_FOUND)
        post.updateByAdmin(
            exposePriority = request.exposePriority,
            title = request.title,
            content = request.content,
            tagName = request.tagName,
            tagColor = request.tagColor,
        )
        return AdminPostDetailResponse.from(post)
    }

    @Transactional
    fun deleteComment(commentId: Long) {
        commentRepository.findActiveCommentById(commentId)
            .run { this?.delete() }
            ?: throw CoreException(ErrorType.COMMENT_NOT_FOUND)

    }
}
