package com.uni.todoary.util

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.*

interface RetrofitInterface {
    // --------- Auth ------------- //
    @POST("/auth/signin")
    fun login(@Body loginInfo: LoginRequest): Call<BaseResponse<LoginResponse>>

    @POST("/auth/signin/auto")
    fun autoLogin(@Body loginInfo: LoginRequest) : Call<BaseResponse<LoginResponse>>

    @POST("/auth/signup")
    fun SignIn(@Body signinInfo : SignInRequest) : Call<BaseResponse<Any>>

    @GET("/auth/email/duplication")
    fun EmailCheck(@Query("email") email : String) : Call<BaseResponse<Any>>

    @GET("/auth/email/existence")
    fun ExistenceCheck(@Query("email") email : String) : Call<BaseResponse<Any>>


    // ------------ Profile ------------- //
    @GET("/users")
    fun GetProfile() : Call<BaseResponse<User>>

    @PATCH("/users/profile")
    fun ProfileChange(@Body ChangeProfileInfo : ProfileChangeRequest) : Call<BaseResponse<Any>>

    @PATCH("/users/status")
    fun DeleteMember() : Call<BaseResponse<User>>

}