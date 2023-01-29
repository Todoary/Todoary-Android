package com.uni.todoary.feature.auth.data.repository

import android.util.Log
import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.config.FcmToken
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val remoteApi : LoginInterface, private val spfManager : AuthSpfManager) {
    suspend fun login(request: LoginRequest): Response<BaseResponse<LoginResponse>> {
        return remoteApi.login(request)
    }

    suspend fun autoLogin(request: LoginRequest): Response<BaseResponse<LoginResponse>> {
        return remoteApi.autoLogin(request)
    }

    suspend fun getProfile(): Response<BaseResponse<User>> {
        return remoteApi.getProfile()
    }

    suspend fun socialLogin(request : SocialLoginRequest) : Response<BaseResponse<SocialLoginResponse>> {
        return remoteApi.socialLogin(request)
    }

    suspend fun socialSignIn(request : SocialSignInRequest) : Response<BaseResponse<Any>>{
        return remoteApi.socialSignIn(request)
    }

    suspend fun patchFcmToken(token : FcmToken) : Response<BaseResponse<Any>> {
        return remoteApi.patchFcmToken(token)
    }

    // -------------- SharedPreferencesManager --------------- //
    fun saveIsAutoLogin(isTrue: Boolean) = spfManager.saveIsAutoLogin(isTrue)
    fun getIsAutoLogin(): Boolean = spfManager.getIsAutoLogin()
    fun saveUser(user: User) = spfManager.saveUser(user)
    fun getUser(): User? = spfManager.getUser()
    fun saveXcesToken(AccessToken: String) = spfManager.saveXcesToken(AccessToken)
    fun getXcesToken(): String? = spfManager.getXcesToken()
    fun removeXcesToken() = spfManager.removeXcesToken()
    fun saveRefToken(refreshToken: String) = spfManager.saveRefToken(refreshToken)
    fun getRefToken(): String? = spfManager.getRefToken()
    fun removeRefToken() = spfManager.removeRefToken()
    fun getFCMToken(): String = spfManager.getFCMToken()
}