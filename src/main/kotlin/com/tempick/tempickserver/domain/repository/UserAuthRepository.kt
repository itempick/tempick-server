package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.UserAuth

interface UserAuthRepository {
    fun findActiveByLoginId(loginId: String): UserAuth?
    fun existsByLoginId(loginId: String): Boolean
    fun save(userAuth: UserAuth): UserAuth
}