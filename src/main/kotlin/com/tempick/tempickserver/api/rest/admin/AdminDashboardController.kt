package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminDashboardResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminDashboardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "어드민 대시보드", description = "관리자 대시보드 API")
@RestController
@RequestMapping("/admin/dashboard")
class AdminDashboardController(
    private val adminDashboardService: AdminDashboardService
) {
    @Operation(summary = "대시보드 데이터 조회")
    @GetMapping
    fun getDashboardData(): RestResponse<AdminDashboardResponse> {
        return RestResponse.success(adminDashboardService.getDashboardData())
    }
}
