package com.uni.todoary.feature.main.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetDiaryRequest(
    @SerializedName("createdDate") val createdDate: String
)
