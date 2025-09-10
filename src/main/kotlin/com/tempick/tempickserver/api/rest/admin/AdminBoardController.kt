package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminCreateBoardRequest
import com.tempick.tempickserver.api.rest.admin.dto.request.AdminUpdateBoardRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminBoardResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminBoardService
import com.tempick.tempickserver.application.admin.dto.AdminBoardData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "어드민 보드", description = "관리자 보드 관리 API")
class AdminBoardController(
    private val adminBoardService: AdminBoardService
) {

    @PostMapping(
        value = ["/admin/board"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "보드 생성",
        description = "새로운 보드를 생성합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "보드 생성 성공",
                content = [Content(schema = Schema(implementation = AdminBoardResponse::class))]
            ),
            ApiResponse(responseCode = "400", description = "카테고리를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.")
        ]
    )
    fun createBoard(@RequestBody request: AdminCreateBoardRequest): RestResponse<AdminBoardResponse> {
        val board = adminBoardService.create(
            AdminBoardData(
                name = request.name,
                categoryId = request.categoryId
            )
        )

        return RestResponse.success(AdminBoardResponse.from(board))
    }

    @GetMapping(
        value = ["/admin/board/{boardId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "보드 상세 조회",
        description = "특정 ID의 보드 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "보드 조회 성공",
                content = [Content(schema = Schema(implementation = AdminBoardResponse::class))]
            ),
            ApiResponse(responseCode = "400", description = "보드를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.")
        ]
    )
    @Deprecated("카테고리를 통해 조회되므로 사용 되지 않습니다")
    fun getBoardById(@PathVariable boardId: Long): RestResponse<AdminBoardResponse> {
        val board = adminBoardService.getBoardById(boardId)

        return RestResponse.success(AdminBoardResponse.from(board))
    }

    @GetMapping(
        value = ["/admin/board"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "모든 보드 조회",
        description = "모든 보드 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "보드 목록 조회 성공",
                content = [Content(array = ArraySchema(schema = Schema(implementation = AdminBoardResponse::class)))]
            ),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.")
        ]
    )
    @Deprecated("카테고리를 통해 조회되므로 사용 되지 않습니다")
    fun getAllBoards(): RestResponse<List<AdminBoardResponse>> {
        return RestResponse.success(adminBoardService.getAllBoards()
            .map { AdminBoardResponse.from(it) })
    }

    @DeleteMapping(
        value = ["/admin/board/{boardId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "보드 삭제",
        description = "특정 ID의 보드를 삭제합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "보드 삭제 성공"
            ),
            ApiResponse(responseCode = "400", description = "보드를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.")
        ]
    )
    fun deleteBoard(@PathVariable boardId: Long): RestResponse<Any> {
        adminBoardService.delete(boardId)
        return RestResponse.success()
    }

    @PutMapping(
        value = ["/admin/board"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Transactional
    @Operation(
        summary = "보드 수정",
        description = "기존 보드를 수정합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "보드 수정 성공",
                content = [Content(schema = Schema(implementation = AdminBoardResponse::class))]
            ),
            ApiResponse(responseCode = "400", description = "보드를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.")
        ]
    )
    fun updateBoard(@RequestBody request: AdminUpdateBoardRequest): RestResponse<AdminBoardResponse> {
        val board = adminBoardService.update(
            AdminBoardData(
                id = request.id,
                name = request.name,
                permission = request.permission,
                isMainExposed = request.isMainExposed
            )
        )

        return RestResponse.success(AdminBoardResponse.from(board))
    }
}
