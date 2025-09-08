package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Category(
    @Id @GeneratedValue(strategy = IDENTITY)
    var id: Long = 0L,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var sequence: Int,

    @Column(nullable = false)
    var isDeleted: Boolean = false
) : BaseDatetime() {
    fun delete() {
        isDeleted = true
    }

    fun checkDeleted() = this.isDeleted

    fun update(name: String, sequence: Int): Category {
        if (name == this.name) {
            this.name = name
        }

        if (sequence == this.sequence) {
            this.sequence = sequence
        }

        return this
    }
}
