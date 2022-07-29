package com.uni.todoary.feature.auth.data.module

import android.content.SharedPreferences
import com.google.gson.Gson
import com.uni.todoary.feature.auth.data.dto.User
import javax.inject.Inject

class AuthSpfManager @Inject constructor(private val spf : SharedPreferences){

    fun saveXcesToken(AccessToken: String) {
        val editor = spf.edit()
        editor.putString("AccessToken", AccessToken)
        editor.apply()
    }

    fun getXcesToken(): String? = spf.getString("AccessToken", null)

    fun removeXcesToken(){
        val editor = spf.edit()
        editor.remove("AccessToken")
        editor.apply()
    }

    fun saveRefToken(refreshToken : String){
        val editor = spf.edit()
        editor.putString("RefreshToken", refreshToken)
        editor.apply()
    }

    fun getRefToken(): String? = spf.getString("RefreshToken", null)

    fun removeRefToken(){
        val editor = spf.edit()
        editor.remove("RefreshToken")
        editor.apply()
    }

    fun saveUser(user : User){
        val userGson = Gson().toJson(user)
        val editor = spf.edit()
        editor.putString("user", userGson)
        editor.apply()
    }

    fun getUser() : User?{
        val gsonData = spf.getString("user", null)
        return if (gsonData == null){
            null
        } else {
            Gson().fromJson(gsonData, User::class.java)
        }
    }

    fun saveIsAutoLogin(isTrue : Boolean){
        val editor = spf.edit()
        editor.putBoolean("isAutoLogin", isTrue)
        editor.apply()
    }

    fun getIsAutoLogin() = spf.getBoolean("isAutoLogin", false)
}