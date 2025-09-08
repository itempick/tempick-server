package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminCreateBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.repository.BannerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBannerCommandService(
    private val bannerRepository: BannerRepository,
) {
    @Transactional
    fun updateBanner(bannerData: AdminCreateBannerData): Banner {
        if (bannerData.id == null) {
            throw CoreException(ErrorType.BANNER_NOT_FOUND)
        }

        val existingBanner = bannerRepository.findById(bannerData.id)
            .orElseThrow { throw CoreException(ErrorType.BANNER_NOT_FOUND) }

        if (bannerData.displaySequence != existingBanner.displaySequence &&
            bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerData.id)
        ) {
            throw CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)
        }

        existingBanner.displaySequence = bannerData.displaySequence
        existingBanner.bannerImageUrl = bannerData.bannerImageUrl
        return bannerRepository.save(existingBanner)
    }

    fun createBanner(bannerData: AdminCreateBannerData): Banner {
        if (bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence)) {
            throw CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)
        }

        return bannerRepository.save(bannerData.toEntity())
    }
}