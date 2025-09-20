package com.tempick.tempickserver.api.rest.admin.dto.respnose

import java.time.LocalDate

data class TopPostResponse(
    val id: Long,
    val title: String,
    val viewCount: Int,
    val createdAt: LocalDate
)