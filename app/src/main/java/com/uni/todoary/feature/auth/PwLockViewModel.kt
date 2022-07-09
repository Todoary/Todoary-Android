package com.uni.todoary.feature.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PwLockViewModel : ViewModel() {
    val passward : MutableLiveData<List<Int>> by lazy{
        MutableLiveData<List<Int>>()
    }
    val passwardList = ArrayList<Int>()

    fun addPw(number : Int){
        Log.d("pwpwpwpw", passward.value.toString())
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