package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class AdminLoginRequest (
    @field:Schema(description = "관리자 로그인 아이디", example = "admin@admin.com")
    @field:NotBlank(message = "이메일은 필수값입니다.")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String,
    @field:Schema(description = "관리자 로그인 비밀번호", example = "admin12!!@")
    @field:NotBlank(message = "비밀번호는 필수값입니다.")
    val password: String
)