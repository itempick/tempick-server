package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Banner
import io.swagger.v3.oas.annotations.media.Schema

data class AdminBannerResponse(
    @field:Schema(description = "배너 ID", example = "1")
    val id: Long,
    
    @field:Schema(description = "배너 이미지 URL", example = "https://example.com/banner.jpg")
    val bannerImageUrl: String,
    
    @field:Schema(description = "배너 표시 순서", example = "1")
    val displaySequence: Int
) {
    companion object {
        fun from(banner: Banner): AdminBannerResponse {
            return AdminBannerResponse(
                id = banner.id,
                bannerImageUrl = banner.bannerImageUrl,
                displaySequence = banner.displaySequence
            )
        }
    }
}