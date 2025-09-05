package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.*

@Entity
class BlackList (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(nullable = true)
    val reason: String? = null
): BaseDatetime()