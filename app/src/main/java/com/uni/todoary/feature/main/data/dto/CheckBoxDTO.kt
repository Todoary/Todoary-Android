package com.uni.todoary.feature.main.data.dto

import com.google.gson.annotations.SerializedName

data class CheckBoxRequest(
    @SerializedName("todoId") val todoId: Long,
    @SerializedName("isChecked") val isChecked : Boolean
)
