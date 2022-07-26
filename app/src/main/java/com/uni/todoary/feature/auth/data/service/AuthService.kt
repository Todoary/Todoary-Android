package com.uni.todoary.feature.auth.data.service

import android.util.Log
import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.SignInRequest
import com.uni.todoary.feature.auth.data.view.EmailCheckView
import com.uni.todoary.feature.auth.data.view.ExistenceCheckView
import com.uni.todoary.feature.auth.data.view.SignInView
import com.uni.todoary.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private val authService= ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    private lateinit var SignInView: SignInView
    private lateinit var EmailCheckView: EmailCheckView
    private lateinit var ExistenceCheckView: ExistenceCheckView

    fun setSignInView(SignInView: SignInView){
        this.SignInView=SignInView
    }

    fun setEmailCheckView(EmailCheckView: EmailCheckView){
        this.EmailCheckView=EmailCheckView
    }

    fun setExistenceCheckView(ExistenceCheckView:ExistenceCheckView){
        this.ExistenceCheckView=ExistenceCheckView
    }

    fun SignIn(request : SignInRequest){
        SignInView.SignInLoading()
        authService.SignIn(request).enqueue(object: Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {

                val resp = response.body()!!
                Log.d("resp: ",request.toString())
                when(resp.code){
                    1000 -> SignInView.SignInSuccess()
                    else -> SignInView.SignInFailure(resp.code)
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("SignIn_API_Failure",t.toString())
            }
        })
    }
    fun EmailCheck(email: String){
        EmailCheckView.EmailCheckLoading()
        authService.EmailCheck(email).enqueue(object: Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("email", email)
                when(resp.code){
                    1000 -> EmailCheckView.EmailCheckSuccess()
                    else -> EmailCheckView.EmailCheckFailure(resp.code)
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("EmailCheck_API_Failure",t.toString())
            }
        })
    }


    fun ExistenceCheck(email: String){
        ExistenceCheckView.ExistenceCheckLoading()
        authService.ExistenceCheck(email).enqueue(object: Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("email_existence: ", email)
                when(resp.code){
                    1000 -> ExistenceCheckView.ExistenceCheckSuccess()
                    else -> ExistenceCheckView.ExistenceCheckFailure(resp.code)
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("Existence_API_Failure",t.toString())
            }
        })
    }
}