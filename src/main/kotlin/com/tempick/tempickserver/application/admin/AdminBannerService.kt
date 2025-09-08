package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.application.admin.dto.AdminCreateBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import org.springframework.stereotype.Service

@Service
class AdminBannerService(
    private val adminBannerCommandService: AdminBannerCommandService,
) {

    fun upsertBanner(bannerData: AdminCreateBannerData): Banner {
        return if (bannerData.id != null) {
            adminBannerCommandService.updateBanner(bannerData)
        } else {
            adminBannerCommandService.createBanner(bannerData)
        }
    }
}
