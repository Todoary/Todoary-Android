package com.uni.todoary.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.feature.category.ui.view.TodoSettingActivity
import com.uni.todoary.feature.main.data.dto.TodoListInfo

class CategoryRVAdapter(private val context: Context, val catelist: ArrayList<CategoryData>):  RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {
    //public var catelist = arrayListOf<CategoryData>()

    var mPosition = 0

    fun setPosition(position: Int){
        mPosition = position
    }
    fun addItem(dataVo:CategoryData){
        catelist.add(dataVo)
        notifyItemInserted(mPosition)
    }

    fun removeItem(position: Int){
        if(position>=0){
            catelist.removeAt(position)
            //notifyDataSetChanged()
            notifyItemRemoved(mPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_card_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(catelist[position],context)
        holder.itemView.setOnClickListener { view->
            val intent = Intent(context,TodoSettingActivity::class.java)
            intent.apply {
                putExtra("cateData", catelist[position])
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run {
                Log.d("클릭이벤트","작동")
                context.startActivity(intent) }
        }

    }

    override fun getItemCount(): Int {
        return catelist.size
    }
    // 각 항목에 필요한 기능을 구현
    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        val title = itemView.findViewById<TextView>(R.id.cate_item_tv)
        val card = itemView.findViewById<CardView>(R.id.cate_card_cv)
        var categoryColor = itemView.resources.getIntArray(R.array.category_array)

        fun bind(dataVo: CategoryData, context: Context){
            title.text=dataVo.title
            //배경색 지정하는 코드
            card.setCardBackgroundColor(categoryColor.get(dataVo.color))

        }

    }

}