package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "어드민 게시물 수정 요청")
data class AdminUpdatePostRequest(
    @field:Schema(description = "게시물 노출 우선순위")
    val exposePriority: Int? = null,

    @field:Schema(description = "태그 이름")
    val tagName: String,

    @field:Schema(description = "태그 색상 (hex 또는 미리 정의된 값)")
    val tagColor: String,

    @field:Schema(description = "게시물 텍스트(내용)")
    val content: String,

    @field:Schema(description = "게시물 제목")
    val title: String,
)