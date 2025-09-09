package com.tempick.tempickserver.application.admin.dto

import com.tempick.tempickserver.domain.enums.Permission

data class AdminBoardData (
    val id: Long = 0L,
    val name: String,
    val categoryId: Long = 0L,
    val permission: Permission? = null,
    val isMainExposed: Boolean? = null,
)