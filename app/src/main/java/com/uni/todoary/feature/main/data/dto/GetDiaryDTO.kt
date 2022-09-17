package com.uni.todoary.feature.main.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetDiaryRequest(
    @SerializedName("diaryId") val diaryId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val created_at: String
)
