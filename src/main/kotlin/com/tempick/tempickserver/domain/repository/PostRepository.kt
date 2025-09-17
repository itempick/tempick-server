package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    @Query("select p from Post p where p.isDeleted = false")
    fun findAllActive(pageable: Pageable): Page<Post>

    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    fun findActivePost(id: Long): Post?

    @Query("SELECT DATE(p.createdAt) as date, COUNT(p.id) as count FROM Post p WHERE p.createdAt BETWEEN :startDate AND :endDate GROUP BY DATE(p.createdAt)")
    fun findDailyPostCounts(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<Array<Any>>

    @Query("SELECT FUNCTION('YEAR', p.createdAt) as year, FUNCTION('MONTH', p.createdAt) as month, COUNT(p.id) as count FROM Post p WHERE p.createdAt BETWEEN :startDate AND :endDate GROUP BY FUNCTION('YEAR', p.createdAt), FUNCTION('MONTH', p.createdAt)")
    fun findMonthlyPostCounts(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<Array<Any>>

    @Query("SELECT p FROM Post p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.viewCount DESC")
    fun findTop5ByViewCount(pageable: Pageable, @Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<Post>
}
