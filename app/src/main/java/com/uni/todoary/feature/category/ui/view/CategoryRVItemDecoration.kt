package com.uni.todoary.feature.category.ui.view

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue
import com.uni.todoary.util.dpToPx
import androidx.recyclerview.widget.GridLayoutManager

import androidx.annotation.NonNull

import android.view.View

import android.graphics.Rect

class CategoryRVItemDecoration(context : Context) : RecyclerView.ItemDecoration() {
    private var size10 = dpToPx(context, 10f)
    private var size5 = dpToPx(context,5f)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        //상하 설정
        if (position == 0) {
            // 첫번 째 아이템
            outRect.left = size5
        }
        if (position == itemCount - 1){
            outRect.right = size5
        }
    }
}