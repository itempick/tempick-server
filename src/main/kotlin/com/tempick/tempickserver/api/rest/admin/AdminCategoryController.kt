package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminCreateCategoryRequest
import com.tempick.tempickserver.api.rest.admin.dto.request.AdminUpdateCategoryRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminCategoryBoardResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminCategoryResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminCategoryService
import com.tempick.tempickserver.application.admin.dto.AdminCategoryData
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
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
@Tag(name = "어드민 카테고리", description = "관리자 카테고리 관리 API")
class AdminCategoryController(
    private val adminCategoryService: AdminCategoryService,
) {

    @PostMapping(
        value = ["/admin/categories"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @Transactional
    @Operation(
        summary = "카테고리 생성",
        description = "새로운 카테고리를 생성합니다.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "카테고리 생성 성공",
                content = [Content(array = ArraySchema(schema = Schema(implementation = AdminCategoryResponse::class)))],
            ),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
        ],
    )
    fun createCategory(
        @RequestBody @Valid request: AdminCreateCategoryRequest,
    ): RestResponse<AdminCategoryResponse> {
        val data = AdminCategoryData(
            id = 0L,
            name = request.name,
            sequence = request.sequence
        )
        return RestResponse.success(AdminCategoryResponse.from(adminCategoryService.create(data)))
    }

    @PutMapping(
        value = ["/admin/categories"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @Transactional
    @Operation(
        summary = "카테고리 수정",
        description = "기존 카테고리를 수정합니다.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "카테고리 수정 성공",
                content = [Content(array = ArraySchema(schema = Schema(implementation = AdminCategoryResponse::class)))],
            ),
            ApiResponse(responseCode = "400", description = "카테고리를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
        ],
    )
    fun updateCategory(
        @RequestBody @Valid request: AdminUpdateCategoryRequest,
    ): RestResponse<AdminCategoryResponse> {
        val data = AdminCategoryData(
            id = request.id,
            name = request.name,
            sequence = request.sequence
        )
        return RestResponse.success(AdminCategoryResponse.from(adminCategoryService.update(data)))
    }

    @GetMapping(
        value = ["/admin/categories"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @Operation(
        summary = "모든 카테고리, 게시판 조회",
        description = "모든 카테고리, 게시판 정보를 조회합니다.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "카테고리, 게시판 목록 조회 성공",
                content = [Content(array = ArraySchema(schema = Schema(implementation = AdminCategoryBoardResponse::class)))],
            ),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
        ],
    )
    fun getAllCategories(): RestResponse<List<AdminCategoryBoardResponse>> {
        val categoriesWithBoards = adminCategoryService.getAllCategoriesWithBoards()
        return RestResponse.success(categoriesWithBoards.map { AdminCategoryBoardResponse.from(it) })
    }

    @DeleteMapping(
        value = ["/admin/categories/{categoryId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @Transactional
    @Operation(
        summary = "카테고리 삭제",
        description = "특정 ID의 카테고리를 삭제합니다.",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "카테고리 삭제 성공",
                content = [Content(array = ArraySchema(schema = Schema(implementation = AdminCategoryResponse::class)))],
            ),
            ApiResponse(responseCode = "400", description = "카테고리를 찾을 수 없습니다."),
            ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
        ],
    )
    fun deleteCategory(
        @PathVariable categoryId: Long,
    ): RestResponse<Any> {
        adminCategoryService.delete(categoryId)
        return RestResponse.success()
    }
}
