package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminCreateBoardRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminBoardResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminBoardService
import com.tempick.tempickserver.application.admin.dto.AdminBoardData
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminBoardController(
    private val adminBoardService: AdminBoardService
) {

    @PostMapping("/admin/board")
    fun createBoard(@RequestBody request: AdminCreateBoardRequest): RestResponse<AdminBoardResponse> {
        val board = adminBoardService.create(
            AdminBoardData(
                name = request.name,
                categoryId = request.categoryId
            )
        )

        return RestResponse.success(AdminBoardResponse.from(board))
    }
}