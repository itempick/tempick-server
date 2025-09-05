package com.tempick.tempickserver.application.app

import com.tempick.tempickserver.application.common.EmailService
import com.tempick.tempickserver.domain.repository.UserAuthRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AppUserService(
    private val userAuthRepository: UserAuthRepository,
    private val emailService: EmailService
) {

    @Transactional
    fun register(email: String, password: String) {

    }

    @Transactional
    fun sendVerificationEmail(email: String) {
        emailService.sendEmail(email, "인증 번호 전송", "Please verify your email by clicking on the link below")
    }
}