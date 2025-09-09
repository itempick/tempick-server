package com.tempick.tempickserver.api.rest.admin.dto.respnose

import com.tempick.tempickserver.domain.entitiy.Category
import com.tempick.tempickserver.utils.DateTimeUtils.toDisplayDatetime
import io.swagger.v3.oas.annotations.media.Schema

data class AdminCategoryResponse(
    @field:Schema(description = "카테고리 ID", example = "1")
    val id: Long,

    @field:Schema(description = "카테고리 이름", example = "거래 게시판")
    val name: String,

    @field:Schema(description = "카테고리 표시 순서", example = "1")
    val sequence: Int,

    @field:Schema(description = "등록일", example = "2025.08.01 23:11")
    val createdAt: String
) {
    companion object {
        fun from(category: Category): AdminCategoryResponse {
            return AdminCategoryResponse(
                id = category.id,
                name = category.name,
                sequence = category.sequence,
                createdAt = toDisplayDatetime(category.createdAt)
            )
        }
    }
}