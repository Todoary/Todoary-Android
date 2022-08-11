package com.uni.todoary.util

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class CategoryData(
    var id : Long,
    var title : String,
    var color : Int
):Serializable
