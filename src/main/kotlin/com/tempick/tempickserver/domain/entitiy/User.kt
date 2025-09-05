package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.UserRole
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true)
    val loginId: String,

    @Column(unique = true)
    val email: String,

    @Column(unique = true)
    val nickName: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: UserRole,

    @Column(nullable = true)
    var profileImageUrl: String? = null,

    @Column(nullable = false)
    val isActive: Boolean = true
) : BaseDatetime() {
    fun isPasswordMatch(rawPassword: String, encoder: PasswordEncoder): Boolean {
        return encoder.matches(rawPassword, this.password)
    }
}
