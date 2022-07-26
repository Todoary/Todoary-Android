package com.uni.todoary.feature.main.data.dto

data class TodoListInfo(
    val isChecked : Boolean,
    val isPined : Boolean,
    val content : String,
    val category : ArrayList<String>,
    val alarm : TodoListAlarm
) {
}

data class TodoListAlarm(
    val enable : Boolean,
    val hour : Int,
    val minute  : Int
)