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
import com.uni.todoary.feature.category.data.dto.CategoryChangeRequest
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.view.CategoryAddView
import com.uni.todoary.feature.category.data.view.CategoryChangeView
import com.uni.todoary.feature.category.data.view.CategoryDeleteView
import com.uni.todoary.feature.category.data.view.GetCategoryView
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.dto.CheckBoxRequest
import com.uni.todoary.feature.main.data.view.AddDiaryView
import com.uni.todoary.feature.main.data.view.CheckBoxView
import com.uni.todoary.feature.main.data.view.GetDiaryView
import com.uni.todoary.util.RetrofitInterface
import com.uni.todoary.util.getXcesToken
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
    private lateinit var GetCategoryView: GetCategoryView
    private lateinit var CategoryChangeView: CategoryChangeView
    private lateinit var CategoryDeleteView: CategoryDeleteView
    private lateinit var AddDiaryView: AddDiaryView
    private lateinit var AgreeTermsView: AgreeTermsView
    private lateinit var GetDiaryView: GetDiaryView

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

    fun setCategoryChangeView(CategoryChangeView:CategoryChangeView){
        this.CategoryChangeView = CategoryChangeView
    }
    fun setGetCategoryView(GetCategoryView: GetCategoryView){
        this.GetCategoryView=GetCategoryView
    }

    fun setAddDiaryView(AddDiaryView: AddDiaryView){
        this.AddDiaryView=AddDiaryView
    }

    fun setCategoryDeleteView(CategoryDeleteView:CategoryDeleteView){
        this.CategoryDeleteView=CategoryDeleteView
    }

    fun setAgreeTermsView(AgreeTermsView:AgreeTermsView){
        this.AgreeTermsView=AgreeTermsView
    }

    fun setGetDiaryView(GetDiaryView: GetDiaryView){
        this.GetDiaryView=GetDiaryView
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

    fun CategoryAdd(request: CategoryChangeRequest) {
        CategoryAddView.CategoryAddLoading()
        authService.CategoryAdd(request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {

                val resp = response.body()!!
                Log.d("resp ", request.toString())
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

    fun CategoryChange(categoryId:Long,request: CategoryChangeRequest) {
        CategoryChangeView.CategoryChangeLoading()
        authService.CategoryChange(categoryId,request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                Log.d("cateId",categoryId.toString())
                Log.d("cateRequ",request.toString())
                Log.d("response1",response.toString())
                Log.d("cate_log", getXcesToken().toString())
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> CategoryChangeView.CategoryChangeSuccess()
                    else -> CategoryChangeView.CategoryChangeFailure(resp.code)
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("CateChange_API_Failure", t.toString())
            }
        })
    }

    fun GetCategory() {
        GetCategoryView.GetCategoryLoading()
        authService.GetCategory().enqueue(object : Callback<BaseResponse<List<CategoryData>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<CategoryData>>>,
                response: Response<BaseResponse<List<CategoryData>>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> resp.result?.let { GetCategoryView.GetCategorySuccess(it) }
                    else -> GetCategoryView.GetCategoryFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<CategoryData>>>, t: Throwable) {
                Log.d("GetCategory_API_Failure", t.toString())
            }
        })
    }

    fun CategoryDelete(categoryId:Long) {
        CategoryDeleteView.CategoryDeleteLoading()
        authService.CategoryDelete(categoryId).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> CategoryDeleteView.CategoryDeleteSuccess()
                    else -> CategoryDeleteView.CategoryDeleteFailure(resp.code)
                }
            }
            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("CateDelete_API_Failure", t.toString())
            }
        })
    }


    //Diary
    fun AddDiary(createdDate:String, request: AddDiaryRequest) {
        AddDiaryView.AddDiaryLoading()
        authService.AddDiary(createdDate, request).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("resp: ", request.toString())
                when (resp.code) {
                    1000 -> AddDiaryView.AddDiarySuccess()
                    else -> AddDiaryView.AddDiaryFailure(resp.code)
                }
                Log.d("resp", resp.toString())
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("AddDiary_API_Failure", t.toString())
            }
        })
    }
    fun AgreeTerms(isChecked : Boolean) {
        AgreeTermsView.AgreeTermsLoading()
        authService.AgreeTerms(isChecked).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                Log.d("resp code: ",resp.code.toString())
                when (resp.code) {
                    1000 -> AgreeTermsView.AgreeTermsSuccess()
                    else -> AgreeTermsView.AgreeTermsFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("AgreeTerms_API_Failure", t.toString())
            }
        })
    }

    fun GetDiary(createdDate: String) {
        GetDiaryView.GetDiaryLoading()
        authService.GetDiary(createdDate).enqueue(object : Callback<BaseResponse<Any>> {
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                val resp = response.body()!!
                when (resp.code) {
                    1000 -> GetDiaryView.GetDiarySuccess()
                    else -> GetDiaryView.GetDiaryFailure(resp.code)
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Log.d("GetDiary_API_Failure", t.toString())
            }
        })
    }
}

