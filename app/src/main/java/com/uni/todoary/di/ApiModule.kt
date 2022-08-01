package com.uni.todoary.di

import android.content.Context
import com.uni.todoary.ApplicationClass
import com.uni.todoary.BuildConfig
import com.uni.todoary.config.TokenInterface
import com.uni.todoary.config.TokenRepository
import com.uni.todoary.config.XAccessTokenInterceptor
import com.uni.todoary.feature.auth.data.module.LoginInterface
import com.uni.todoary.feature.setting.data.module.UserInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    // 레트로핏 선언
    @Provides
    fun provideBaseUrl() = ApplicationClass.BASE_URL

    @Singleton
    @Provides
    fun provideTokenApi(): TokenInterface {
        return TokenInterface.create()
    }

    @Provides
    fun provideTokenRepo(tokenApi : TokenInterface) = TokenRepository(tokenApi)

    @Singleton
    @Provides
    fun provideOkHttpClient(tokenRepo : TokenRepository, @ApplicationContext context : Context) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addInterceptor(XAccessTokenInterceptor(tokenRepo, context)) // JWT 자동 헤더 전송
            .build()
    } else {
        OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addInterceptor(XAccessTokenInterceptor(tokenRepo, context)) // JWT 자동 헤더 전송
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    // Api Interfaces 바인딩
    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginInterface {
        return retrofit.create(LoginInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserInterface {
        return retrofit.create(UserInterface::class.java)
    }

}