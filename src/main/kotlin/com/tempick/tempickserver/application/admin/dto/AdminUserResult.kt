package com.tempick.tempickserver.application.admin.dto

import com.tempick.tempickserver.domain.entitiy.User
import com.tempick.tempickserver.domain.entitiy.UserAuth
import java.time.LocalDateTime

 data class AdminUserResult (
    val id: Long,
    val email: String,
    val nickname: String,
    val isBlacklisted: Boolean,
    val blacklistReason: String?,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun toAdminUserResult(user: User, userAuth: UserAuth): AdminUserResult {
            return AdminUserResult(
                id = user.id,
                email = userAuth.email,
                nickname = user.nickname,
                isBlacklisted = user.isBlacklisted,
                blacklistReason = user.blacklistReason,
                createdAt = user.createdAt,
            )
        }
    }
}