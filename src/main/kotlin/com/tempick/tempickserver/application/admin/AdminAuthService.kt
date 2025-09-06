package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminLoginData
import com.tempick.tempickserver.configuration.properties.AdminAuthProperties
import com.tempick.tempickserver.configuration.security.JwtTokenProvider
import com.tempick.tempickserver.domain.entitiy.User
import com.tempick.tempickserver.domain.entitiy.UserAuth
import com.tempick.tempickserver.domain.enums.UserRole
import com.tempick.tempickserver.domain.repository.UserAuthRepository
import com.tempick.tempickserver.domain.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminAuthService(
    private val userRepository: UserRepository,
    private val userAuthRepository: UserAuthRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminAuthProperties: AdminAuthProperties,
) {
    @PostConstruct
    @Transactional
    fun initAdmin() {
        if (!userAuthRepository.existsByEmail(adminAuthProperties.email)) {
            val encoded = passwordEncoder.encode(adminAuthProperties.password)
            val adminProfile = User(
                nickname = "관리자",
            )
            val savedProfile = userRepository.save(adminProfile)
            val adminAuth = UserAuth(
                email = adminAuthProperties.email,
                password = encoded,
                role = UserRole.ADMIN,
                user = savedProfile,
            )
            userAuthRepository.save(adminAuth)
        }
    }

    @Transactional(readOnly = true)
    fun login(request: AdminLoginData): String {
        val auth = userAuthRepository.findActiveByEmail(request.email)
            ?: throw CoreException(ErrorType.USER_NOT_FOUND)

        if (!passwordEncoder.matches(request.password, auth.password)) {
            throw CoreException(ErrorType.INVALID_PASSWORD)
        }

        return jwtTokenProvider.createToken(
            auth.email,
            "ROLE_${auth.role.name}",
        )
    }
}