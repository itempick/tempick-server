package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserJpaRepository : JpaRepository<User, Long> {
    @Query("select (count(u) > 0) from User u where u.nickname = :nickname")
    fun existsUserByNickname(nickname: String): Boolean
}
