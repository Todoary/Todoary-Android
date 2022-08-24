package com.uni.todoary.config

import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.uni.todoary.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.uni.todoary.util.*
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import kotlin.system.exitProcess

class XAccessTokenInterceptor @Inject constructor (
    private val tokenRepository: TokenRepository,
    private val context: Context
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getXcesToken()
        var request : Request? = null

        // jwtToken이 없는 경우 : 로그인
        // jwtToken이 있는 경우 : 로그인 이외의 모든 경우
        // 위의 두가지 케이스를 모두 처리하기 위해 jwtToken을 nullable로 선언
        jwtToken?.let{
            request = builder.putTokenHeader(jwtToken)
        } ?: run {
            request = builder.build()
        }

        // 응답을 가져와서 401 에러 (jwt토큰이 유효하지 않은 경우)를 캐치
        var response : Response = chain.proceed(request!!)

        // 401 에러시 토큰 재요청, 갈아끼우기
        if (response.code == 401){
            response.close()        // 기존의 응답을 버리고 새로 요청을 해야 하므로 close()
            // RefreshToken이 있는 경우 (자동로그인)
            if (getRefToken() != null){
                // 새로 AccessToken을 요청하는 API Call 로직 수행
                val refreshRequest = RefreshToken(getRefToken()!!, getFCMToken())
                // Interceptor는 동기, Retrofit은 비동기 실행이므로 runBlocking으로 둘다 동기적으로 실행되도록 함
                runBlocking {
                    tokenRepository.refreshToken(refreshRequest).let {
                        if(it.isSuccessful){
                            if(it.body()!!.code == 1000){
                                // 새로운 토큰을 받아와서 SPF에 저장
                                saveXcesToken(it.body()!!.result!!.tokens.accessToken)
                                saveRefToken(it.body()!!.result!!.tokens.refreshToken)
                                val newToken: String? = getXcesToken()
                                Log.d("토큰",newToken.toString())
                                // 새로운 Request 생성 후 다시 API Call을 하기 위해 newBuilder() 사용
                                val newRequest = chain.request().newBuilder().putTokenHeader(newToken!!)
                                response = chain.proceed(newRequest)
                            }
                        }
                    }
                }
                return response
            } else {
                // RefreshToken이 없는 경우 (일반 로그인)
                val handler = android.os.Handler(Looper.getMainLooper())
                handler.postDelayed(Runnable { Toast.makeText(
                    context,
                    "세션이 만료되었습니다. 다시 로그인 해주세요",
                    Toast.LENGTH_SHORT).show() },
                    0)
                exitProcess(0)      // 안내메시지 토스트로 띄우고 앱 자체 프로세스 Kill
            }
        }
        return response
    }

    private fun Request.Builder.putTokenHeader(token: String): Request {
        return this.addHeader(X_ACCESS_TOKEN, token)
            .build()
    }
}