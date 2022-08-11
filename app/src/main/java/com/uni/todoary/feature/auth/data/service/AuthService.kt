package com.uni.todoary.feature.auth.data.service

import android.util.Log
import com.uni.todoary.ApplicationClass
import com.uni.todoary.base.BaseResponse
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.data.module.LoginRequest
import com.uni.todoary.feature.auth.data.module.LoginResponse
import com.uni.todoary.feature.auth.data.view.GetProfileView
import com.uni.todoary.feature.auth.data.view.LoginView
import com.uni.todoary.feature.auth.data.dto.*
import com.uni.todoary.feature.auth.data.view.*
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.view.CategoryAddView
import com.uni.todoary.feature.main.data.dto.CheckBoxRequest
import com.uni.todoary.feature.main.data.view.CheckBoxView
import com.uni.todoary.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private val authService = ApplicationClass.retrofit.create(RetrofitInterface::class.java)

    private lateinit var SignInView: SignInView
    private lateinit var EmailCheckView: EmailCheckView
    private lateinit var ExistenceCheckView: ExistenceCheckView
    private lateinit var ProfileChangeView: ProfileChangeView
    private lateinit var DeleteMemberView: DeleteMemberView
    private lateinit var CheckBoxView: CheckBoxView
    private lateinit var CategoryAddView:CategoryAddView

    fun setSignInView(SignInView: SignInView) {
        this.SignInView = SignInView
    }

    fun setEmailCheckView(EmailCheckView: EmailCheckView) {
        this.EmailCheckView = EmailCheckView
    }

    fun setExistenceCheckView(ExistenceCheckView: ExistenceCheckView) {
        this.ExistenceCheckView = ExistenceCheckView
    }

    fun setProfileChangeView(ProfileChangeView: ProfileChangeView){
        this.ProfileChangeView = ProfileChangeView
    }

    fun setDeleteMemberView(DeleteMemberView: DeleteMemberView){
        this.DeleteMemberView = DeleteMemberView
    }

    fun setCheckBoxView(CheckBoxView: CheckBoxView){
        this.CheckBoxView = CheckBoxView
    }

    fun setCategoryAddView(CategoryAddView: CategoryAddView){
        this.CategoryAddView = CategoryAddView
    }
    fun SignIn(request: SignInRequest) {
        SignInView.SignInLoading()
        authService.SignIn(request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {

                val resp = response.body()!!
                Log.d("resp: ", request.toString())
                when (resp.code) {
                    1000 -> SignInView.SignInSuccess()
                    else -> SignInView.SignInFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("SignIn_API_Failure", t.toString())
            }
        })
    }

    fun EmailCheck(email: String) {
        EmailCheckView.EmailCheckLoading()
        authService.EmailCheck(email).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("email", email)
                when (resp.code) {
                    1000 -> EmailCheckView.EmailCheckSuccess()
                    else -> EmailCheckView.EmailCheckFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("EmailCheck_API_Failure", t.toString())
            }
        })
    }

    fun ProfileChange(request: ProfileChangeRequest) {
        ProfileChangeView.ProfileChangeLoading()
        authService.ProfileChange(request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("resp: ", request.toString())
                Log.d("resp code: ",resp.code.toString())
                when (resp.code) {
                    1000 -> ProfileChangeView.ProfileChangeSuccess()
                    else -> ProfileChangeView.ProfileChangeFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("ProChange_API_Failure", t.toString())
            }
        })
    }

    private lateinit var loginView: LoginView
    private lateinit var profileView: GetProfileView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setProfileView(profileView: GetProfileView) {
        this.profileView = profileView
    }

    fun logIn(request: LoginRequest) {
        loginView.loginLoading()
        authService.login(request).enqueue(object : Callback<BaseResponse<LoginResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<LoginResponse>>,
                response: Response<BaseResponse<LoginResponse>>
            ) {
                Log.d("asdf", response.toString())
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> loginView.loginSuccess(resp.result!!)
                    else -> loginView.loginFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {
                Log.d("login_API_Failure", t.toString())
            }
        })
    }

    fun autoLogin(loginInfo: LoginRequest) {
        loginView.loginLoading()
        authService.autoLogin(loginInfo).enqueue(object : Callback<BaseResponse<LoginResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<LoginResponse>>,
                response: Response<BaseResponse<LoginResponse>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> loginView.loginSuccess(resp.result!!)
                    else -> loginView.loginFailure(resp.code)
                }
            }
            override fun onFailure(call: Call<BaseResponse<LoginResponse>>, t: Throwable) {
                Log.d("autoLogin_API_Failure", t.toString())
            }

        })
    }

    fun GetProfile() {
        profileView.getProfileLoading()
        authService.GetProfile().enqueue(object : Callback<BaseResponse<User>> {
            override fun onResponse(
                call: Call<BaseResponse<User>>,
                response: Response<BaseResponse<User>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> profileView.getProfileSuccess(resp.result!!)
                    else -> profileView.getProfileFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                Log.d("GetProfile_API_Failure", t.toString())
            }
        })
    }

    fun ExistenceCheck(email: String) {
        ExistenceCheckView.ExistenceCheckLoading()
        authService.ExistenceCheck(email).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("email_existence: ", email)
                when (resp.code) {
                    1000 -> ExistenceCheckView.ExistenceCheckSuccess()
                    else -> ExistenceCheckView.ExistenceCheckFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("Existence_API_Failure", t.toString())
            }
        })
    }


    fun DeleteMember() {
        DeleteMemberView.DeleteMemberLoading()
        authService.DeleteMember().enqueue(object : Callback<BaseResponse<User>> {
            override fun onResponse(
                call: Call<BaseResponse<User>>,
                response: Response<BaseResponse<User>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> DeleteMemberView.DeleteMemberSuccess()
                    else -> DeleteMemberView.DeleteMemberFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                Log.d("Delete_API_Failure", t.toString())
            }
        })
    }

    fun CheckBox(request: CheckBoxRequest) {
        CheckBoxView.CheckBoxLoading()
        authService.CheckBox(request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {

                val resp = response.body()!!
                Log.d("resp: ", request.toString())
                when (resp.code) {
                    1000 -> CheckBoxView.CheckBoxSuccess()
                    else -> CheckBoxView.CheckBoxFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("CheckBox_API_Failure", t.toString())
            }
        })
    }

    fun CategoryAdd(request: CategoryData) {
        CategoryAddView.CategoryAddLoading()
        authService.CategoryAdd(request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {

                val resp = response.body()!!
                Log.d("resp: ", request.toString())
                when (resp.code) {
                    1000 -> CategoryAddView.CategoryAddSuccess()
                    else -> CategoryAddView.CategoryAddFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("CategoryAdd_API_Failure", t.toString())
            }
        })
    }

}

