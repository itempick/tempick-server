package com.tempick.tempickserver.configuration.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider(
    private val securityProperties: SecurityProperties,
) {

    private val key: Key = Keys.hmacShaKeyFor(securityProperties.jwt.secretKey.toByteArray(StandardCharsets.UTF_8))
    private val tokenValidityInSeconds = 100L

    /**
     * 액세스 토큰 생성
     */
    fun createToken(email: String, role: String): String {
        val now = Date()
        Date(now.time + tokenValidityInSeconds * 1000)

        return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(now)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * 토큰에서 이메일 추출
     */
    fun getEmailFromToken(token: String): String {
        return getClaims(token).subject
    }

    /**
     * 토큰 유효성 검증
     */
    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
