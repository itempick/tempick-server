package com.tempick.tempickserver.configuration.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userAuthRepository: com.tempick.tempickserver.domain.repository.UserAuthRepository,
) : UserDetailsService {

    override fun loadUserByUsername(loginId: String): UserDetails {
        val auth = userAuthRepository.findActiveByEmail(loginId)
            ?: throw UsernameNotFoundException("사용자를 찾을 수 없습니다: $loginId")

        return CustomUserDetails(
            id = auth.id,
            loginId = auth.email,
            password = auth.password,
            role = auth.role,
        )
    }
}
