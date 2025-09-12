package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminUpdateUserRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminUserDetailResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminUserListItemResponse
import com.tempick.tempickserver.api.rest.common.dto.response.CustomPage
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "어드민 사용자", description = "관리자 사용자 관리 API")
class AdminUserController(
    private val adminUserService: AdminUserService,
) {

    @GetMapping(
        value = ["/admin/user"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "사용자 목록 조회",
        description = "페이지네이션으로 사용자 목록을 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "사용자 목록 조회 성공",
                content = [Content(schema = Schema(implementation = CustomPage::class))]
            )
        ]
    )
    fun getUsers(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): RestResponse<CustomPage<AdminUserListItemResponse>> {
        val resultPage = adminUserService.getUsers(page, size)
        val mapped = resultPage.map { AdminUserListItemResponse.from(it) }
        return RestResponse.success(CustomPage.of(mapped))
    }

    @GetMapping(
        value = ["/admin/user/{userId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "사용자 상세 조회",
        description = "특정 사용자의 상세 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "사용자 상세 조회 성공",
                content = [Content(schema = Schema(implementation = AdminUserDetailResponse::class))]
            )
        ]
    )
    fun getUserDetail(@PathVariable userId: Long): RestResponse<AdminUserDetailResponse> {
        val result = adminUserService.getUserDetail(userId)
        return RestResponse.success(AdminUserDetailResponse.from(result))
    }

    @PutMapping(
        value = ["/admin/user/{userId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "사용자 정보 수정",
        description = "닉네임 및 블랙리스트 여부/사유를 수정합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "사용자 정보 수정 성공",
                content = [Content(schema = Schema(implementation = AdminUserDetailResponse::class))]
            )
        ]
    )
    fun updateUser(
        @PathVariable userId: Long,
        @RequestBody request: AdminUpdateUserRequest
    ): RestResponse<AdminUserDetailResponse> {
        val updated = adminUserService.updateUser(
            userId = userId,
            nickname = request.nickname,
            isBlacklisted = request.isBlacklisted,
            reason = request.blacklistReason
        )
        return RestResponse.success(AdminUserDetailResponse.from(updated))
    }
}
