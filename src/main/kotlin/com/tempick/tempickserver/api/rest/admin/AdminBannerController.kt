package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminCreateBannerRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminBannerResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminBannerService
import com.tempick.tempickserver.application.admin.dto.AdminCreateBannerData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "어드민 배너", description = "관리자 배너 관리 API")
class AdminBannerController(
    private val adminBannerService: AdminBannerService,
) {

    @PostMapping(
        value = ["/admin/banners"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @Transactional
    @Operation(
        summary = "배너 생성 또는 수정",
        description = "새로운 배너를 생성하거나 기존 배너를 수정합니다. ID가 제공되면 기존 배너를 수정하고, 제공되지 않으면 새로운 배너를 생성합니다.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "배너 생성 또는 수정 성공",
                content = [Content(schema = Schema(implementation = AdminBannerResponse::class))],
            ),
            ApiResponse(responseCode = "400", description = "배너 표시 순서가 이미 존재합니다."),
            ApiResponse(responseCode = "400", description = "배너를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
        ],
    )
    fun upsertBanner(
        @RequestBody @Valid request: AdminCreateBannerRequest,
    ): RestResponse<AdminBannerResponse> {
        val data = AdminCreateBannerData(
            id = request.id,
            bannerImageUrl = request.bannerImageUrl,
            displaySequence = request.displaySequence
        )
        val banner = adminBannerService.upsertBanner(data)
        return RestResponse.success(AdminBannerResponse.from(banner))
    }
}
