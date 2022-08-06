package com.uni.todoary.feature.auth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.module.AccountInfo
import com.uni.todoary.feature.auth.data.repository.FindPwRepository
import com.uni.todoary.util.saveUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPwViewModel @Inject constructor(private val repository : FindPwRepository): ViewModel() {
    val email : MutableLiveData<String> = MutableLiveData("")
    private var checkList : ArrayList<Boolean> = arrayListOf(false, false, false)

    val _findPwResult = MutableLiveData<ApiResult<Any>>()
    val findPwResult : LiveData<ApiResult<Any>>
        get() = _findPwResult

    // checkList가 다 true 일 때만 유효성 통과, API 함수 호출 가능하도록
    fun codeChecked(idx : Int, status : Boolean){
        this.checkList[idx] = status
    }

    fun validationCheck() : Boolean{
        return checkList[0] && checkList[1] && checkList[2]
    }

    fun registerNewPw(request : AccountInfo){
        _findPwResult.value = ApiResult.loading()
        viewModelScope.launch{
            repository.findPw(request).let {
                if (it.isSuccessful){
                    if (it.body()!!.code == 1000){
                        _findPwResult.value = ApiResult.success(null)
                        // 새로운 유저정보 캐싱
                        val user = repository.getUser()!!
                        user.password = request.newPassword
                        repository.saveUser(user)
                    } else _findPwResult.value = ApiResult.error(it.body()!!.code)
                } else _findPwResult.value = ApiResult.networkError(it.code(), it.message())
            }
        }
    }
}