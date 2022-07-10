package com.uni.todoary.util

import android.util.Log
import com.google.gson.Gson
import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.feature.auth.data.dto.User

fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("jwt", jwtToken)
    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString("jwt", null)

fun removeJwt(){
    val editor = mSharedPreferences.edit()
    editor.remove("jwt")
    editor.commit()
}

fun saveUser(user : User){
    val userGson = Gson().toJson(user)
    val editor = mSharedPreferences.edit()
    editor.putString("user", userGson)
    editor.apply()
}

fun getUser() : User{
    val gsonData = mSharedPreferences.getString("user", null)
    return Gson().fromJson(gsonData, User::class.java)
}