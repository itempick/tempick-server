package com.tempick.tempickserver.domain.entitiy

import jakarta.persistence.*

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(unique = true)
    var nickname: String,

    @Column(nullable = true)
    var profileImageUrl: String? = null,

    @Column(nullable = false)
    var isActive: Boolean = true,

    @Column(nullable = false)
    var isBlacklisted: Boolean = false,

    @Column(nullable = true)
    var blacklistReason: String? = null,
) : BaseDatetime() {
    fun updateNickname(nickname: String) {
        if (nickname != this.nickname) {
            this.nickname = nickname
        }
    }

    fun updateBlacklist(isBlacklisted: Boolean, reason: String) {
        if (isBlacklisted != this.isBlacklisted) {
            this.isBlacklisted = isBlacklisted
        }

        if (reason != this.blacklistReason) {
            this.blacklistReason = reason
        }
    }
}
