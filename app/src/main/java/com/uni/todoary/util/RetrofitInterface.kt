package com.uni.todoary.util

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.SignInRequest
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    // ---------- Auth ------------ //
    @POST("/auth/signup")
    fun SignIn(@Body signinInfo : SignInRequest) : Call<BaseResponse<Any>>

    @GET("/auth/email/duplication")
    fun EmailCheck(@Query("email") email : String) : Call<BaseResponse<Any>>

    @GET("/auth/email/existence")
    fun ExistenceCheck(@Query("email") email : String) : Call<BaseResponse<Any>>
}