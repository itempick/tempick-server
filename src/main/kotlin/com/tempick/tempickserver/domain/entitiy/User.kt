package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.UserRole
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val loginId: String,
    val password: String,
    val role: UserRole,
)