package com.uni.todoary.feature.auth.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name : String,
    val intro : String,
    val email : String,
    val password : String,
    var secureKey : ArrayList<Int>?
) : Parcelable