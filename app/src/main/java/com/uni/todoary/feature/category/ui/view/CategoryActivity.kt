package com.uni.todoary.feature.category.ui.view

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityCategoryBinding
import com.uni.todoary.feature.main.data.dto.TodoListAlarm
import com.uni.todoary.feature.main.data.dto.TodoListInfo
import kotlin.collections.ArrayList

class CategoryActivity : BaseActivity<ActivityCategoryBinding>(ActivityCategoryBinding::inflate) {

    companion object {
        private val todoListAdapter = CategoryTodoListRVAdapter()
    }

    override fun initAfterBinding() {

        initView()

        // TODO : API 연결하면서 더미데이터 생성하는 부분 없애기
        val todoLists = arrayListOf<TodoListInfo>()
        todoLists.add(TodoListInfo(true, true, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "아랄아랄", TodoListAlarm(false, 6, 30)))
        todoLists.add(TodoListInfo(true, false, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "오롤오롤", TodoListAlarm(true, 7, 30)))
        todoLists.add(TodoListInfo(false, true, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "구룰구룰", TodoListAlarm(true, 6, 45)))
        todoLists.add(TodoListInfo(false, false, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "끼릭끼릭", TodoListAlarm(false, 6, 45)))
        setTodolist(todoLists)
    }

    private fun initView(){
        // 투두리스트 RecyclerView 초기화
        binding.categoryTodoListsRv.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        // 투두리스트 만들기 버튼
        binding.categoryTodoListsAddIv.setOnClickListener {
            val intent = Intent(this, CategorysettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTodolist(dataset : ArrayList<TodoListInfo>){
        Log.d("dataset", dataset.toString())
        todoListAdapter.initTodoLists(dataset)
    }
}