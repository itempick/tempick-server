package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.UserAuth

interface UserAuthRepository {
    fun findActiveByEmail(email: String): UserAuth?
    fun existsByEmail(email: String): Boolean
    fun save(userAuth: UserAuth): UserAuth
}