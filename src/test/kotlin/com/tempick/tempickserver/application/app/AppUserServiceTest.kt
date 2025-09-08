package com.tempick.tempickserver.application.app

import com.tempick.tempickserver.domain.repository.UserRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AppUserServiceTest : StringSpec({
    val userRepository = mockk<UserRepository>()
    val appUserService = AppUserService(userRepository)

    "닉네임 중복 확인 - 중복된 닉네임이 있는 경우 true 반환" {
        val nickname = "testNickname"
        every { userRepository.existsUserByNickname(nickname) } returns true

        val result = appUserService.checkDuplicateNickname(nickname)

        result shouldBe true
        verify(exactly = 1) { userRepository.existsUserByNickname(nickname) }
    }

    "닉네임 중복 확인 - 중복된 닉네임이 없는 경우 false 반환" {
        val nickname = "newNickname"
        every { userRepository.existsUserByNickname(nickname) } returns false

        val result = appUserService.checkDuplicateNickname(nickname)

        result shouldBe false
        verify(exactly = 1) { userRepository.existsUserByNickname(nickname) }
    }
})