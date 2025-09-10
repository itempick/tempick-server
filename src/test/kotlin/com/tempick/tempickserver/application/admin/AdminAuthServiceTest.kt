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
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.password.PasswordEncoder

class AdminAuthServiceTest : StringSpec({
    val userRepository = mockk<UserRepository>()
    val userAuthRepository = mockk<UserAuthRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val jwtTokenProvider = mockk<JwtTokenProvider>()
    val adminAuthProperties = mockk<AdminAuthProperties>()

    val adminAuthService = AdminAuthService(
        userRepository = userRepository,
        userAuthRepository = userAuthRepository,
        passwordEncoder = passwordEncoder,
        jwtTokenProvider = jwtTokenProvider,
        adminAuthProperties = adminAuthProperties
    )

    "관리자 계정으로 성공적으로 로그인할 수 있다" {
        val email = "admin@example.com"
        val password = "password"
        val loginData = AdminLoginData(email = email, password = password)

        val user = mockk<User>()
        val userAuth = UserAuth(
            id = 1L,
            email = email,
            password = "encodedPassword",
            role = UserRole.ADMIN,
            user = user
        )

        val expectedToken = "jwt.token.string"

        every { userAuthRepository.findActiveByEmail(email) } returns userAuth
        every { passwordEncoder.matches(password, userAuth.password) } returns true
        every { jwtTokenProvider.createToken(email, "ROLE_${userAuth.role.name}") } returns expectedToken

        val result = adminAuthService.login(loginData)

        result shouldBe expectedToken
    }

    "존재하지 않는 사용자로 로그인하면 예외가 발생한다" {
        // given
        val email = "nonexistent@example.com"
        val password = "password"
        val loginData = AdminLoginData(email = email, password = password)

        every { userAuthRepository.findActiveByEmail(email) } returns null

        val exception = shouldThrow<CoreException> {
            adminAuthService.login(loginData)
        }

        exception.errorType shouldBe ErrorType.USER_NOT_FOUND
    }

    "잘못된 비밀번호로 로그인하면 예외가 발생한다" {
        val email = "admin@example.com"
        val password = "wrongPassword"
        val loginData = AdminLoginData(email = email, password = password)

        val user = mockk<User>()
        val userAuth = UserAuth(
            id = 1L,
            email = email,
            password = "encodedPassword",
            role = UserRole.ADMIN,
            user = user
        )

        every { userAuthRepository.findActiveByEmail(email) } returns userAuth
        every { passwordEncoder.matches(password, userAuth.password) } returns false

        val exception = shouldThrow<CoreException> {
            adminAuthService.login(loginData)
        }

        exception.errorType shouldBe ErrorType.INVALID_PASSWORD
    }
})
