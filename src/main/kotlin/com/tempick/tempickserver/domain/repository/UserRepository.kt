package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    @Query("select u from User u where u.id = :id and u.isActive = true")
    fun findActiveUserById(id: Long): User?

    @Query("select (count(u) > 0) from User u where u.nickname = :nickname")
    fun existsUserByNickname(nickname: String): Boolean
}
