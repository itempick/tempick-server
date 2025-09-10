package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.repository.BannerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBannerService(
    private val bannerRepository: BannerRepository,
    private val adminBannerUpsertHandler: AdminBannerUpsertHandler
) {

    @Transactional(readOnly = true)
    fun getAllBanners(): List<Banner> {
        return bannerRepository.findAll()
            .asSequence()
            .filter { !it.checkDeleted() }
            .sortedBy { it.displaySequence }
            .toList()
            .ifEmpty { throw CoreException(ErrorType.BANNER_NOT_FOUND) }
    }

    @Transactional(readOnly = true)
    fun getBannerById(bannerId: Long): Banner {
        return bannerRepository.findActiveBanner(bannerId)
            ?: throw CoreException(ErrorType.BANNER_NOT_FOUND)
    }

    @Transactional
    fun upsertBanner(bannerData: AdminBannerData): Banner {
        return if (bannerData.id != null) {
            adminBannerUpsertHandler.updateBanner(bannerData)
        } else {
            adminBannerUpsertHandler.createBanner(bannerData)
        }
    }

    @Transactional
    fun deleteBanner(bannerId: Long) {
        val banner = bannerRepository.findActiveBanner(bannerId)
            ?: throw CoreException(ErrorType.BANNER_NOT_FOUND)

        banner.delete()
    }
}
