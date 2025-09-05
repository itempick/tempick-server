package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class Board(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category,
) : BaseDatetime()
