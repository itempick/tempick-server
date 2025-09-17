package com.tempick.tempickserver.domain.repository

import com.tempick.tempickserver.domain.entitiy.VisitorLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface VisitorLogRepository : JpaRepository<VisitorLog, Long> {
    @Query("SELECT DATE(v.createdAt) as date, COUNT(v.id) as count FROM VisitorLog v WHERE v.createdAt BETWEEN :startDate AND :endDate GROUP BY DATE(v.createdAt)")
    fun findDailyVisitorCounts(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<Array<Any>>

    @Query("SELECT FUNCTION('YEAR', v.createdAt) as year, FUNCTION('MONTH', v.createdAt) as month, COUNT(v.id) as count FROM VisitorLog v WHERE v.createdAt BETWEEN :startDate AND :endDate GROUP BY FUNCTION('YEAR', v.createdAt), FUNCTION('MONTH', v.createdAt)")
    fun findMonthlyVisitorCounts(@Param("startDate") startDate: LocalDateTime, @Param("endDate") endDate: LocalDateTime): List<Array<Any>>
}
