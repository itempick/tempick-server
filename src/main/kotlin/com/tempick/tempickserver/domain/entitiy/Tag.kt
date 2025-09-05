package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Tag (
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(unique = true)
    var name: String,

    @Column
    var adminOnly: Boolean = false,
): BaseDatetime()