package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.application.admin.dto.AdminUserResult

data class AdminUserDetailResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val isBlacklisted: Boolean,
    val blacklistReason: String?,
) {
    companion object {
        fun from(adminUserResult: AdminUserResult): AdminUserDetailResponse {
            return AdminUserDetailResponse(
                id = adminUserResult.id,
                email = adminUserResult.email,
                nickname = adminUserResult.nickname,
                isBlacklisted = adminUserResult.isBlacklisted,
                blacklistReason = adminUserResult.blacklistReason,
            )
        }
    }
}
