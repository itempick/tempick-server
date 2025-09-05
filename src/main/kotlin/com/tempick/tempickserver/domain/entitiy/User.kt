package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true)
    val loginId: String,

    @Column
    val password: String,

    @Column
    @Enumerated(EnumType.STRING)
    val role: UserRole,

    @Column
    val isActive: Boolean = true,
) : BaseDatetime() {
    fun isPasswordMatch(rawPassword: String, encoder: PasswordEncoder): Boolean {
        return encoder.matches(rawPassword, this.password)
    }
}
