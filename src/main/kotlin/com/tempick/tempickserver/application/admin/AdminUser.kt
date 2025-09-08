package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object AdminUser {

    fun getUserId(): Long? {
        val request = getCurrentRequest() ?: return null
        return request.getAttribute("userId") as? Long
    }

    private fun getCurrentRequest(): HttpServletRequest? {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
            requestAttributes?.request
        } catch (_: Exception) {
            throw CoreException(ErrorType.DEFAULT_ERROR, "Cannot get current request")
        }
    }
}