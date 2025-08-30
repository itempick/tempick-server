package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminLoginRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminTokenResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "어드민 인증", description = "관리자 인증 API")
class AdminAuthController(
    private val adminAuthService: AdminAuthService,
) {

    @PostMapping(
        value = ["/admin/auth/login"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @Transactional(readOnly = false)
    @Operation(
        summary = "관리자 로그인",
        description = "관리자 계정으로 로그인하여 JWT 토큰을 발급받습니다.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [Content(schema = Schema(implementation = AdminTokenResponse::class))],
            ),
            ApiResponse(responseCode = "401", description = "아이디 또는 비밀번호가 올바르지 않습니다."),
            ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다."),
        ],
    )
    fun login(
        @RequestBody request: AdminLoginRequest,
    ): RestResponse<AdminTokenResponse> {
        return RestResponse.success(AdminTokenResponse(adminAuthService.login(request)))
    }

}