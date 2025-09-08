package com.tempick.tempickserver.application.admin.dto

import com.tempick.tempickserver.domain.entitiy.Category

data class AdminCategoryData (
    val id: Long,
    val name: String,
    var sequence: Int
) {
    fun toEntity(): Category {
        return Category(
            id = id,
            name = name,
            sequence = sequence
        )
    }
}