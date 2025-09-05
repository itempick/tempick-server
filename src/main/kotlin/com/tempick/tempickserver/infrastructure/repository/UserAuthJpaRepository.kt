package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.UserAuth
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserAuthJpaRepository : JpaRepository<UserAuth, Long> {
    @Query("SELECT ua FROM UserAuth ua WHERE ua.email = :email AND ua.isActive = true")
    fun findActiveByEmail(email: String): UserAuth?

    @Query("SELECT CASE WHEN COUNT(ua) > 0 THEN true ELSE false END FROM UserAuth ua WHERE ua.email = :email")
    fun existsByEmail(email: String): Boolean
}
