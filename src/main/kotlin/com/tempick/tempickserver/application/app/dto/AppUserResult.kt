package com.tempick.tempickserver.application.app.dto

import com.tempick.tempickserver.domain.entitiy.User

data class AppUserResult(
    val id: Long,
    val nickName: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun from(entity: User): AppUserResult = AppUserResult(
            id = entity.id,
            nickName = entity.nickname,
            profileImageUrl = entity.profileImageUrl,
        )
    }
}