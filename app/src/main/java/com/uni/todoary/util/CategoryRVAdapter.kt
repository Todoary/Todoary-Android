package com.uni.todoary.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R

class CategoryRVAdapter(private val context: Context,private val dataList: ArrayList<CategoryData>):  RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {
    //var listData = mutableListOf<CategoryData>()

    var mPosition = 0

    fun setPosition(position: Int){
        mPosition = position
    }
    fun addItem(dataVo:CategoryData){
        dataList.add(dataVo)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        if(position>=0){
            dataList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.categoryitem_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position],context)
        holder.itemView.setOnClickListener { view->
            setPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    // 각 항목에 필요한 기능을 구현
    class ViewHolder(itmeView:View) : RecyclerView.ViewHolder(itmeView) {
        val title = itemView.findViewById<TextView>(R.id.cate_item_tv)

        fun bind(dataVo: CategoryData, context: Context){
            title.text=dataVo.title
            //title.background=TodoSettingActivity().colorArray[color]

        }

    }

}