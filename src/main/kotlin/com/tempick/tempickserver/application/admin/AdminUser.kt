package com.tempick.tempickserver.application.admin

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

object AdminUser {

    /**
     * 현재 사용자의 ID를 반환
     */
    fun getUserId(): Long? {
        val request = getCurrentRequest() ?: return null
        return request.getAttribute("userId") as? Long
    }

    private fun getCurrentRequest(): HttpServletRequest? {
        return try {
            val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
            requestAttributes?.request
        } catch (e: Exception) {
            null
        }
    }
}