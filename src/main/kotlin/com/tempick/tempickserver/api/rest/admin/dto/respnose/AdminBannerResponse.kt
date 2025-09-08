package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.utils.DateTimeUtils.toDisplayDatetime
import io.swagger.v3.oas.annotations.media.Schema

data class AdminBannerResponse(
    @field:Schema(description = "배너 ID", example = "1")
    val id: Long,

    @field:Schema(description = "배너 이미지 URL", example = "https://example.com/banner.jpg")
    val bannerImageUrl: String,

    @field:Schema(description = "배너 클릭 URL", example = "https://example.com/landing")
    val clickUrl: String?,

    @field:Schema(description = "배너 표시 순서", example = "1")
    val displaySequence: Int,

    @field:Schema(description = "등록일", example = "2025.08.01 23:11")
    val createdAt: String
) {
    companion object {
        fun from(banner: Banner): AdminBannerResponse {
            return AdminBannerResponse(
                id = banner.id,
                bannerImageUrl = banner.bannerImageUrl,
                clickUrl = banner.clickUrl,
                displaySequence = banner.displaySequence,
                createdAt = toDisplayDatetime(banner.createdAt)
            )
        }
    }
}
