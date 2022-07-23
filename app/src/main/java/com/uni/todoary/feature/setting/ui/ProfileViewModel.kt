package com.uni.todoary.feature.setting.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uni.todoary.ApplicationClass.Companion.mSharedPreferences
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.util.getUser
import com.uni.todoary.util.saveUser

class ProfileViewModel : ViewModel() {
    private val _user : MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }
    val user : LiveData<User> get() = _user

    init{
        _user.value = getUser()

    }

    fun updateUser(name : String, intro : String){
        val newUser = getUser()
        newUser!!.nickname = name
        newUser.introduce = intro
        saveUser(newUser)
        this._user.value = newUser
    }
}