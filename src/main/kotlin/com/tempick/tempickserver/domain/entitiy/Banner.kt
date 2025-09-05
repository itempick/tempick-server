package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Banner(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var bannerImageUrl: String,

    @Column(nullable = false)
    val startedAt: LocalDateTime,

    @Column(nullable = false)
    val endedAt: LocalDateTime,
): BaseDatetime()