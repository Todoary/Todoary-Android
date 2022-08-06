package com.uni.todoary.feature.main.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.databinding.ItemMainSlideTodolistRvBinding
import com.uni.todoary.feature.main.data.dto.TodoListInfo

class TodoListRVAdapter : RecyclerView.Adapter<TodoListRVAdapter.ViewHolder>() {
    private var todolist = arrayListOf<TodoListInfo>()
    inner class ViewHolder(val binding : ItemMainSlideTodolistRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initView(position : Int){
            val todolist = todolist[position]
            if (todolist.alarm.enable){
                binding.itemTodolistAlarmLayout.visibility = View.VISIBLE
                val time = "${todolist.alarm.hour}:${todolist.alarm.minute}"
                binding.itemTodolistAlarmTv.text = time
            }
            if (todolist.isPined){
                // TODO : 정렬 관해서 추가 논의 , 기능구현 필요
                binding.itemTodolistPinSmallIv.visibility = View.VISIBLE
            }
            binding.itemTodolistCb.isChecked = todolist.isChecked
            binding.itemTodolistContentTv.text = todolist.content
            // TODO : 카테고리 색 변경 알고리즘 추가 해야함.
            binding.itemTodolistCategoryTv.text = todolist.category[0]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMainSlideTodolistRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initView(position)
    }

    override fun getItemCount(): Int {
        return this.todolist.size
    }

    fun setTodoList(todolist : ArrayList<TodoListInfo>){
        this.todolist = todolist
    }
}