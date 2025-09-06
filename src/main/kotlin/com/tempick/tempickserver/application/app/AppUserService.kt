package com.tempick.tempickserver.application.app

import com.tempick.tempickserver.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AppUserService(
    private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun checkDuplicateNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }
}