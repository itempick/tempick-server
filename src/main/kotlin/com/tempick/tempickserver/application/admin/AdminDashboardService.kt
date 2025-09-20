package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminDashboardResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.DailyStatResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.MonthlyStatResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.TopPostResponse
import com.tempick.tempickserver.domain.repository.PostRepository
import com.tempick.tempickserver.domain.repository.VisitorLogRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

@Service
class AdminDashboardService(
    private val postRepository: PostRepository,
    private val visitorLogRepository: VisitorLogRepository
) {

    @Transactional(readOnly = true)
    fun getDashboardData(): AdminDashboardResponse {
        val today = LocalDate.now()
        val sevenDaysAgo = today.minusDays(6)
        val startOfMonth = today.withDayOfMonth(1)
        val startOfSixMonthsAgo = today.minusMonths(5).withDayOfMonth(1)

        val dailyPostCounts = getDailyPostCounts(sevenDaysAgo.atStartOfDay(), today.atTime(23, 59, 59))
        val monthlyPostCounts = getMonthlyPostCounts(startOfSixMonthsAgo.atStartOfDay(), today.atTime(23, 59, 59))
        val dailyVisitorCounts = getDailyVisitorCounts(sevenDaysAgo.atStartOfDay(), today.atTime(23, 59, 59))
        val monthlyVisitorCounts = getMonthlyVisitorCounts(startOfSixMonthsAgo.atStartOfDay(), today.atTime(23, 59, 59))
        val weeklyTopPosts = getTopPosts(sevenDaysAgo.atStartOfDay(), today.atTime(23, 59, 59))
        val monthlyTopPosts = getTopPosts(startOfMonth.atStartOfDay(), today.atTime(23, 59, 59))

        return AdminDashboardResponse(
            dailyPostCounts,
            monthlyPostCounts,
            dailyVisitorCounts,
            monthlyVisitorCounts,
            weeklyTopPosts,
            monthlyTopPosts
        )
    }

    private fun getDailyPostCounts(startDate: LocalDateTime, endDate: LocalDateTime): List<DailyStatResponse> {
        return postRepository.findDailyPostCounts(startDate, endDate).map {            
            DailyStatResponse(
                (it[0] as Date).toLocalDate(),
                (it[1] as Number).toLong()
            )
        }
    }

    private fun getMonthlyPostCounts(startDate: LocalDateTime, endDate: LocalDateTime): List<MonthlyStatResponse> {
        return postRepository.findMonthlyPostCounts(startDate, endDate).map {
            MonthlyStatResponse(
                YearMonth.of(it[0] as Int, it[1] as Int),
                (it[2] as Number).toLong()
            )
        }
    }

    private fun getDailyVisitorCounts(startDate: LocalDateTime, endDate: LocalDateTime): List<DailyStatResponse> {
        return visitorLogRepository.findDailyVisitorCounts(startDate, endDate).map {
            DailyStatResponse(
                (it[0] as Date).toLocalDate(),
                (it[1] as Number).toLong()
            )
        }
    }

    private fun getMonthlyVisitorCounts(startDate: LocalDateTime, endDate: LocalDateTime): List<MonthlyStatResponse> {
        return visitorLogRepository.findMonthlyVisitorCounts(startDate, endDate).map {
            MonthlyStatResponse(
                YearMonth.of(it[0] as Int, it[1] as Int),
                (it[2] as Number).toLong()
            )
        }
    }

    private fun getTopPosts(startDate: LocalDateTime, endDate: LocalDateTime): List<TopPostResponse> {
        return postRepository.findTop5ByViewCount(PageRequest.of(0, 5), startDate, endDate).map {
            TopPostResponse(
                it.id,
                it.title,
                it.viewCount,
                it.createdAt.toLocalDate()
            )
        }
    }
}
