package com.uni.todoary.feature.main.data.repository

import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.AuthSpfManager
import com.uni.todoary.feature.main.data.module.MainApiInterface
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteApi : MainApiInterface, private val spf : AuthSpfManager){

    // --------------- SPF Manager ---------------- //
    fun getUser() : User = spf.getUser()!!
}