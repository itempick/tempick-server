package com.tempick.tempickserver.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "admin.auth")
class AdminAuthProperties {
    lateinit var loginId: String
    lateinit var password: String
}