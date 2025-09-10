package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.repository.BannerRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class AdminBannerUpsertHandlerTest : StringSpec({
    val bannerRepository = mockk<BannerRepository>()
    val adminBannerUpsertHandler = AdminBannerUpsertHandler(bannerRepository)

    "배너 생성이 성공적으로 이루어진다" {
        // Given
        val bannerData = AdminBannerData(
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        val expectedBanner = Banner(
            id = 1L,
            bannerImageUrl = bannerData.bannerImageUrl,
            clickUrl = bannerData.clickUrl,
            displaySequence = bannerData.displaySequence
        )

        every { bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence) } returns false
        every { bannerRepository.save(any()) } returns expectedBanner

        // When
        val result = adminBannerUpsertHandler.createBanner(bannerData)

        // Then
        result shouldBe expectedBanner
        verify { bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence) }
        verify { bannerRepository.save(any()) }
    }

    "이미 존재하는 배너 순서로 배너를 생성하면 예외가 발생한다" {
        // Given
        val bannerData = AdminBannerData(
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        every { bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence) } returns true

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerUpsertHandler.createBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS.message
        verify { bannerRepository.existsBannerByDisplaySequence(bannerData.displaySequence) }
    }

    "배너 정보 업데이트가 성공적으로 이루어진다" {
        // Given
        val bannerId = 1L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/updated-banner.jpg",
            clickUrl = "https://example.com/updated-landing",
            displaySequence = 2
        )

        val existingBanner = Banner(
            id = bannerId,
            bannerImageUrl = "https://example.com/old-banner.jpg",
            clickUrl = "https://example.com/old-landing",
            displaySequence = 1
        )

        val updatedBanner = Banner(
            id = bannerId,
            bannerImageUrl = bannerData.bannerImageUrl,
            clickUrl = bannerData.clickUrl,
            displaySequence = bannerData.displaySequence
        )

        every { bannerRepository.findActiveBanner(bannerId) } returns existingBanner
        every { bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerId) } returns false
        every { bannerRepository.save(any()) } returns updatedBanner

        // When
        val result = adminBannerUpsertHandler.updateBanner(bannerData)

        // Then
        result shouldBe updatedBanner
        verify { bannerRepository.findActiveBanner(bannerId) }
        verify { bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerId) }
        verify { bannerRepository.save(any()) }
    }

    "배너 ID가 null인 경우 업데이트 시 예외가 발생한다" {
        // Given
        val bannerData = AdminBannerData(
            id = null,
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerUpsertHandler.updateBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
    }

    "존재하지 않는 배너를 업데이트하려고 하면 예외가 발생한다" {
        // Given
        val bannerId = 999L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        every { bannerRepository.findActiveBanner(bannerId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerUpsertHandler.updateBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
        verify { bannerRepository.findActiveBanner(bannerId) }
    }

    "이미 존재하는 배너 순서로 배너를 업데이트하면 예외가 발생한다" {
        // Given
        val bannerId = 1L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 3
        )

        val existingBanner = Banner(
            id = bannerId,
            bannerImageUrl = "https://example.com/old-banner.jpg",
            clickUrl = "https://example.com/old-landing",
            displaySequence = 1
        )

        every { bannerRepository.findActiveBanner(bannerId) } returns existingBanner
        every { bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerId) } returns true

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerUpsertHandler.updateBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS.message
        verify { bannerRepository.findActiveBanner(bannerId) }
        verify { bannerRepository.existsBannerByDisplaySequenceAndIdNot(bannerData.displaySequence, bannerId) }
    }
})
