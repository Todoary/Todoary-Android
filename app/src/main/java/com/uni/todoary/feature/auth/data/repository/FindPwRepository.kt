package com.uni.todoary.feature.auth.data.repository

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.AccountInfo
import com.uni.todoary.feature.auth.data.module.AuthSpfManager
import com.uni.todoary.feature.auth.data.module.LoginInterface
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FindPwRepository @Inject constructor(private val remoteApi : LoginInterface, private val spf : AuthSpfManager){
    suspend fun findPw(accountInfo : AccountInfo) : Response<BaseResponse<Any>> {
        return remoteApi.findPw(accountInfo)
    }

    fun getUser() : User? = spf.getUser()
    fun saveUser(user : User) = spf.saveUser(user)
}