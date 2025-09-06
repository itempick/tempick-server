package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.User
import com.tempick.tempickserver.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    private val jpa: UserJpaRepository
) : UserRepository {
    override fun save(user: User): User = jpa.save(user)

    override fun existsByNickname(nickName: String): Boolean {
        return jpa.existsUserByNickname(nickName)
    }
}
