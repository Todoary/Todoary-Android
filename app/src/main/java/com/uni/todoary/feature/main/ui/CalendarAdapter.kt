package com.uni.todoary.feature.main.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.util.CalendarUtil.Companion.selectedDate
import java.time.LocalDate


class CalendarAdapter(private val dayList: ArrayList<LocalDate?>):
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>(){
        
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dayText: TextView = itemView.findViewById(R.id.dayText)
    }

    //화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar, parent, false)

        return ItemViewHolder(view)
    }

    //데이터 설정
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //날자 변수에 담기
        var day = dayList[holder.adapterPosition]

        if(day==null){
            holder.dayText.text = ""
        }else{
            //해당 일자 넣음
            holder.dayText.text = day.dayOfMonth.toString()

            //현재 날짜 색상 변경
            if(day == selectedDate){
                holder.dayText.setBackgroundResource(R.drawable.calendar_today_stroke)
                holder.dayText.setTextColor(Color.WHITE)
            }
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener {
            if(day != selectedDate && day != null) {
                holder.dayText.setBackgroundResource(R.drawable.calendar_datepick_stroke)
            }
        }
        //holder.dayText.text=dayList[holder.adapterPosition]
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}