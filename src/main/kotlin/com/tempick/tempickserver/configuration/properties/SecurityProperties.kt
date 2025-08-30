package com.tempick.tempickserver.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * YAML/ENV 파일로부터 보안 설정을 로드하는 클래스
 */
@Configuration
@ConfigurationProperties(prefix = "app.security")
data class SecurityProperties(
    var jwt: JwtProperties = JwtProperties(),
    var publicPaths: List<String> = emptyList(),
    var roleBasedPaths: Map<String, List<String>> = emptyMap(),
    var methodBasedPaths: Map<String, List<PathRoleMapping>> = emptyMap(),
    var admins: List<AdminCredentials> = emptyList(),
) {
    /**
     * HTTP 메서드별 경로와 역할 매핑을 위한 클래스
     */
    data class PathRoleMapping(
        var path: String,
        var roles: List<String>,
    )

    data class JwtProperties(
        var secretKey: String = "",
        var expirationMs: Long = 0,
        var refreshExpirationMs: Long = 0,
    )

    /**
     * 초기 ADMIN 계정을 위한 자격 증명
     */
    data class AdminCredentials(
        var loginId: String = "",
        var password: String = "",
    )
}
