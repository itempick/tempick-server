package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    @Query("select p from Post p where p.isDeleted = false")
    fun findAllActive(pageable: Pageable): Page<Post>

    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    fun findActivePost(id: Long): Post?
}
