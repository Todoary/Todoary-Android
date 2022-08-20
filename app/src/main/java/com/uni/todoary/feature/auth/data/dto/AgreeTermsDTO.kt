package com.uni.todoary.feature.auth.data.dto

import com.google.gson.annotations.SerializedName

data class AgreeTermsRequest(
    @SerializedName("isChecked") val isChecked : Boolean
)

