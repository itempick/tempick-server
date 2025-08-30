package com.tempick.tempickserver.api.support.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus

enum class ErrorType(
    val status: HttpStatus,
    val code: ErrorCode,
    val message: String,
    val logLevel: LogLevel,
) {
    DEFAULT_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        ErrorCode.E403,
        "개발자가 예상하지 못한 에러입니다.",
        LogLevel.ERROR,
    ),

    FORBIDDEN(
        HttpStatus.FORBIDDEN,
        ErrorCode.E403,
        "접근이 제한 되었습니다.",
        LogLevel.ERROR,
    )
}
