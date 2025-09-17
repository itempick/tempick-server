package com.tempick.tempickserver.api.rest.admin.dto.respnose

data class AdminDashboardResponse(
    val dailyPostCounts: List<DailyStatResponse>,
    val monthlyPostCounts: List<MonthlyStatResponse>,
    val dailyVisitorCounts: List<DailyStatResponse>,
    val monthlyVisitorCounts: List<MonthlyStatResponse>,
    val weeklyTopPosts: List<TopPostResponse>,
    val monthlyTopPosts: List<TopPostResponse>
)

