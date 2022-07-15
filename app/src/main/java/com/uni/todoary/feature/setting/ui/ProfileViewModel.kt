package com.uni.todoary.feature.setting.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uni.todoary.feature.auth.data.dto.User

class ProfileViewModel : ViewModel() {
    val user : MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }
}