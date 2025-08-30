package com.tempick.tempickserver.api.rest.common.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "이미지 업로드 응답")
data class ImageUploadResponse(
    @field:Schema(description = "이미지 URL", example = "https://example.com/images/profile.jpg")
    val url: String,
    @field:Schema(description = "이미지 이름", example = "profile.jpg")
    val name: String,
)
