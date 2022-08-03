package com.uni.todoary.feature.setting.data.repository

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.AuthSpfManager
import com.uni.todoary.feature.setting.data.module.ProfileChangeRequest
import com.uni.todoary.feature.setting.data.module.UserInterface
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteApi : UserInterface,
    private val spfManager: AuthSpfManager
){
    suspend fun changeProfile(request : ProfileChangeRequest) : Response<BaseResponse<ProfileChangeRequest>>{
        return remoteApi.changeProfile(request)
    }

    suspend fun deleteUser() : Response<BaseResponse<Any>> {
        return remoteApi.deleteUser()
    }

    suspend fun logOut() : Response<BaseResponse<Any>>{
        return remoteApi.logOut()
    }

    // Spf Manager
    fun saveUser(user: User) = spfManager.saveUser(user)
    fun getUser(): User? = spfManager.getUser()
    fun removeUser() = spfManager.removeUser()
    fun removeRefToken() = spfManager.removeRefToken()
    fun removeXcesToken() = spfManager.removeXcesToken()
    fun saveIsAutoLogin(isTrue : Boolean) = spfManager.saveIsAutoLogin(isTrue)
}