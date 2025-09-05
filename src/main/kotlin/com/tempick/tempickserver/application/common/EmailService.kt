package com.tempick.tempickserver.application.common

interface EmailService {
    fun sendEmail(to: String, subject: String, text: String)
}