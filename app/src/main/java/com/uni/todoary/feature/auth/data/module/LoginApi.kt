package com.uni.todoary.feature.auth.data.module

import com.google.gson.annotations.SerializedName
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.config.FcmToken
import com.uni.todoary.feature.auth.data.dto.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password : String,
)

data class LoginResponse(
    @SerializedName("token") val token: LoginToken,
)

data class LoginToken(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)

data class AccountInfo(
    @SerializedName("email") val email: String,
    @SerializedName("newPassword") val newPassword: String
)

data class SocialLoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("providerId") val providerId: String,
    )

data class SocialLoginResponse(
    @SerializedName("isNewUser") val isNewUser: Boolean,
    @SerializedName("token") val token: LoginToken
    )

data class SocialUser(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("providerId") val providerId: String
)
data class SocialSignInRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("providerId") val providerId: String,
    @SerializedName("isTermsEnable") val isTermsEnable: Boolean,
)

interface LoginInterface{
    // --------- Auth ------------- //
    @POST("/auth/signin")
    suspend fun login(@Body loginInfo: LoginRequest): Response<BaseResponse<LoginResponse>>

    @POST("/auth/signin/auto")
    suspend fun autoLogin(@Body loginInfo: LoginRequest) : Response<BaseResponse<LoginResponse>>

    @PATCH("/auth/password")
    suspend fun findPw(@Body accountInfo : AccountInfo) : Response<BaseResponse<Any>>

    @POST("/oauth2/authorization/google")
    suspend fun socialLogin(@Body socialLoginInfo : SocialLoginRequest) : Response<BaseResponse<SocialLoginResponse>>

    @POST("/auth/signup/oauth2")
    suspend fun socialSignIn(@Body request : SocialSignInRequest) : Response<BaseResponse<Any>>

    @PATCH("/users/fcm_token")
    suspend fun patchFcmToken(@Body fcm_token : FcmToken) : Response<BaseResponse<Any>>

    // ------------ Profile ------------- //
    @GET("/users")
    suspend fun getProfile() : Response<BaseResponse<User>>
}