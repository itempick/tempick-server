package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByLoginId(loginId: String): User?
}