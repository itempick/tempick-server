package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.UserRole
import jakarta.persistence.*

@Entity
class UserAuth(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: UserRole,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToOne(mappedBy = "userAuth", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, optional = true)
    var emailVerification: UserEmailVerification? = null,

    @Column(nullable = false)
    val isActive: Boolean = true,
) : BaseDatetime()
