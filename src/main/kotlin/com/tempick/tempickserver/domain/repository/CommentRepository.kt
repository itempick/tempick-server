package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    @Query("select c from Comment c where c.post.id = :postId and c.isDeleted = false order by c.createdAt asc")
    fun findActiveComments(postId: Long): List<Comment>

    @Query("select c from Comment c where c.post.id = :postId and c.isDeleted = false order by c.createdAt asc")
    fun findActiveComments(postId: Long, pageable: Pageable): Page<Comment>

    @Query("select c from Comment c where c.id = :id and c.isDeleted = false")
    fun findActiveCommentById(id: Long): Comment?
}
