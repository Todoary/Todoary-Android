package com.uni.todoary.feature.main.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.main.data.dto.TodoListAlarm
import com.uni.todoary.feature.main.data.dto.TodoListInfo
import com.uni.todoary.feature.setting.ui.SettingActivity
import com.uni.todoary.util.getRefToken
import com.uni.todoary.util.getXcesToken

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initAfterBinding() {
        // 설정 버튼
        binding.mainMenuIv.setOnClickListener {
            val menuIntent = Intent(this, SettingActivity::class.java)
            startActivity(menuIntent)
        }

        // 달력 프래그먼트 달기
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment())
            .commit()

        // TODO: API 연결 시 더미데이터 부분 삭제
        val todoLists = arrayListOf<TodoListInfo>()
        todoLists.add(TodoListInfo(true, true, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", arrayListOf("아랄아랄", "기릴기릴"), TodoListAlarm(false, 6, 30)))
        todoLists.add(TodoListInfo(true, false, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", arrayListOf("오롤오롤", "기릴기릴"), TodoListAlarm(true, 7, 30)))
        todoLists.add(TodoListInfo(false, true, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", arrayListOf("구룰구룰", "기릴기릴"), TodoListAlarm(true, 6, 45)))
        todoLists.add(TodoListInfo(false, false, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", arrayListOf("끼릭끼릭", "기릴기릴"), TodoListAlarm(false, 6, 45)))
        setTodolist(todoLists)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTodolist(todoList : ArrayList<TodoListInfo>){
        val todolistAdapter = TodoListRVAdapter()
        todolistAdapter.setTodoList(todoList)
        val swipeCallback = TodoListSwipeHelper().apply {
            setClamp(300f, 200f)
        }
        val swipeHelper = ItemTouchHelper(swipeCallback)
        swipeHelper.attachToRecyclerView(binding.mainSlideTodolistRv)
        binding.mainSlideTodolistRv.apply {
            adapter = todolistAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setOnTouchListener { _, _ ->
                swipeCallback.removePreviousClamp(this)
                false
            }
        }
    }
}