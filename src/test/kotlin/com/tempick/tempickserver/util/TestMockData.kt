package com.tempick.tempickserver.util

import com.tempick.tempickserver.domain.entitiy.Banner
import com.tempick.tempickserver.domain.entitiy.Board
import com.tempick.tempickserver.domain.entitiy.Category
import com.tempick.tempickserver.domain.enums.Permission
import io.mockk.every
import io.mockk.mockk


object TestMockData {

    fun createCategory(
        id: Long = 1L,
        name: String = "Test Category",
        sequence: Int = 1
    ): Category {
        return Category(
            id = id,
            name = name,
            sequence = sequence
        )
    }

    fun createBoard(
        id: Long = 1L,
        name: String = "Test Board",
        category: Category = createCategory(),
        permission: Permission = Permission.ALL,
        isMainExposed: Boolean = false
    ): Board {
        return Board(
            id = id,
            name = name,
            category = category,
            permission = permission,
            isMainExposed = isMainExposed
        )
    }

    fun createBanner(
        id: Long = 1L,
        bannerImageUrl: String = "https://example.com/banner.jpg",
        clickUrl: String? = "https://example.com/landing",
        displaySequence: Int = 1
    ): Banner {
        return Banner(
            id = id,
            bannerImageUrl = bannerImageUrl,
            clickUrl = clickUrl,
            displaySequence = displaySequence
        )
    }

    fun createBannerMock(
        id: Long = 1L,
        bannerImageUrl: String = "https://example.com/banner.jpg",
        clickUrl: String? = "https://example.com/landing",
        displaySequence: Int = 1,
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
}
