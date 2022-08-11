package com.uni.todoary.feature.category.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CategoryChangeRequest(
    @SerializedName("title") val title: String,
    @SerializedName("color") val color : Int
)
