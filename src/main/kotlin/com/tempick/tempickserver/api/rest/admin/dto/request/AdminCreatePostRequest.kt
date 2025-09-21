package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class AdminCreatePostRequest(
    @Schema(description = "게시글 제목", example = "새로운 게시글 제목")
    val title: String,
    @Schema(description = "노출 우선순위", example = "1")
    val exposePriority: Int,
    @Schema(description = "태그 이름", example = "공지")
    val tagName: String?,
    @Schema(description = "태그 색상", example = "#FF0000")
    val tagColor: String?,
    @Schema(description = "게시글 내용", example = "새로운 게시글 내용입니다.")
    val content: String,
)
