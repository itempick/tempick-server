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

class AdminBannerTest : StringSpec({
    val bannerRepository = mockk<BannerRepository>()
    val adminBannerUpsertHandler = mockk<AdminBannerUpsertHandler>()
    val adminBannerService = AdminBannerService(bannerRepository, adminBannerUpsertHandler)

    "배너 생성 성공 테스트" {
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

        every { adminBannerUpsertHandler.createBanner(bannerData) } returns expectedBanner

        val result = adminBannerService.upsertBanner(bannerData)

        result shouldBe expectedBanner
        verify { adminBannerUpsertHandler.createBanner(bannerData) }
    }

    "이미 존재하는 배너 순서로 생성 시 예외 발생 테스트" {
        val bannerData = AdminBannerData(
            bannerImageUrl = "https://example.com/banner.jpg",
            displaySequence = 1
        )

        every { adminBannerUpsertHandler.createBanner(bannerData) } throws CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)

        val exception = shouldThrow<CoreException> {
            adminBannerService.upsertBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS.message
        verify { adminBannerUpsertHandler.createBanner(bannerData) }
    }

    "배너 업데이트 성공 테스트" {
        val bannerId = 1L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/updated-banner.jpg",
            clickUrl = "https://example.com/updated-landing",
            displaySequence = 2
        )

        val updatedBanner = Banner(
            id = bannerId,
            bannerImageUrl = bannerData.bannerImageUrl,
            clickUrl = bannerData.clickUrl,
            displaySequence = bannerData.displaySequence
        )

        every { adminBannerUpsertHandler.updateBanner(bannerData) } returns updatedBanner

        val result = adminBannerService.upsertBanner(bannerData)

        result shouldBe updatedBanner
        verify { adminBannerUpsertHandler.updateBanner(bannerData) }
    }

    "존재하지 않는 배너 업데이트 시 예외 발생 테스트" {
        val bannerId = 999L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        every { adminBannerUpsertHandler.updateBanner(bannerData) } throws CoreException(ErrorType.BANNER_NOT_FOUND)

        val exception = shouldThrow<CoreException> {
            adminBannerService.upsertBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
        verify { adminBannerUpsertHandler.updateBanner(bannerData) }
    }

    "이미 존재하는 배너 순서로 업데이트 시 예외 발생 테스트" {
        val bannerId = 1L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 3
        )

        every { adminBannerUpsertHandler.updateBanner(bannerData) } throws CoreException(ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS)

        val exception = shouldThrow<CoreException> {
            adminBannerService.upsertBanner(bannerData)
        }

        exception.message shouldBe ErrorType.BANNER_DISPLAY_SEQUENCE_ALREADY_EXISTS.message
        verify { adminBannerUpsertHandler.updateBanner(bannerData) }
    }
})
