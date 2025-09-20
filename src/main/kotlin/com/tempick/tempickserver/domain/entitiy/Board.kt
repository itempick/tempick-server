package com.tempick.tempickserver.domain.entitiy

import com.tempick.tempickserver.domain.enums.Permission
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Board(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    var category: Category,

    @Enumerated(EnumType.STRING)
    var permission: Permission = Permission.PUBLIC,

    @Column(nullable = false)
    var isMainExposed: Boolean = false,

    @Column(nullable = false)
    var isDeleted: Boolean = false
) : BaseDatetime() {
    fun checkDeleted(): Boolean {
        return this.isDeleted
    }

    fun delete() {
        this.isDeleted = true
    }

    fun update(name: String, permission: Permission, isMainExposed: Boolean): Board {
        if (name != this.name) {
            this.name = name
        }

        if (permission != this.permission) {
            this.permission = permission
        }

        if (isMainExposed != this.isMainExposed) {
            this.isMainExposed = isMainExposed
        }
        return this
    }

    fun validateCategoryChange(categoryId: Long): Boolean {
        // 카테고리는 변경 될 수 없음
        return categoryId == 0L || categoryId == this.category.id
    }
}
