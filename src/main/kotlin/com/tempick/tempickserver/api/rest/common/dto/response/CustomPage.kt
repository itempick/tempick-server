package com.tempick.tempickserver.api.rest.common.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

@Schema(description = "페이지네이션 응답")
data class CustomPage<T>(
    @field:Schema(description = "페이지 내용")
    val content: List<T>,

    @field:Schema(description = "전체 페이지 수", example = "5")
    val totalPages: Int,

    @field:Schema(description = "전체 항목 수", example = "42")
    val totalElements: Long,

    @field:Schema(description = "페이지 크기", example = "10")
    val size: Int,

    @field:Schema(description = "현재 페이지 번호 (1부터 시작)", example = "1")
    val currentPage: Int,

    @field:Schema(description = "첫 페이지 여부", example = "true")
    val firstPage: Boolean,

    @field:Schema(description = "마지막 페이지 여부", example = "false")
    val lastPage: Boolean,
) {
    companion object {
        fun <T> of(page: Page<T>): CustomPage<T> {
            return CustomPage(
                currentPage = page.number + 1, // 0-based → 1-based
                content = page.content,
                totalPages = page.totalPages,
                totalElements = page.totalElements,
                size = page.size,
                firstPage = page.isFirst,
                lastPage = page.isLast,
            )
        }

        fun startWithIndexOne(page: Int): Int = if (page > 0) page - 1 else 0
    }
}
