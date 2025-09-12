package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.application.admin.dto.AdminUserResult
import com.tempick.tempickserver.utils.DateTimeUtils.toDisplayDatetime

data class AdminUserListItemResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val blackList: AdminBlacklistResponse,
    val createdAt: String,
) {
    class AdminBlacklistResponse(
        val isBlacklisted: Boolean,
        val reason: String?,
    )

    companion object {
        fun from(adminUserResult: AdminUserResult): AdminUserListItemResponse {
            return AdminUserListItemResponse(
                id = adminUserResult.id,
                email = adminUserResult.email,
                nickname = adminUserResult.nickname,
                blackList = AdminBlacklistResponse(
                    isBlacklisted = adminUserResult.isBlacklisted,
                    reason = adminUserResult.blacklistReason
                ),
                createdAt = toDisplayDatetime(adminUserResult.createdAt),
            )
        }
    }
}
