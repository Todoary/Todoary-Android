package com.uni.todoary.util

import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences

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