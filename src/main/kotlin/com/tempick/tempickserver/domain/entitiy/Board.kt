package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.Permission
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class Board(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    var category: Category,

    @Enumerated(EnumType.STRING)
    var permissions: Permission,

    @Column(nullable = false)
    var isMainExposed: Boolean = false,

    @Embedded
    var tag: Tag? = null,

    @Column(nullable = false)
    var isDeleted: Boolean = false
) : BaseDatetime()
