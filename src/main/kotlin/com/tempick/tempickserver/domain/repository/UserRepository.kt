package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.User

interface UserRepository {
    fun save(user: User): User
    fun existsByNickname(nickName: String): Boolean
}
