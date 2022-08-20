package com.uni.todoary.feature.category.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.databinding.ItemCategoryTodoListsRvBinding
import com.uni.todoary.feature.category.data.module.TodoInfo

class CategoryTodoListRVAdapter(context : Context) : RecyclerView.Adapter<CategoryTodoListRVAdapter.ViewHolder>() {

    var todoLists : ArrayList<TodoInfo> = arrayListOf()
    val categoryColors  = context.resources.obtainTypedArray(R.array.category_array)
    lateinit var mItemClickListener: ItemClickListener

    interface ItemClickListener {
        fun todoCheckListener(todoId : Long, isChecked : Boolean)
    }

    fun setItemClickListener(listener : ItemClickListener){
        this.mItemClickListener = listener
    }

    inner class ViewHolder(val binding : ItemCategoryTodoListsRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initView(position: Int){
            val todolist = todoLists[position]
            val dateMonth = todolist.targetDate.slice(IntRange(5,6)).toInt()
            val dateDay = todolist.targetDate.slice(IntRange(8,9)).toInt()
            val dateString = "${dateMonth}월 ${dateDay}일"
            val color = categoryColors.getColor(todolist.color, 0)

            binding.itemCategoryTodolistContentTv.text = todolist.title
            binding.itemCategoryTodolistCb.isChecked = todolist.isChecked
            if(todoLists[position].isAlarmEnabled){
                binding.itemCategoryTodolistAlarmLayout.visibility = View.VISIBLE
                binding.itemCategoryTodolistAlarmTv.text = todolist.targetTime
            } else {
                binding.itemCategoryTodolistAlarmLayout.visibility = View.GONE
            }
            binding.itemCategoryTodolistCategoryTv.text = todolist.categoryTitle
            binding.itemCategoryTodolistDateTv.text = dateString
            binding.itemCategoryTodolistCategoryCv.strokeColor = color
            binding.itemCategoryTodolistCategoryTv.setTextColor(color)
        }
        fun initClickListener(position : Int){
            val todolist = todoLists[position]
            binding.itemCategoryTodolistCb.setOnCheckedChangeListener { buttonView, isChecked ->
                mItemClickListener.todoCheckListener(todolist.todoId, isChecked)
            }
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
        holder.initClickListener(position)
    }

    override fun getItemCount(): Int {
        return this.todoLists.size
    }

    fun initTodoLists(dataset : ArrayList<TodoInfo>){
        this.todoLists.clear()
        this.todoLists.addAll(dataset)
        notifyDataSetChanged()
    }
}