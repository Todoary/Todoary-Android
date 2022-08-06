package com.uni.todoary.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R

class DiaryAdapter(): RecyclerView.Adapter<DiaryAdapter.MyViewHolder>(){

    var titles = arrayOf("장보기", "택배 보내기")
    //var details = arrayOf("Item one", "Item two", "Item three", "Item four", "Itme five")


    override fun onCreateViewHolder(viewgroup: ViewGroup, position: Int): MyViewHolder {
        var v: View = LayoutInflater.from(viewgroup.context).inflate(R.layout.diarycard_layout, viewgroup, false)

        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemtitle.setText(titles.get(position))
//        holder.itemimage.setImageResource(images.get(position))
//        holder.itemdetail.setText(details.get(position))
    }

    override fun getItemCount(): Int {
        return titles.size
    }
    class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        //        public var itemimage: ImageView = itemview.item_image
        public var itemtitle: TextView = itemview.findViewById(R.id.item_title)
    }
}
