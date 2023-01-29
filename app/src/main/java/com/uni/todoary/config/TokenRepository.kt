package com.uni.todoary.config

import com.uni.todoary.base.BaseResponse
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(private val tokenApi : TokenInterface) {
    suspend fun refreshToken(token : RefreshToken) : Response<BaseResponse<TokensResponse>> {
        return tokenApi.refreshXcesToken(token)
    }

    suspend fun patchFcmToken(token : FcmToken) : Response<BaseResponse<Any>> {
        return tokenApi.patchFcmToken(token)
    }
}