package com.uni.todoary.config

import com.google.gson.annotations.SerializedName
import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import java.util.*

data class RefreshToken(
    @SerializedName("refreshToken") val refreshToken : String,
)

data class FcmToken(
    @SerializedName("fcm_token") val fcmToken : String
)

data class Tokens(
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String
)

data class TokensResponse(
    @SerializedName("token") val tokens : Tokens,
)


interface TokenInterface {
    @POST ("/auth/jwt")
    suspend fun refreshXcesToken(
        @Body request : RefreshToken
    ) : Response<BaseResponse<TokensResponse>>

    @PATCH("/users/fcm_token")
    suspend fun patchFcmToken(
        @Body fcm_token : FcmToken
    ) : Response<BaseResponse<Any>>

    companion object {
        private const val BASE_URL: String = ApplicationClass.BASE_URL
        fun create():TokenInterface {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TokenInterface ::class.java)
        }
    }
}