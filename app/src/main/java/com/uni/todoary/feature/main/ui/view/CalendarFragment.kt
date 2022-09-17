package com.uni.todoary.feature.main.ui.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.base.ApiResult
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.databinding.FragmentCalendarBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.ui.view.LoginActivity
import com.uni.todoary.feature.main.data.dto.GetDiaryRequest
import com.uni.todoary.feature.main.data.view.GetDiaryView
import com.uni.todoary.feature.main.ui.viewmodel.MainViewModel
import kotlinx.coroutines.selects.select
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalendarFragment(binding: ActivityMainBinding) : Fragment(), GetDiaryView {
    val model : MainViewModel by activityViewModels()
    var main = binding
    companion object{
        var selectedDate: LocalDate = LocalDate.now()
    }

    private lateinit var binding: FragmentCalendarBinding
    //lateinit var selectedDate: LocalDate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        val backbtn = binding.calendarBackIv
        val nextbtn = binding.calendarNextIv

        //현재 날짜
//        selectedDate = LocalDate.now()

        //화면 설정
        setMonthView()
        initObservers()

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

    private fun initObservers(){
        model.getCalendarInfoResp.observe(viewLifecycleOwner, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    initCalendar(it.data!!)     // 성공 시 달력 생성
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code){
                        2005, 2010 -> {
                            Toast.makeText(requireContext(), "잘못된 유저정보입니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                            goToReLogin(requireContext())
                        }
                        else -> Toast.makeText(requireContext(), "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Get_Calendar_Info_Api_Error", it.message!!)
            }
        })  // 달력에 마킹할 날짜배열 가져오는 API
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        // 날짜 변경 후 calendar에 marking할 데이터 요청
        model.date.value = selectedDate
        model.getCalendarInfo()
        //년월 텍스트뷰 셋팅
        binding.calendarMonthTv.text = monthYearFromDate(selectedDate)
    }

    private fun initCalendar(markerList : ArrayList<Int>){
        //날짜 생성해서 리스트에 담음
        val dayList= dayInMonthArray(selectedDate)
        val adapter = CalendarAdapter(dayList, markerList)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
        adapter.setItemClickListener(object : CalendarAdapter.ItemClickListener{
            override fun onDateSelect(day: LocalDate) {
                model.date.value = day
                model.getTodoList()
                GetDiary(day.toString())

            }

        })
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
                dayList.add(LocalDate.of(selectedDate.year, selectedDate.monthValue, i-dayOfWeek))
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

    fun goToReLogin(context : Context){
        val mIntent = Intent(context, LoginActivity::class.java)
        mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mIntent)
    }
    private fun GetDiary(createdDate:String){
        val getDiaryService = AuthService()
        getDiaryService.setGetDiaryView(this)
        getDiaryService.GetDiary(createdDate)
    }
    override fun GetDiaryLoading() {
    }

    override fun GetDiarySuccess(code: Int, result: GetDiaryRequest) {
        main.mainSlideDiaryTitleTv.text=result.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            main.mainDiaryTv.text=Html.fromHtml(result.content, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            main.mainDiaryTv.text=Html.fromHtml(result.content).toString()
        }
        //main.mainDiaryTv.text=result.content
    }


    override fun GetDiaryFailure(code: Int) {
        Log.d("다이어리 조회실패",code.toString())
        main.mainDiaryTv.text=""
        main.mainSlideDiaryTitleTv.text="오늘의 일기를 작성해주세요!"
    }
}