package com.tempick.tempickserver.api.rest.admin.dto.respnose

import java.time.LocalDate

data class DailyStatResponse(
    val date: LocalDate,
    val count: Long
)