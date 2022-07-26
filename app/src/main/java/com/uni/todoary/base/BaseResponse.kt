package com.uni.todoary.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: T?

)
