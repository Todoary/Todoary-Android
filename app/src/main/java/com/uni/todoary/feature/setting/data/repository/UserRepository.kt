package com.uni.todoary.feature.setting.data.repository

import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.AuthSpfManager
import com.uni.todoary.feature.setting.data.module.AlarmStatus
import com.uni.todoary.feature.setting.data.module.AlarmUpdateRequest
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

    suspend fun getAlarmStatus() : Response<BaseResponse<AlarmStatus>>{
        return remoteApi.getAlarmStatus()
    }

    suspend fun updateAlarmTodo(status : Boolean) : Response<BaseResponse<Any>>{
        val request = AlarmUpdateRequest(status)
        return remoteApi.updateAlarmTodo(request)
    }

    suspend fun updateAlarmDiary(status : Boolean) : Response<BaseResponse<Any>>{
        val request = AlarmUpdateRequest(status)
        return remoteApi.updateAlarmDiary(request)
    }

    suspend fun updateAlarmRemind(status : Boolean) : Response<BaseResponse<Any>>{
        val request = AlarmUpdateRequest(status)
        return remoteApi.updateAlarmRemind(request)
    }

    // Spf Manager
    fun saveUser(user: User) = spfManager.saveUser(user)
    fun getUser(): User? = spfManager.getUser()
    fun removeUser() = spfManager.removeUser()
    fun removeRefToken() = spfManager.removeRefToken()
    fun removeXcesToken() = spfManager.removeXcesToken()
    fun saveIsAutoLogin(isTrue : Boolean) = spfManager.saveIsAutoLogin(isTrue)
}