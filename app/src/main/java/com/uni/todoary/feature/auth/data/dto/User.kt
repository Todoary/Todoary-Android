package com.uni.todoary.feature.auth.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    @SerializedName("profileImgUrl") var profileImgUrl : String?,
    @SerializedName("nickname") var nickname : String,
    @SerializedName("introduce") var introduce : String,
    @SerializedName("email") var email : String,
    var password : String?
    ) : Parcelable
