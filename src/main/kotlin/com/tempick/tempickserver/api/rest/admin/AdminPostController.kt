package com.tempick.tempickserver.api.rest.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminCreatePostRequest
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminPostCommentResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminPostDetailResponse
import com.tempick.tempickserver.api.rest.admin.dto.respnose.AdminPostListItemResponse
import com.tempick.tempickserver.api.rest.common.dto.response.CustomPage
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.admin.AdminPostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "어드민 게시물", description = "관리자 게시물 관리 API")
class AdminPostController(
    private val adminPostService: AdminPostService
) {

    @GetMapping(
        value = ["/admin/post"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 목록 조회",
        description = "페이지네이션으로 게시물 목록을 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시물 목록 조회 성공",
                content = [Content(schema = Schema(implementation = CustomPage::class))]
            )
        ]
    )
    fun getPosts(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): RestResponse<CustomPage<AdminPostListItemResponse>> {
        return RestResponse.success(adminPostService.getPosts(page, size))
    }

    @GetMapping(
        value = ["/admin/post/{postId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 상세 조회",
        description = "특정 게시물의 상세 정보를 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시물 상세 조회 성공",
                content = [Content(schema = Schema(implementation = AdminPostDetailResponse::class))]
            )
        ]
    )
    fun getPostDetail(@PathVariable postId: Long): RestResponse<AdminPostDetailResponse> {
        return RestResponse.success(adminPostService.getPostDetail(postId))
    }

    @PostMapping(
        value = ["/admin/boards/{boardId}/posts"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 생성",
        description = "특정 게시판에 게시물을 생성합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "게시물 생성 성공",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun createPost(
        @PathVariable boardId: Long,
        @RequestBody request: AdminCreatePostRequest
    ): RestResponse<Any> {
        return RestResponse.success(adminPostService.createPost(boardId, request))
    }

    @GetMapping(
        value = ["/admin/post/{postId}/comments"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 댓글 목록 조회",
        description = "특정 게시물의 댓글 목록을 페이지네이션으로 조회합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "댓글 목록 조회 성공",
                content = [Content(schema = Schema(implementation = CustomPage::class))]
            )
        ]
    )
    fun getPostComments(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): RestResponse<CustomPage<AdminPostCommentResponse>> {
        return RestResponse.success(adminPostService.getPostComments(postId, page, size))
    }

    @PutMapping(
        value = ["/admin/post/{postId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 수정",
        description = "특정 게시물의 노출 우선순위, 태그, 텍스트, 제목을 수정합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시물 수정 성공",
                content = [Content(schema = Schema(implementation = AdminPostDetailResponse::class))]
            )
        ]
    )
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody request: com.tempick.tempickserver.api.rest.admin.dto.request.AdminUpdatePostRequest
    ): RestResponse<AdminPostDetailResponse> {
        return RestResponse.success(adminPostService.updatePost(postId, request))
    }

    @DeleteMapping(
        value = ["/admin/post/{postId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 삭제",
        description = "특정 게시물을 삭제(soft delete)합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시물 삭제 성공",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun deletePost(
        @PathVariable postId: Long,
    ): RestResponse<Any> {
        adminPostService.deletePost(postId)
        return RestResponse.success()
    }

    @DeleteMapping(
        value = ["/admin/post/comments/{commentId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Operation(
        summary = "게시물 댓글 삭제",
        description = "특정 게시물의 특정 댓글을 삭제(soft delete)합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "댓글 삭제 성공",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun deleteComment(
        @PathVariable commentId: Long,
    ): RestResponse<Any> {
        adminPostService.deleteComment(commentId)
        return RestResponse.success()
    }
}
