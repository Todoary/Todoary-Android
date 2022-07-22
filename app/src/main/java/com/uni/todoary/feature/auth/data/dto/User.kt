package com.uni.todoary.feature.auth.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var name : String,
    var intro : String,
    val email : String,
    val password : String
    ) : Parcelable