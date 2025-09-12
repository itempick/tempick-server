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
    VALIDATION_ERROR(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "요청 값이 올바르지 않습니다.",
        LogLevel.INFO,
    ),
    DUPLICATE_EMAIL(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "이미 사용 중인 이메일입니다.",
        LogLevel.INFO,
    ),

    BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "해당 순서로 배너를 등록할 수 없습니다",
        LogLevel.INFO,
    ),

    BANNER_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "배너를 찾을 수 없습니다.",
        LogLevel.INFO,
    ),
    CATEGORY_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "카테고리를 찾을 수 없습니다.",
        LogLevel.INFO,
    ),
    CATEGORY_SEQUENCE_ALREADY_EXISTS(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "카테고리 표시 순서가 이미 존재합니다.",
        LogLevel.INFO,
    ),
    BOARD_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "게시판을 찾을 수 없습니다.",
        LogLevel.INFO,
    ),
    BOARD_CATEGORY_CANNOT_BE_CHANGED(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "게시판의 카테고리는 변경할 수 없습니다.",
        LogLevel.INFO,
    ),
    DUPLICATE_BOARD_NAME(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "게시판 이름은 중복될 수 없습니다.",
        LogLevel.INFO,
    ),
    POST_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "게시물을 찾을 수 없습니다.",
        LogLevel.INFO,
    ),
    COMMENT_NOT_FOUND(
        HttpStatus.BAD_REQUEST,
        ErrorCode.E400,
        "댓글을 찾을 수 없습니다.",
        LogLevel.INFO,
    ),
}
