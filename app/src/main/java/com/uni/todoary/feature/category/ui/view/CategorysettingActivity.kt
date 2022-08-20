package com.uni.todoary.feature.category.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.base.ApiResult
import com.uni.todoary.databinding.ActivityCategorysettingBinding
import com.uni.todoary.feature.auth.ui.view.LoginActivity
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.ui.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategorysettingActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategorysettingBinding
    val model : TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorysettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObservers()

        model.initCategoryList()
    }

    private fun initObservers(){
        model.date.observe(this, {
            val dateString = "${it.year}년 ${it.monthValue}월 ${it.dayOfMonth}일"
            binding.categorysettingDateBtnTv.text = dateString
        })  // 날짜 셋팅
        model.categoryList.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    model.setCategoryIdx(it.data!![0].id)
                    makeCategoryList(it.data)
                }
                else -> {
                    Toast.makeText(this, "카테고리 목록 조회에 실패하였습니다. 인터넷 연결을 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        })  // 카테고리 목록 셋팅
        model.createTodoResp.observe(this, {
            when(it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    finish()
                }
                ApiResult.Status.API_ERROR -> {
                    when(it.code){
                        2005, 2010 -> {
                            val handler = android.os.Handler(Looper.getMainLooper())
                            handler.postDelayed({
                                Snackbar.make(
                                    binding.categorysettingRegisterTv,
                                    "유효하지 않은 회원정보입니다. 다시 로그인 해주세요.",
                                Snackbar.LENGTH_SHORT).show() },
                                0)
                            goToReLogin()
                        }
                        2301 -> {
                            Snackbar.make(binding.categorysettingRegisterTv, "존재하지 않는 카테고리 입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(this, "Database error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> {
                    Log.d("CreateTodo_Api_Error", it.message!!)
                }
            }
        })  // 카테고리 생성 API 응답
    }

    private fun goToReLogin(){
        val mIntent = Intent(this, LoginActivity::class.java)
        mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mIntent)
    }

    private fun initView(){
        // 툴바 세팅
        binding.categorysettingToolbarTb.toolbarBackMainTv.text = " "
        binding.categorysettingToolbarTb.toolbarBackIv.setOnClickListener {
            finish()
        }

        // 카테고리 새로 추가하는 Activity 실행 -> 되돌아오면 onResume에서 카테고리 목록 refresh
        binding.categorysettingAddLayout.setOnClickListener {
            val mIntent = Intent(this, TodoSettingActivity::class.java)
            startActivity(mIntent)
        }
        // 알람 설정
        binding.alarmTodoarySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val bottomSheet = SettingAlarmBottomSheet()
                bottomSheet.isCancelable = false        // 바깥 영역 클릭시 dismiss() 되는 현상 막기
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            } else {
                model.setAlarmInfo(false, null, null)
            }
        }

        // 날짜 설정 bottomSheet
        binding.categorysettingDateLayout.setOnClickListener {
            val bottomSheet = SettingCalendarBottomSheet()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }

        // content 입력 시 viewmmodel에 전달
        binding.categorysettingEditEt.addTextChangedListener {
            model.setContent(it.toString())
        }

        // 완료 버튼
        binding.categorysettingRegisterTv.setOnClickListener {
            model.createTodo()
        }
    }

    // CategoryList 리사이클러뷰 생성함수 (Flexbox Library 사용)
    private fun makeCategoryList(list : ArrayList<CategoryData>){
        val mAdapter=CategoryRVAdapter(this)
        val mLayoutManager = FlexboxLayoutManager(this)
        mLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        mAdapter.setItemSelectedListener(object : CategoryRVAdapter.ItemSelectedListener{
            override fun categorySelectedCallback(categoryIdx: Long) {
                // 뷰모델에 아이템 인덱스 전달
                model.setCategoryIdx(categoryIdx)
            }
        })
        binding.categorysettingRecyclerRv.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        mAdapter.setList(list)
    }

    override fun onResume() {
        super.onResume()
        model.initCategoryList()
    }
}