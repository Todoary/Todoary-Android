package com.uni.todoary.feature.main.data.dto

data class TodoListInfo(
    val isChecked : Boolean,
    val isPined : Boolean,
    val content : String,
    val category : String,
    val alarm : TodoListAlarm
) {
}

data class TodoListAlarm(
    val enable : Boolean,
    val hour : Int = 6,
    val minute  : Int = 0
)