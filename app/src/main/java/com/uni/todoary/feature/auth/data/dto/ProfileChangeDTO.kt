package com.uni.todoary.feature.auth.data.dto

import com.google.gson.annotations.SerializedName

data class ProfileChangeRequest(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("introduce") val introduce : String
)
