package com.tempick.tempickserver.infrastructure.email

import com.tempick.tempickserver.application.common.EmailService
import org.springframework.stereotype.Component

@Component
class EmailServiceImpl: EmailService {
    override fun sendEmail(to: String, subject: String, text: String) {
        TODO("Not yet implemented")
    }
}