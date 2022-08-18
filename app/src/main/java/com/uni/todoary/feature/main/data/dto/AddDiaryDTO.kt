package com.uni.todoary.feature.main.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddDiaryRequest(
    @SerializedName("createdDate") val createdDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)
