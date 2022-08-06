package com.uni.todoary.feature.main.ui

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.core.view.marginStart
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R

class TodoListSwipeHelper : ItemTouchHelper.Callback() {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var rightClamp = 0f
    private var leftClamp = 0f

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        previousPosition = viewHolder.adapterPosition
        getDefaultUIUtil().clearView(getView(viewHolder))
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x =  clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

            // 고정시킬 시 애니메이션 추가 (고정될 때 좀 더 부드러운 움직임을 하게 해줌)
            if (x == -rightClamp) {
                getView(viewHolder).animate().translationX(-rightClamp).setDuration(100L).start()
                return
            } else if (x == leftClamp){
                getView(viewHolder).animate().translationX(leftClamp).setDuration(100L).start()
                return
            }

            currentDx = x
            getDefaultUIUtil().onDraw(
                c,
                recyclerView,
                view,
                x,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
    }

    private fun getView(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as TodoListRVAdapter.ViewHolder).itemView.findViewById(R.id.item_todolist_content_panel)
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        val isClamped = getTag(viewHolder)
        Log.d("isclamped1", isClamped.toString())
        Log.d("xxx_currentDx", currentDx.toString())
        // 현재 View가 고정되어있지 않고 사용자가 -clamp 이상 swipe시 isClamped true로 변경 아닐시 false로 변경
        if (currentDx < 0){ // 왼쪽으로 밀렸을 때
            setTag(viewHolder, (!isClamped && currentDx <= -rightClamp))
            Log.d("isclamped2", isClamped.toString())
            Log.d("xxx", "right")
        } else {    // 오른쪽으로 밀렸을 때
            setTag(viewHolder, (!isClamped && currentDx >= leftClamp))
            Log.d("isclamped3", isClamped.toString())
            Log.d("xxx", "left")
        }
        return 2f
    }

    private fun clampViewPositionHorizontal(
        view: View,
        dX: Float,
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ) : Float {
        // View의 가로 길이의 절반까지만 swipe 되도록
        val min: Float = -view.width.toFloat()/2
        // RIGHT 방향으로 swipe 막기
        val max: Float = view.width.toFloat()/2

        val x = if (isClamped) {
            // View가 고정되었을 때 swipe되는 영역 제한
                Log.d("xxx_dX", currentDx.toString())
            if (currentDx < 0f)
                if(isCurrentlyActive) dX - rightClamp else -rightClamp
            else
                if (isCurrentlyActive) dX + leftClamp else leftClamp
        } else {
            dX
        }
        return x.coerceAtLeast(min).coerceAtMost(max)   // x 와 max 사이의 값으로 min 제한
    }

    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        // isClamped를 view의 tag로 관리
        viewHolder.itemView.tag = isClamped
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean {
        // isClamped를 view의 tag로 관리
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    fun setClamp(rightClamp: Float, leftClamp : Float) {
        Log.d("xxxmuyaho", "right : ${rightClamp}, left : ${leftClamp}")
        this.rightClamp = rightClamp
        this.leftClamp = leftClamp
    }

    // 다른 View가 swipe 되거나 터치되면 고정 해제
    fun removePreviousClamp(recyclerView: RecyclerView) {
        // 현재 선택한 view가 이전에 선택한 view와 같으면 패스
        if (currentPosition == previousPosition) return

        // 이전에 선택한 위치의 view 고정 해제
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            val dest = getView(viewHolder).marginStart.toFloat()
            getView(viewHolder).animate().x(dest).setDuration(100L).start()
            setTag(viewHolder, false)
            previousPosition = null
        }
    }
}