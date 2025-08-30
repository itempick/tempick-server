package com.tempick.tempickserver.api.rest.common

import com.tempick.tempickserver.api.rest.common.dto.response.ImageUploadResponse
import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.common.S3MediaUploader
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "이미지", description = "이미지 업로드 API")
@RestController
class ImageController(
    private val uploader: S3MediaUploader,
) {

    @Operation(
        summary = "이미지 업로드",
        description = "multipart/form-data 형식으로 이미지를 업로드합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "업로드 성공",
                content = [
                    Content(
                        schema = Schema(
                            implementation = ImageUploadResponse::class,
                        ),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = [
                    Content(
                        schema = Schema(
                            implementation = RestResponse::class,
                        ),
                    ),
                ],
            ),
            ApiResponse(
                responseCode = "413",
                description = "파일 크기 초과",
                content = [
                    Content(
                        schema = Schema(
                            implementation = RestResponse::class,
                        ),
                    ),
                ],
            ),
        ],
    )
    @PostMapping(
        path = ["/common/image"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadImage(
        @Parameter(description = "업로드할 이미지 파일", required = true)
        @RequestParam
        file: MultipartFile,
    ): ResponseEntity<ImageUploadResponse> {
        val image = uploader.upload(file)
        return ResponseEntity.ok(ImageUploadResponse(image.imageUrl, image.originalFileName))
    }
}
