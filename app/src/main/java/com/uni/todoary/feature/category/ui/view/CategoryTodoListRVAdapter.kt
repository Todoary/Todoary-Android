package com.uni.todoary.feature.category.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.databinding.ItemCategoryTodoListsRvBinding
import com.uni.todoary.feature.main.data.dto.TodoListInfo

class CategoryTodoListRVAdapter() : RecyclerView.Adapter<CategoryTodoListRVAdapter.ViewHolder>() {

    var todoLists : ArrayList<TodoListInfo> = arrayListOf()

    inner class ViewHolder(val binding : ItemCategoryTodoListsRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initView(position: Int){
            binding.itemCategoryTodolistContentTv.text = todoLists[position].content
            binding.itemCategoryTodolistCb.isChecked = todoLists[position].isChecked
            if(todoLists[position].alarm.enable){
                binding.itemCategoryTodolistAlarmLayout.visibility = View.VISIBLE
            } else {
                binding.itemCategoryTodolistAlarmLayout.visibility = View.GONE
            }
            binding.itemCategoryTodolistCategoryTv.text = todoLists[position].category
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryTodoListRVAdapter.ViewHolder {
        val binding = ItemCategoryTodoListsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryTodoListRVAdapter.ViewHolder, position: Int) {
        holder.initView(position)
    }

    override fun getItemCount(): Int {
        return this.todoLists.size
    }

    fun initTodoLists(dataset : ArrayList<TodoListInfo>){
        this.todoLists.clear()
        this.todoLists.addAll(dataset)
        notifyDataSetChanged()
    }
}