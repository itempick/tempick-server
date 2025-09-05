package com.tempick.tempickserver.infrastructure.repository

import com.tempick.tempickserver.domain.entitiy.UserAuth
import com.tempick.tempickserver.domain.repository.UserAuthRepository
import org.springframework.stereotype.Repository

@Repository
class UserAuthRepositoryAdapter(
    private val jpa: UserAuthJpaRepository
) : UserAuthRepository {
    override fun findActiveByLoginId(loginId: String): UserAuth? = jpa.findActiveByEmail(loginId)
    override fun existsByLoginId(loginId: String): Boolean = jpa.existsByEmail(loginId)
    override fun save(userAuth: UserAuth): UserAuth = jpa.save(userAuth)
}
