package com.tempick.tempickserver.configuration

import com.tempick.tempickserver.domain.entitiy.VisitorLog
import com.tempick.tempickserver.domain.repository.VisitorLogRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class VisitorLogInterceptor(
    private val visitorLogRepository: VisitorLogRepository
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        visitorLogRepository.save(VisitorLog())
        return true
    }
}
