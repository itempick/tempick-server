package com.tempick.tempickserver.api.support

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.api.support.response.RestResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class ApiControllerAdvice {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CoreException::class)
    fun handleCoreException(e: CoreException): ResponseEntity<RestResponse<Any>> {
        when (e.errorType.logLevel) {
            LogLevel.ERROR -> log.error("CoreException : {}", e.message, e)
            LogLevel.WARN -> log.warn("CoreException : {}", e.message, e)
            else -> log.info("CoreException : {}", e.message)
        }
        return ResponseEntity(RestResponse.exception(e.errorType, e.data), e.errorType.status)
    }

    @ExceptionHandler(value = [AccessDeniedException::class, AuthorizationDeniedException::class])
    fun handleAccessDenied(e: RuntimeException): ResponseEntity<RestResponse<Any>> {
        log.warn("Access Denied: {}", e.message)
        return ResponseEntity(
            RestResponse.exception(ErrorType.FORBIDDEN),
            ErrorType.FORBIDDEN.status,
        )
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(e: BindException): ResponseEntity<RestResponse<Any>> {
        val fieldErrors = e.bindingResult.fieldErrors.map { fe ->
            mapOf(
                "field" to fe.field,
                "rejectedValue" to fe.rejectedValue,
                "message" to (fe.defaultMessage ?: "유효하지 않은 값입니다."),
            )
        }
        log.info("BindException validation failed: {}", fieldErrors)
        return ResponseEntity(
            RestResponse.error(ErrorType.VALIDATION_ERROR, mapOf("errors" to fieldErrors)),
            ErrorType.VALIDATION_ERROR.status,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<RestResponse<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(RestResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.status)
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException(e: MaxUploadSizeExceededException): ResponseEntity<RestResponse<Any>> {
        log.warn("MaxUploadSizeExceededException: {}", e.message)
        return ResponseEntity(
            RestResponse.error(ErrorType.FILE_SIZE_EXCEEDED),
            ErrorType.FILE_SIZE_EXCEEDED.status
        )
    }
}
