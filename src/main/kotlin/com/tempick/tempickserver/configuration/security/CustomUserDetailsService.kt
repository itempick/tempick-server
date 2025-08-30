package com.tempick.tempickserver.configuration.security

import com.tempick.tempickserver.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByLoginId(email)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $email")

        return CustomUserDetails(
            id = user.id,
            loginId = user.loginId,
            password = user.password,
            role = user.role,
        )
    }
}
