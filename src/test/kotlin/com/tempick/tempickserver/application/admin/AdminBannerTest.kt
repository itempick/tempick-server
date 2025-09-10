package com.tempick.tempickserver.application.admin

import com.tempick.tempickserver.api.support.error.CoreException
import com.tempick.tempickserver.api.support.error.ErrorType
import com.tempick.tempickserver.application.admin.dto.AdminBannerData
import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.repository.BannerRepository
import com.tempick.tempickserver.util.TestMockData
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

// Helper function to create and configure a Banner mock
private fun createBannerMock(
    id: Long,
    bannerImageUrl: String,
    clickUrl: String,
    displaySequence: Int,
    isDeleted: Boolean = false
): Banner {
    return mockk<Banner>().apply {
        every { this@apply.id } returns id
        every { this@apply.bannerImageUrl } returns bannerImageUrl
        every { this@apply.clickUrl } returns clickUrl
        every { this@apply.displaySequence } returns displaySequence
        every { this@apply.checkDeleted() } returns isDeleted
    }
}

class AdminBannerTest : StringSpec({
    val bannerRepository = mockk<BannerRepository>()
    val adminBannerUpsertHandler = mockk<AdminBannerUpsertHandler>()
    val adminBannerService = AdminBannerService(bannerRepository, adminBannerUpsertHandler)

    "모든 배너를 성공적으로 조회할 수 있다" {
        // Given
        val banner1 = TestMockData.createBannerMock(
            id = 1L,
            bannerImageUrl = "url1",
            clickUrl = "click1",
            displaySequence = 2,
            isDeleted = false
        )

        val banner2 = TestMockData.createBannerMock(
            id = 2L,
            bannerImageUrl = "url2",
            clickUrl = "click2",
            displaySequence = 1,
            isDeleted = false
        )

        val banners = listOf(banner1, banner2)

        every { bannerRepository.findAll() } returns banners

        // When
        val result = adminBannerService.getAllBanners()

        // Then - should be sorted by displaySequence
        result shouldContainExactly listOf(banner2, banner1)
        verify { bannerRepository.findAll() }
        verify { banner1.checkDeleted() }
        verify { banner2.checkDeleted() }
    }

    "삭제된 배너는 조회 결과에서 제외된다" {
        // Given
        val banner1 = TestMockData.createBannerMock(
            id = 1L,
            bannerImageUrl = "url1",
            clickUrl = "click1",
            displaySequence = 2,
            isDeleted = false
        )

        val banner2 = TestMockData.createBannerMock(
            id = 2L,
            bannerImageUrl = "url2",
            clickUrl = "click2",
            displaySequence = 1,
            isDeleted = true
        )

        val banners = listOf(banner1, banner2)

        every { bannerRepository.findAll() } returns banners

        // When
        val result = adminBannerService.getAllBanners()

        // Then - only non-deleted banners
        result shouldContainExactly listOf(banner1)
        verify { bannerRepository.findAll() }
        verify { banner1.checkDeleted() }
        verify { banner2.checkDeleted() }
    }

    "배너가 하나도 없으면 예외가 발생한다" {
        // Given
        every { bannerRepository.findAll() } returns emptyList()

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerService.getAllBanners()
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
        verify { bannerRepository.findAll() }
    }

    "ID로 배너를 성공적으로 조회할 수 있다" {
        // Given
        val bannerId = 1L
        val banner = TestMockData.createBanner(
            id = bannerId,
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        every { bannerRepository.findActiveBanner(bannerId) } returns banner

        // When
        val result = adminBannerService.getBannerById(bannerId)

        // Then
        result shouldBe banner
        verify { bannerRepository.findActiveBanner(bannerId) }
    }

    "존재하지 않는 ID로 배너를 조회하면 예외가 발생한다" {
        // Given
        val bannerId = 999L

        every { bannerRepository.findActiveBanner(bannerId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerService.getBannerById(bannerId)
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
        verify { bannerRepository.findActiveBanner(bannerId) }
    }

    "배너를 성공적으로 삭제할 수 있다" {
        // Given
        val bannerId = 1L
        val banner = TestMockData.createBannerMock(
            id = bannerId,
            bannerImageUrl = "test-url",
            clickUrl = "test-click",
            displaySequence = 1
        )

        every { bannerRepository.findActiveBanner(bannerId) } returns banner
        every { banner.delete() } returns Unit

        // When
        adminBannerService.deleteBanner(bannerId)

        // Then
        verify { bannerRepository.findActiveBanner(bannerId) }
        verify { banner.delete() }
    }

    "존재하지 않는 배너를 삭제하려고 하면 예외가 발생한다" {
        // Given
        val bannerId = 999L

        every { bannerRepository.findActiveBanner(bannerId) } returns null

        // When & Then
        val exception = shouldThrow<CoreException> {
            adminBannerService.deleteBanner(bannerId)
        }

        exception.message shouldBe ErrorType.BANNER_NOT_FOUND.message
        verify { bannerRepository.findActiveBanner(bannerId) }
    }

    "배너를 성공적으로 생성할 수 있다" {
        val bannerData = AdminBannerData(
            bannerImageUrl = "https://example.com/banner.jpg",
            clickUrl = "https://example.com/landing",
            displaySequence = 1
        )

        val expectedBanner = TestMockData.createBanner(
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

    "이미 존재하는 배너 순서로 배너를 생성하면 예외가 발생한다" {
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

    "배너 정보를 성공적으로 업데이트할 수 있다" {
        val bannerId = 1L
        val bannerData = AdminBannerData(
            id = bannerId,
            bannerImageUrl = "https://example.com/updated-banner.jpg",
            clickUrl = "https://example.com/updated-landing",
            displaySequence = 2
        )

        val updatedBanner = TestMockData.createBanner(
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

    "존재하지 않는 배너를 업데이트하려고 하면 예외가 발생한다" {
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

    "이미 존재하는 배너 순서로 배너를 업데이트하면 예외가 발생한다" {
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
