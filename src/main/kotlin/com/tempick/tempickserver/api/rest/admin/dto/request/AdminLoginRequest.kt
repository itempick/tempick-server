package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class AdminLoginRequest (
    @Schema(description = "관리자 로그인 아이디", example = "privateadmin")
    val loginId: String,
    @Schema(description = "관리자 로그인 비밀번호", example = "admin12!!@")
    val password: String
)