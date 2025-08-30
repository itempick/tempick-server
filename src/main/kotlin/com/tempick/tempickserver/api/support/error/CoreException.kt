package com.tempick.tempickserver.api.support.error

class CoreException(
    val errorType: ErrorType,
    val data: Any? = null,
) : RuntimeException(errorType.message)
