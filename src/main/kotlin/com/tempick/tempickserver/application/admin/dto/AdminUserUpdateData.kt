package com.tempick.tempickserver.application.admin.dto

data class AdminUserUpdateData (
    val nickname: String,
    val isBlacklisted: Boolean,
    val blacklistReason: String?,
)