package com.tempick.tempickserver.api.support.response

import com.tempick.tempickserver.api.support.error.ErrorMessage
import com.tempick.tempickserver.api.support.error.ErrorType

data class RestResponse<T> private constructor(
    val result: ResultType,
    val data: T? = null,
    val error: ErrorMessage? = null,
) {
    companion object {
        fun success(): RestResponse<Any> {
            return RestResponse(ResultType.SUCCESS, null, null)
        }

        fun <S> success(data: S): RestResponse<S> {
            return RestResponse(ResultType.SUCCESS, data, null)
        }

        fun <S> error(error: ErrorType, errorData: Any? = null): RestResponse<S> {
            return RestResponse(ResultType.ERROR, null, ErrorMessage(error, errorData))
        }

        fun <S> exception(error: ErrorType, errorData: Any? = null): RestResponse<S> {
            return RestResponse(ResultType.EXCEPTION, null, ErrorMessage(error, errorData))
        }
    }
}
