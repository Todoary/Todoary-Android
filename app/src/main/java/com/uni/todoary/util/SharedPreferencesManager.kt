package com.uni.todoary.util

import android.util.Log
import com.google.gson.Gson
import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.feature.auth.data.dto.User
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


// ------------------------- Token -------------------------------- //
fun saveXcesToken(AccessToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString("AccessToken", AccessToken)
    editor.apply()
}

fun getXcesToken(): String? = mSharedPreferences.getString("AccessToken", null)

fun removeXcesToken(){
    val editor = mSharedPreferences.edit()
    editor.remove("AccessToken")
    editor.commit()
}

fun saveRefToken(refreshToken : String){
    val editor = mSharedPreferences.edit()
    editor.putString("RefreshToken", refreshToken)
    editor.apply()
}

fun getRefToken(): String? = mSharedPreferences.getString("RefreshToken", null)

fun removeRefToken(){
    val editor = mSharedPreferences.edit()
    editor.remove("RefreshToken")
    editor.commit()
}

// ------------------------ User Info ----------------------------- //

fun saveUser(user : User){
    val userGson = Gson().toJson(user)
    val editor = mSharedPreferences.edit()
    editor.putString("user", userGson)
    editor.apply()
}

fun removeUser(){
    val editor = mSharedPreferences.edit()
    editor.remove("user")
    editor.commit()
}

fun getUser() : User?{
    val gsonData = mSharedPreferences.getString("user", null)
    return if (gsonData == null){
        null
    } else {
        Gson().fromJson(gsonData, User::class.java)
    }
}

fun saveSecureKey(key : ArrayList<Int>?){
    val editor = mSharedPreferences.edit()
    val keyJson = Gson().toJson(key)
    editor.putString("secureKey", keyJson)
    editor.apply()
}

fun getSecureKey() : ArrayList<Int>?{
    val gsonData = mSharedPreferences.getString("secureKey", null)
    return if (gsonData == null){
        null
    } else {
        val type: Type = object : TypeToken<ArrayList<Int>>() {}.type
        Gson().fromJson(gsonData, type)
    }
}

fun saveIsAutoLogin(isTrue : Boolean){
    val editor = mSharedPreferences.edit()
    editor.putBoolean("isAutoLogin", isTrue)
    editor.apply()
}

fun getIsAutoLogin() = mSharedPreferences.getBoolean("isAutoLogin", false)