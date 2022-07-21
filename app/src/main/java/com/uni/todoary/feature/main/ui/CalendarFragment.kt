package com.uni.todoary.feature.main.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.databinding.FragmentCalendarBinding
import com.uni.todoary.util.CalendarUtil
import com.uni.todoary.util.CalendarUtil.Companion.selectedDate
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    //lateinit var selectedDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val backbtn = binding.calendarBackIv
        val nextbtn = binding.calendarNextIv

        //현재 날짜
        selectedDate = LocalDate.now()

        //화면 설정
        setMonthView()

        //이전 달 버튼 이벤트
        backbtn.setOnClickListener {
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        //다음 달 버튼 이벤트
        nextbtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        //년월 텍스트뷰 셋팅
        binding.calendarMonthTv.text = monthYearFromDate(selectedDate)
        //날짜 생성해서 리스트에 담음
        val dayList= dayInMonthArray(selectedDate)
        val adapter = CalendarAdapter(dayList)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
        binding.calendarDateRv.layoutManager = manager
        binding.calendarDateRv.adapter = adapter
    }

    //날짜 생성
    @RequiresApi(Build.VERSION_CODES.O)
    private fun dayInMonthArray(date: LocalDate): ArrayList<LocalDate?> {
        var dayList = ArrayList<LocalDate?>()
        var yearMonth = YearMonth.from(date)

        //해당 월 마지막 날짜 가겨조익 (예: 28, 30, 31)
        var lastDay = yearMonth.lengthOfMonth()
        //해당 월 첫 번째 날 가져오기
        var firstDay = selectedDate.withDayOfMonth(1)
        //첫 번째 날 요일 가져오기
        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..41) {
            if (i <= dayOfWeek || i > (lastDay + dayOfWeek)) {
                dayList.add(null)
            } else {
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.year, selectedDate.monthValue, i-dayOfWeek))
            }

        }
        return dayList
    }

    //날짜 타입 설정
    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate): String {
        var formatter = DateTimeFormatter.ofPattern("yyyy"+"년 MM월")

        //받아온 날짜를 해당 포맷으로 변경
        return date.format(formatter)

    }
}