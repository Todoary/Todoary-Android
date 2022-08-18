package com.uni.todoary.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import com.uni.todoary.feature.main.ui.view.DiaryActivity
import com.uni.todoary.feature.main.ui.view.MainActivity
import com.uni.todoary.feature.setting.ui.view.AlarmActivity
import java.text.DecimalFormat

fun dpToPx(context: Context, dp: Float) : Int{
    val dm : DisplayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm).toInt()
}

fun alarmFormatter(ampm : String, hour : Int, minute : Int) : String{
    val timePattern = DecimalFormat("00")
    var realhour = hour
    val realMinute = timePattern.format(minute)
    var timeFormat = ""
    if (ampm == "AM" && hour == 12){
        realhour = 0
    }
    timeFormat = "$ampm $realhour:$realMinute"
    return timeFormat
}