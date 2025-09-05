package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.*

@Entity
class UserEmailVerification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_auth_id", nullable = false, unique = true)
    val userAuth: UserAuth,

    @Column(nullable = false)
    var isEmailVerified: Boolean = false,

    @Column(nullable = true)
    var emailVerificationCode: String? = null,
) : BaseDatetime()
