package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.repository.BannerRepository
import org.springframework.stereotype.Service

@Service
class AdminBannerUpsertHandler(
    private val bannerRepository: BannerRepository
) {
    fun updateBanner(bannerData: AdminBannerData): Banner {
        if (bannerData.id == null) {
            throw CoreException(ErrorType.BANNER_NOT_FOUND)
        }

        val existingBanner = bannerRepository.findActiveBanner(bannerData.id)
            ?: throw CoreException(ErrorType.BANNER_NOT_FOUND)

        if (bannerData.displaySequence != existingBanner.displaySequence &&
            bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerData.id)
        ) {
            throw CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)
        }

        existingBanner.apply {
            this.displaySequence = bannerData.displaySequence
            this.bannerImageUrl = bannerData.bannerImageUrl
            this.clickUrl = bannerData.clickUrl
        }

        return bannerRepository.save(existingBanner)
    }

    fun createBanner(bannerData: AdminBannerData): Banner {
        if (bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence)) {
            throw CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)
        }

        return bannerRepository.save(bannerData.toEntity())
    }
}