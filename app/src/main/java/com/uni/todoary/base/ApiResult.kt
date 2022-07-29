package com.uni.todoary.base

import java.net.URLDecoder

data class ApiResult<out T>(val status: Status, val code: Int?, val message: String?, val data: T?, val exception: Throwable?) {

    enum class Status {
        SUCCESS,
        API_ERROR,
        NETWORK_ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): ApiResult<T> {
            return ApiResult(Status.SUCCESS, null, "", data, null)
        }

        fun <T> error(code: Int): ApiResult<T> {
            return ApiResult(Status.API_ERROR, code, null, null, null)
        }

//        fun <T> error(code : Int, exception: String?): ApiResult<T> {
//            return ApiResult(Status.NETWORK_ERROR, code, URLDecoder.decode(exception.toString(), "UTF-8"), null, null)
//        }

        fun <T> networkError(code: Int, message: String): ApiResult<T> {
            return ApiResult(Status.NETWORK_ERROR, code, message, null, null)
        }

        fun <T> loading(): ApiResult<T> {
            return ApiResult(Status.LOADING, null, null, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, code=$code, message=$message, data=$data, error=$exception)"
    }
}