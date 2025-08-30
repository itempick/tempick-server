package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.rest.admin.dto.request.AdminLoginRequest
import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.configuration.properties.AdminAuthProperties
import com.tempick.tempickserver.configuration.security.JwtTokenProvider
import com.tempick.tempickserver.domain.entitiy.User
import com.tempick.tempickserver.domain.enums.UserRole
import com.tempick.tempickserver.domain.repository.UserRepository
import jakarta.annotation.PostConstruct
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminAuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val adminAuthProperties: AdminAuthProperties,
) {
    @PostConstruct
    @Transactional
    fun initAdmin() {
        if (!userRepository.existsByLoginId(adminAuthProperties.loginId)) {
            val encoded = passwordEncoder.encode(adminAuthProperties.password)
            val admin = User(
                loginId = adminAuthProperties.loginId,
                password = encoded,
                role = UserRole.ADMIN,
            )
            userRepository.save(admin)
        }
    }

    @Transactional(readOnly = true)
    fun login(request: AdminLoginRequest): String {
        val user = userRepository.findByLoginId(request.loginId)
            ?: throw CoreException(ErrorType.USER_NOT_FOUND)

        if (!user.isPasswordMatch(request.password, passwordEncoder)) {
            throw CoreException(ErrorType.INVALID_PASSWORD)
        }

        return jwtTokenProvider.createToken(
            user.loginId,
            "ROLE_${user.role.name}",
        )
    }
}