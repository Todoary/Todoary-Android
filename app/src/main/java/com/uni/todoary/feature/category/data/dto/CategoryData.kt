package com.uni.todoary.feature.category.data.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryData(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("color") val color : Int
): Serializable
