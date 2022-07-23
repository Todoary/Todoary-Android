package com.uni.todoary.util

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.LoginRequest
import com.uni.todoary.feature.auth.data.dto.LoginResponse
import com.uni.todoary.feature.auth.data.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitInterface {
    // --------- Auth ------------- //
    @POST("/auth/signin")
    fun login(@Body loginInfo: LoginRequest): Call<BaseResponse<LoginResponse>>

    // ------------ Profile ------------- //
    @GET("/users")
    fun getProfile() : Call<BaseResponse<User>>
}