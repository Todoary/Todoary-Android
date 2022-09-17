package com.uni.todoary.feature.main.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.toHtml
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.setting.ui.view.SettingActivity
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.ui.view.CategoryActivity
import com.uni.todoary.feature.category.ui.view.CategoryRVAdapter
import com.uni.todoary.util.dpToPx
import kotlin.collections.ArrayList
import com.uni.todoary.feature.category.ui.view.CategorysettingActivity
import com.uni.todoary.feature.category.ui.viewmodel.TodoViewModel
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.dto.GetDiaryRequest
import com.uni.todoary.feature.main.data.module.TodoListResponse
import com.uni.todoary.feature.main.data.view.DeleteDiaryView
import com.uni.todoary.feature.main.data.view.GetDiaryView
import com.uni.todoary.feature.main.ui.viewmodel.MainViewModel
import com.uni.todoary.feature.setting.ui.view.ProfileActivity
import com.uni.todoary.util.SoftKeyboardDectectorView
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate), DeleteDiaryView {
    val model: MainViewModel by viewModels()
    private val todolistAdapter: TodoListRVAdapter by lazy {
        TodoListRVAdapter(this)
    }
    val todo_model: TodoViewModel by viewModels()
    private var backpressedTime: Long = 0

    override fun initAfterBinding() {
        initView()
        setSlidingPanelHeight()
        initObserver()
    }

    private fun initView() {
        // 달력 프래그먼트 달기
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment(binding))
            .commit()

        // 프로필
        binding.mainProfileNameTv.text = model.user.value!!.nickname
        binding.mainProfileIntroTv.text = model.user.value!!.introduce

        // 설정 버튼
        binding.mainMenuIv.setOnClickListener {
            val menuIntent = Intent(this, SettingActivity::class.java)
            startActivity(menuIntent)
        }

        // 달력 프래그먼트 달기
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment(binding))
            .commit()

        // 카테고리 버튼
        binding.mainSlideMenuGridIv.setOnClickListener {
            val categoryIntent = Intent(this, CategoryActivity::class.java)
            categoryIntent.putExtra("date", model.date.value)
            startActivity(categoryIntent)
        }

        // 투두리스트 생성 버튼
        binding.mainSlideMenuAddIv.setOnClickListener {
            val intent = Intent(this, CategorysettingActivity::class.java)
            startActivity(intent)
        }

        // 일기 작성 버튼
        binding.mainPostingBtnCv.setOnClickListener {
            val diaryintent = Intent(this, DiaryActivity::class.java)
            diaryintent.putExtra("date", model.date.value)
            startActivity(diaryintent)
        }

        // 일기 삭제 버튼
        binding.mainDiarydeleteIv.setOnClickListener {
            DeleteDiary()
        }

        // 프로필 화면
        binding.mainProfileLayout.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


        //키보드 감지
        val softKeyboardDecector = SoftKeyboardDectectorView(this)
        addContentView(softKeyboardDecector, FrameLayout.LayoutParams(-1, -1))

        softKeyboardDecector.setOnShownKeyboard(object :
            SoftKeyboardDectectorView.OnShownKeyboardListener {
            override fun onShowSoftKeyboard() {
                //키보드 보일때
                binding.diaryKeytoolbarLl.visibility = View.VISIBLE
            }
        })

        softKeyboardDecector.setOnHiddenKeyboard(object :
            SoftKeyboardDectectorView.OnHiddenKeyboardListener {
            override fun onHiddenSoftKeyboard() {
                binding.diaryKeytoolbarLl.visibility = View.INVISIBLE
            }
        })

        //다이어리 조회
