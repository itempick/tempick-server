package com.tempick.tempickserver.api.rest.app

import com.tempick.tempickserver.api.support.response.RestResponse
import com.tempick.tempickserver.application.app.AppUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "앱 유저", description = "앱 사용자 관련 API")
class AppUserController(
    val appUserService: AppUserService
) {

    @PostMapping("/app/user/check-duplicate-nickname")
    @Operation(
        summary = "닉네임 중복 여부 확인",
        description = "닉네임이 이미 사용 중인지 여부를 반환합니다. true면 중복, false면 사용 가능합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "정상 응답")
        ]
    )
    fun checkDuplicateNickname(
        @Parameter(
            description = "확인할 닉네임",
            required = true,
            example = "간지서든러"
        )
        nickname: String
    ): RestResponse<Boolean> {
        return RestResponse.success(appUserService.checkDuplicateNickname(nickname))
    }

}