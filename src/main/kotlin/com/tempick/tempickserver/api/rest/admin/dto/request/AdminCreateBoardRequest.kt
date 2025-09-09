package com.tempick.tempickserver.api.rest.admin.dto.request

data class AdminCreateBoardRequest (
    val categoryId: Long,
    val name: String
)