package com.tempick.tempickserver.api.rest.admin.dto.request

import com.tempick.tempickserver.domain.enums.Permission

data class AdminUpdateBoardRequest (
    val id: Long,
    val name: String,
    val permission: Permission = Permission.ALL,
    val isMainExposed: Boolean = false
)