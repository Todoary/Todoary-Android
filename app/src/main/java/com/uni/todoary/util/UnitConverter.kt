package com.uni.todoary.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import com.uni.todoary.feature.main.ui.view.DiaryActivity
import com.uni.todoary.feature.main.ui.view.MainActivity
import com.uni.todoary.feature.setting.ui.view.AlarmActivity
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun dpToPx(context: Context, dp: Float) : Int{
    val dm : DisplayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm).toInt()
}

fun alarmFormatter(timeString : String) : String{
    var hour = timeString.slice(IntRange(0,1)).toInt()
    val minute = timeString.slice(IntRange(2,4))
    var ampm = "AM"
    if(hour >= 12){
        ampm = "PM"
        if(hour >= 13){
            hour -= 12
        }
    }
    return "$ampm $hour$minute"
}

fun yearMonthFormatter(date : LocalDate) : String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
    return date.format(formatter)
}