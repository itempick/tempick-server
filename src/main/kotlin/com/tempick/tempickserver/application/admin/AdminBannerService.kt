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
            updateBanner(bannerData)
        } else {
            createBanner(bannerData)
        }
    }

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

    private fun createBanner(bannerData: AdminBannerData): Banner {
        if (bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence)) {
            throw CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)
        }

        return bannerRepository.save(bannerData.toEntity())
    }

    @Transactional
    fun deleteBanner(bannerId: Long) {
        val banner = bannerRepository.findActiveBanner(bannerId)
            ?: throw CoreException(ErrorType.BANNER_NOT_FOUND)

        banner.delete()
    }
}
