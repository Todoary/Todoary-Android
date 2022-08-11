package com.uni.todoary

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.FirebaseApp
import com.uni.todoary.config.TokenInterface
import com.uni.todoary.config.TokenRepository
import com.uni.todoary.config.XAccessTokenInterceptor
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class ApplicationClass : Application() {
    companion object{
        const val X_ACCESS_TOKEN: String = "Authorization"         // Access Token Key
        const val TAG: String = "todoary_preferences"               // Log, SharedPreference
        const val APP_DATABASE = "$TAG-DB"

        const val DEV_URL: String = "https://todoary.com";       // 테스트 서버 주소
        const val PROD_URL: String = "https://api.template.com/"    // 실서버 주소
        const val BASE_URL: String = DEV_URL

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        val tokenApi = TokenInterface.create()
        val tokenRepo = TokenRepository(tokenApi)

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addInterceptor(XAccessTokenInterceptor(tokenRepo, this)) // JWT 자동 헤더 전송
            .build()

//        val client: OkHttpClient = if (BuildConfig.DEBUG) {
//            val loggingInterceptor = HttpLoggingInterceptor()
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//            OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .readTimeout(30000, TimeUnit.MILLISECONDS)
//                .connectTimeout(30000, TimeUnit.MILLISECONDS)
//                .addInterceptor(XAccessTokenInterceptor(tokenRepo, this)) // JWT 자동 헤더 전송
//                .build()
//        } else {
//            OkHttpClient.Builder()
//                .readTimeout(30000, TimeUnit.MILLISECONDS)
//                .connectTimeout(30000, TimeUnit.MILLISECONDS)
//                .addInterceptor(XAccessTokenInterceptor(tokenRepo, this)) // JWT 자동 헤더 전송
//                .build()
//        }

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}