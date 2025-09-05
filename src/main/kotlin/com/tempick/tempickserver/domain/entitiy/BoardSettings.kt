package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.Permission
import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class BoardSettings (
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    var board: Board,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category,

    @ElementCollection(targetClass = Permission::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "board_permissions", joinColumns = [JoinColumn(name = "board_settings_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    var permissions: MutableSet<Permission> = mutableSetOf(),

    @Column(nullable = false)
    var isMainExposed: Boolean = false,
)