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
        // data : 성공적으로 받아온 result 값
        fun <T> success(data: T?): ApiResult<T> {
            return ApiResult(Status.SUCCESS, null, "", data, null)
        }

        // code : API Error case에 해당하는 code값 (ex. 2011, 2014, 4001 ...)
        fun <T> error(code: Int): ApiResult<T> {
            return ApiResult(Status.API_ERROR, code, null, null, null)
        }

//        fun <T> error(code : Int, exception: String?): ApiResult<T> {
//            return ApiResult(Status.NETWORK_ERROR, code, URLDecoder.decode(exception.toString(), "UTF-8"), null, null)
//        }

        // code : http 통신에서 발생한 에러코드 (ex. 401, 503 ...)
        // message : 에러코드를 포함하는 전체 http 응답 메시지
        fun <T> networkError(code: Int, message: String): ApiResult<T> {
            return ApiResult(Status.NETWORK_ERROR, code, message, null, null)
        }

        // 아무런 데이터 없으므로 적절한 로딩화면 구현
        fun <T> loading(): ApiResult<T> {
            return ApiResult(Status.LOADING, null, null, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, code=$code, message=$message, data=$data, error=$exception)"
    }
}