package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.rest.common.dto.response.CustomPage.Companion.startWithIndexOne
import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminUserResult
import com.tempick.tempickserver.application.admin.dto.AdminUserResult.Companion.toAdminUserResult
import com.tempick.tempickserver.domain.repository.UserAuthRepository
import com.tempick.tempickserver.domain.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserService(
    private val userRepository: UserRepository,
    private val userAuthRepository: UserAuthRepository,
) {
    @Transactional(readOnly = true)
    fun getUsers(page: Int, size: Int): Page<AdminUserResult> {
        val pageable: Pageable = PageRequest.of(startWithIndexOne(page), size)
        val users = userRepository.findAll(pageable)

        return users.map { user ->
            val userAuth = userAuthRepository.findByUserId(user.id)
                ?: throw CoreException(ErrorType.USER_NOT_FOUND)
            toAdminUserResult(user, userAuth)
        }
    }

    @Transactional(readOnly = true)
    fun getUserDetail(userId: Long): AdminUserResult {
        val user = userRepository.findActiveUserById(userId)
            ?: throw CoreException(ErrorType.USER_NOT_FOUND)

        val userAuth = userAuthRepository.findByUserId(userId)
            ?: throw CoreException(ErrorType.USER_NOT_FOUND)

        return toAdminUserResult(user, userAuth)
    }

    @Transactional
    fun updateUser(
        userId: Long,
        nickname: String,
        isBlacklisted: Boolean,
        reason: String,
    ): AdminUserResult {
        val user = userRepository.findActiveUserById(userId)
            ?: throw CoreException(ErrorType.USER_NOT_FOUND)

        user.updateNickname(nickname)
        user.updateBlacklist(isBlacklisted, reason)

        return getUserDetail(userId)
    }
}
