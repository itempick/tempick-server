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
        ErrorCode.E500,
        "개발자가 예상하지 못한 에러입니다.",
        LogLevel.ERROR,
    ),

    FORBIDDEN(
        HttpStatus.FORBIDDEN,
        ErrorCode.E403,
        "접근이 제한 되었습니다.",
        LogLevel.ERROR,
    ),
    USER_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "사용자를 찾을 수 없습니다.",
        LogLevel.INFO,
    ),
    INVALID_PASSWORD(
        HttpStatus.FORBIDDEN,
        ErrorCode.E403,
        "비밀번호가 일치하지 않습니다.",
        LogLevel.INFO,
    ),
    FILE_SIZE_EXCEEDED(
        HttpStatus.PAYLOAD_TOO_LARGE,
        ErrorCode.E400,
        "파일 크기가 제한을 초과했습니다.",
        LogLevel.INFO,
    ),
}
