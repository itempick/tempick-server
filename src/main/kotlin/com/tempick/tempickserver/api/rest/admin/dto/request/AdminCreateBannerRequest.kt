package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AdminCreateBannerRequest(
    @field:Schema(description = "배너 ID (업데이트 시 필요)", example = "1", required = false)
    val id: Long? = null,

    @field:Schema(description = "배너 이미지 URL", example = "https://example.com/banner.jpg")
    @field:NotBlank(message = "배너 이미지 URL은 필수값입니다.")
    val bannerImageUrl: String,

    @field:Schema(description = "배너 표시 순서", example = "1")
    @field:NotNull(message = "배너 표시 순서는 필수값입니다.")
    val displaySequence: Int
)
