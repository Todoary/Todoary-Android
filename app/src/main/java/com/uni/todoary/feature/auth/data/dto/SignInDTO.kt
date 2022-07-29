package com.uni.todoary.feature.auth.data.dto

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("password") val password: String,
    @SerializedName("isTermsEnable") val isTermsEnable: Boolean
)

