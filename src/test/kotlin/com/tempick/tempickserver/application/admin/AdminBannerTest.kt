package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminCreateBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.repository.BannerRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class AdminBannerTest : StringSpec({
    val bannerRepository = mockk<BannerRepository>()
    val adminBannerService = AdminBannerCommandService(bannerRepository)

    "배너 생성 성공 테스트" {
        val bannerData = AdminCreateBannerData(
            bannerImageUrl = "https://example.com/banner.jpg",
            displaySequence = 1
        )

        val expectedBanner = Banner(
            id = 1L,
            bannerImageUrl = bannerData.bannerImageUrl,
            displaySequence = bannerData.displaySequence
        )

        every { bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence) } returns false
        every { bannerRepository.save(any()) } returns expectedBanner

        val result = adminBannerService.createBanner(bannerData)

        result shouldBe expectedBanner
    }

    "이미 존재하는 배너 순서로 생성 시 예외 발생 테스트" {
        val bannerData = AdminCreateBannerData(
            bannerImageUrl = "https://example.com/banner.jpg",
            displaySequence = 1
        )

        every { bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence) } returns true

        val exception = shouldThrow<CoreException> {
            adminBannerService.createBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS.message
    }

    "배너 업데이트 성공 테스트" {
        val bannerId = 1L
        val bannerData = AdminCreateBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/updated-banner.jpg",
            displaySequence = 2
        )

        val existingBanner = Optional.of(
            Banner(
                id = bannerId,
                bannerImageUrl = "https://example.com/old-banner.jpg",
                displaySequence = 2
            )
        )

        val updatedBanner = Banner(
            id = bannerId,
            bannerImageUrl = bannerData.bannerImageUrl,
            displaySequence = bannerData.displaySequence
        )

        every { bannerRepository.findById(bannerId) } returns existingBanner
        every { bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerId) } returns false
        every { bannerRepository.save(any()) } returns updatedBanner

        val result = adminBannerService.updateBanner(bannerData)

        result shouldBe updatedBanner
        verify { bannerRepository.findById(bannerId) }
        verify { bannerRepository.save(any()) }
    }

    "존재하지 않는 배너 업데이트 시 예외 발생 테스트" {
        val bannerId = 999L
        val bannerData = AdminCreateBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            displaySequence = 1
        )

        every { bannerRepository.findById(bannerId) } returns Optional.empty()

        val exception = shouldThrow<CoreException> {
            adminBannerService.updateBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
    }

    "이미 존재하는 배너 순서로 업데이트 시 예외 발생 테스트" {
        val bannerId = 1L
        val bannerData = AdminCreateBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            displaySequence = 3
        )

        val existingBanner = Optional.of(
            Banner(
                id = bannerId,
                bannerImageUrl = "https://example.com/old-banner.jpg",
                displaySequence = 2
            )
        )

        every { bannerRepository.findById(bannerId) } returns existingBanner
        every { bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerId) } returns true

        val exception = shouldThrow<CoreException> {
            adminBannerService.updateBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS.message
    }
})
