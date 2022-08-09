package com.uni.todoary.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun dpToPx(context : Context, dp : Float) : Int{
    val dm : DisplayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm).toInt()
}