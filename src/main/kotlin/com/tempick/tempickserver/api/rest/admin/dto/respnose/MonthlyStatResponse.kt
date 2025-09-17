package com.tempick.tempickserver.api.rest.admin.dto.respnose

import java.time.YearMonth

data class MonthlyStatResponse(
    val month: YearMonth,
    val count: Long
)