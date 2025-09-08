package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Banner(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var bannerImageUrl: String,

    @Column(nullable = false, unique = true)
    var displaySequence: Int,

    @Column(nullable = false)
    val isDeleted: Boolean = false
): BaseDatetime()