//        val date = Date(System.currentTimeMillis())
//        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
//        val strDate = dateFormat.format(date)
//        //다이어리 달력 선택한 날짜 받아오는 거

    }

    private fun initObserver() {
        model.todoListResponse.observe(this, {
            when (it.status) {
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    setTodolist(it.data!!)
                    if (it.data.size != 0) {
                        binding.mainSlideTodolistNewCv.visibility = View.GONE
                    } else binding.mainSlideTodolistNewCv.visibility = View.VISIBLE
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code) {
                        2005, 2010 -> {
                            Toast.makeText(
                                this,
                                "유효하지 않은 회원정보입니다. 다시 로그인 해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                            goToReLogin(this)
                        }
                        else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Get_Todo_List_Api_Error", it.message!!)
            }
        })      // 투두리스트 생성
        model.todoCheckResponse.observe(this, {
            when (it.status) {
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {}
                ApiResult.Status.API_ERROR -> {
                    when (it.code) {
                        2005, 2010 -> {
                            Toast.makeText(
                                this,
                                "유효하지 않은 회원정보 입니다. 다시 로그인 해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                            goToReLogin(this)
                        }
                        2302 -> {
                            Snackbar.make(
                                binding.mainSlidingPanelLayout,
                                "존재하지 않는 투두리스트 입니다.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Todo_Check_Api_Error", it.message!!)
            }
        })      // 체크했을 때 API통신
        model.todoPinResp.observe(this, {
            when (it.status) {
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    todolistAdapter.pinTodo(it.data!!)
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code) {
                        2005, 2010 -> {
                            Toast.makeText(
                                this,
                                "유효하지 않은 회원정보 입니다. 다시 로그인 해주세요.",
                                Toast.LENGTH_SHORT
                            ).show()
                            goToReLogin(this)
                        }
                        2302 -> {
                            Snackbar.make(
                                binding.mainSlidingPanelLayout,
                                "존재하지 않는 투두리스트 입니다.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Todo_Check_Api_Error", it.message!!)
            }
        })            // 고정 했을 때 API 통신
    }

    private fun setSlidingPanelHeight() {
        val outMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = this.display
            display?.getRealMetrics(outMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = this.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(outMetrics)
        }
        val targetHeight = outMetrics.heightPixels - dpToPx(this, 500f)
        val handler = android.os.Handler(Looper.getMainLooper())
        handler.postDelayed(
            {
                binding.mainSlidingPanelLayout.panelHeight = targetHeight
            },
            20
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTodolist(todoList: ArrayList<TodoListResponse>) {
        // 어댑터 셋팅
        todolistAdapter.apply {
            setTodoList(todoList)
            setItemClickListener(object : TodoListRVAdapter.ItemClickListener {
                override fun todoCheckListener(todoId: Long, isChecked: Boolean) {
                    model.todoCheck(todoId, isChecked)
                }

                override fun todoPinListener(todoId: Long, isPinned: Boolean, position: Int) {
                    model.todoPin(todoId, isPinned, position)
                }
            })
        }

        // 스와이프 메뉴 셋팅 -> dpToPx 함수 사용해서 해상도에 따른 비율코딩
        val swipeCallback = TodoListSwipeHelper().apply {
            setClamp(
                dpToPx(this@MainActivity, 110f).toFloat(),
                dpToPx(this@MainActivity, 55f).toFloat()
            )
        }
        val swipeHelper = ItemTouchHelper(swipeCallback)
        swipeHelper.attachToRecyclerView(binding.mainSlideTodolistRv)
        binding.mainSlideTodolistRv.apply {
            adapter = todolistAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setOnTouchListener { _, _ ->        // 다른 영역 터치했을 때 clamp 된거 초기화
                swipeCallback.removePreviousClamp(this)
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        model.getTodoList()
        model.getCalendarInfo()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis()
            Snackbar.make(binding.mainSlidingPanelLayout, "진짜 종료하실건가요..? :)", Snackbar.LENGTH_SHORT)
                .show()
            return
        } else {
            finish()
        }
    }

    // CategoryList 리사이클러뷰 생성함수 (Flexbox Library 사용)
    private fun makeCategoryList(list: ArrayList<CategoryData>) {
        val mAdapter = CategoryRVAdapter(this)
        val mLayoutManager = FlexboxLayoutManager(this)
        mLayoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }
        mAdapter.setItemSelectedListener(object : CategoryRVAdapter.ItemSelectedListener {
            override fun categorySelectedCallback(categoryIdx: Long) {
                // 뷰모델에 아이템 인덱스 전달
                todo_model.setCategoryIdx(categoryIdx)
            }
        })
        binding.mainCatervRv.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        mAdapter.setList(list)
    }

    //// 다이어리 삭제 ////
    private fun DeleteDiary() {
        val createdDate = model.date.value.toString()
        val deleteDiaryService = AuthService()
        deleteDiaryService.setDeleteDiaryView(this)
        deleteDiaryService.DeleteDiary(createdDate)
        }


    override fun DeleteDiaryLoading() {
    }

    override fun DeleteDiarySuccess() {
        Log.d("다이어리 삭제","성공")
        Toast.makeText(this,"일기가 삭제되었습니다.",Toast.LENGTH_SHORT).show()
    }

    override fun DeleteDiaryFailure(code: Int) {
        Log.d("다이어리 삭제 실패",code.toString())
    }


}