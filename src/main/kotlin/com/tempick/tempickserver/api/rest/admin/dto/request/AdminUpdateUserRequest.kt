package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "어드민 사용자 수정 요청")
data class AdminUpdateUserRequest(
    @field:Schema(description = "닉네임")
    val nickname: String,

    @field:Schema(description = "블랙리스트 여부")
    val isBlacklisted: Boolean = false,

    @field:Schema(description = "블랙리스트 사유")
    val blacklistReason: String = "",
)