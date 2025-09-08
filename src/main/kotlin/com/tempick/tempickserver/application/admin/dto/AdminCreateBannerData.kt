package com.tempick.tempickserver.application.admin.dto

import com.tempick.tempickserver.domain.entitiy.Banner

data class AdminCreateBannerData (
    val id: Long? = null,
    val bannerImageUrl: String,
    val displaySequence: Int
) {
    fun toEntity(): Banner {
        return Banner(
            id = id ?: 0L,
            bannerImageUrl = bannerImageUrl,
            displaySequence = displaySequence,
        )
    }
}
