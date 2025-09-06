package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.*

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true)
    val nickname: String,

    @Column(nullable = true)
    var profileImageUrl: String? = null,
) : BaseDatetime()
