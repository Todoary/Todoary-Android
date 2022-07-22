package com.uni.todoary.feature.auth.data.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password : String
)

data class LoginResponse(
    @SerializedName("token") val token: LoginToken,
)

data class LoginToken(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)