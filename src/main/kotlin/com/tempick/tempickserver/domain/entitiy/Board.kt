package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.Permission
import jakarta.persistence.*
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GenerationType.IDENTITY

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
    var permission: Permission? = null,

    @Column(nullable = false)
    var isMainExposed: Boolean = false,

    @Column(nullable = false)
    var isDeleted: Boolean = false
) : BaseDatetime() {
    fun checkDeleted(): Boolean {
        return this.isDeleted
    }
}
