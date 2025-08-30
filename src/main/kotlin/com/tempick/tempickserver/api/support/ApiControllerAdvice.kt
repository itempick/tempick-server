package com.tempick.tempickserver.api.support

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.api.support.response.RestResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

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

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<RestResponse<Any>> {
        log.error("Exception : {}", e.message, e)
        return ResponseEntity(RestResponse.error(ErrorType.DEFAULT_ERROR), ErrorType.DEFAULT_ERROR.status)
    }
}
