package com.uni.todoary.feature.main.ui.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.databinding.ItemMainSlideTodolistRvBinding
import com.uni.todoary.feature.main.data.module.TodoListResponse
import com.uni.todoary.util.alarmFormatter
import java.util.*
import kotlin.collections.ArrayList

class TodoListRVAdapter(context : Context) : RecyclerView.Adapter<TodoListRVAdapter.ViewHolder>() {
    val categoryColors  = context.resources.obtainTypedArray(R.array.category_array)
    private var todolist = arrayListOf<TodoListResponse>()
    lateinit var mItemClickListener: ItemClickListener

    interface ItemClickListener {
        fun todoCheckListener(todoId : Long, isChecked : Boolean)
        fun todoPinListener(todoId : Long, isPinned : Boolean, position: Int)
    }

    fun setItemClickListener(listener : ItemClickListener){
        this.mItemClickListener = listener
    }

    inner class ViewHolder(val binding : ItemMainSlideTodolistRvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initView(position : Int){
            val todolist = todolist[position]
            val color = categoryColors.getColor(todolist.color, 0)
            // 알람 표시
            if (todolist.isAlarmEnabled){
                binding.itemTodolistAlarmLayout.visibility = View.VISIBLE
                val time = alarmFormatter(todolist.targetTime)
                binding.itemTodolistAlarmTv.text = time
            }
            if (todolist.isPinned){     // 고정된 항목
                binding.itemTodolistPinSmallIv.visibility = View.VISIBLE
            } else {
                binding.itemTodolistPinSmallIv.visibility = View.GONE
            }
            binding.itemTodolistCb.isChecked = todolist.isChecked
            binding.itemTodolistContentTv.text = todolist.title
            // 카테고리 색 변경
            binding.itemTodolistCategoryTv.apply {
                text = todolist.categoryTitle
                setTextColor(color)
            }
            binding.itemTodolistCategoryCv.strokeColor = color
        }
        fun initClickListener(position : Int){
            val todolist = todolist[position]
            binding.itemTodolistCb.setOnCheckedChangeListener { buttonView, isChecked ->
                mItemClickListener.todoCheckListener(todolist.todoId, isChecked)
            }
            binding.itemTodolistPinIv.setOnClickListener {
                mItemClickListener.todoPinListener(todolist.todoId, !todolist.isPinned, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMainSlideTodolistRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initView(position)
        holder.initClickListener(position)
    }

    override fun getItemCount(): Int {
        return this.todolist.size
    }

    fun setTodoList(todolist : ArrayList<TodoListResponse>){
        val sortedList = ArrayList(todolist.sortedWith(compareBy(
            {!it.isPinned},
            {it.createdTime}
        )))
        this.todolist = sortedList
        Log.d("lislis", this.todolist.toString())
        notifyDataSetChanged()
    }

    fun pinTodo(position : Int){
        this.todolist[position].isPinned = !this.todolist[position].isPinned
        setTodoList(this.todolist)
    }
}