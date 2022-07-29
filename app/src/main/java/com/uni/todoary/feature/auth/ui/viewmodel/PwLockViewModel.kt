package com.uni.todoary.feature.auth.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PwLockViewModel : ViewModel() {
    // 현재 패스워드 데이터
    val passward : MutableLiveData<List<Int>> by lazy{
        MutableLiveData<List<Int>>()
    }
    val passwardList = ArrayList<Int>()

    // LiveData에 리스트를 넣으면 add, remove 등이 Notify가 안되기 때문에
    // 지역변수에 리스트 선언해두고 바꾸면서 LiveData에 그대로 복사해서 업데이트
    fun addPw(number : Int){
        if(passward.value?.size == 4){
            return
        }
        passwardList.add(number)
        passward.value = passwardList
    }

    fun removePw(){
        if(passwardList.size > 0){
            passwardList.removeAt(passwardList.size-1)
            passward.value = passwardList
        }
    }

    fun clearPw(){
        passwardList.clear()
        passward.value = passwardList
    }
}