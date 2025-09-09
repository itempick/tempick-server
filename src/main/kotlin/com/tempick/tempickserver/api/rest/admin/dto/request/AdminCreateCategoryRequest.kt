package com.tempick.tempickserver.api.rest.admin.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AdminCreateCategoryRequest(
    @field:Schema(description = "카테고리 이름", example = "거래 게시판")
    @field:NotBlank(message = "카테고리 이름은 필수값입니다.")
    val name: String,

    @field:Schema(description = "카테고리 표시 순서", example = "1")
    @field:NotNull(message = "카테고리 표시 순서는 필수값입니다.")
    val sequence: Int
)