package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.loginId = :loginId AND u.isActive = true")
    fun findByLoginId(loginId: String): User?

    fun existsByLoginId(loginId: String): Boolean
}