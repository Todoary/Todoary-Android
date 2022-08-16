package com.uni.todoary.feature.main.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.setting.ui.view.SettingActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.category.ui.view.CategoryActivity
import com.uni.todoary.util.dpToPx
import java.util.*
import kotlin.collections.ArrayList
import com.uni.todoary.feature.category.ui.view.CategorysettingActivity
import com.uni.todoary.feature.main.data.module.TodoListResponse
import com.uni.todoary.feature.main.ui.viewmodel.MainViewModel
import com.uni.todoary.feature.setting.ui.view.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val model : MainViewModel by viewModels()
    private var backpressedTime : Long = 0

    override fun initAfterBinding() {

        initView()
        setSlidingPanelHeight()
        initObserver()
        getFCMToken()

    }

    private fun initView(){
        // 달력 프래그먼트 달기
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment())
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
            .replace(R.id.main_calendar_fl, CalendarFragment())
            .commit()

        // 카테고리 버튼
        binding.mainSlideMenuGridIv.setOnClickListener {
            val categoryIntent = Intent(this, CategoryActivity::class.java)
            categoryIntent.putExtra("date", model.date.value)
            startActivity(categoryIntent)
        }

        // 투두리스트 생성 버튼
        binding.mainSlideMenuAddIv.setOnClickListener {
            val intent=Intent(this, CategorysettingActivity::class.java)
            startActivity(intent)
        }

        // 일기 작성 버튼
        binding.mainPostingBtnCv.setOnClickListener{
            val intent = Intent(this, DiaryActivity::class.java)
            startActivity(intent)
        }

        // 프로필 화면
        binding.mainProfileLayout.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initObserver(){
        model.todoListResponse.observe(this, {
            when(it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    setTodolist(it.data!!)
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code){
                        2005, 2010 -> {
                            Toast.makeText(this, "유효하지 않은 회원정보입니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                            goToReLogin(this)
                        } else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Get_Todo_List_Api_Error", it.message!!)
            }
        })      // 투두리스트 생성
        model.todoCheckResponse.observe(this, {
            when(it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {}
                ApiResult.Status.API_ERROR -> {
                    when(it.code){
                        2005, 2010 -> {
                            Toast.makeText(this, "유효하지 않은 회원정보 입니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                            goToReLogin(this)
                        }
                        2302 -> {
                            Snackbar.make(binding.mainSlidingPanelLayout, "존재하지 않는 투두리스트 입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Todo_Check_Api_Error", it.message!!)
            }
        })      // 체크했을 때 API통신
    }

    private fun setSlidingPanelHeight(){
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
        val targetHeight = outMetrics.heightPixels - dpToPx(this, 520f)
        val handler = android.os.Handler(Looper.getMainLooper())
        handler.postDelayed( {
            binding.mainSlidingPanelLayout.panelHeight = targetHeight
        },
            20)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTodolist(todoList : ArrayList<TodoListResponse>){
        // 어댑터 셋팅
        val todolistAdapter = TodoListRVAdapter(this)
        todolistAdapter.apply{
            setTodoList(todoList)
            setItemClickListener(object : TodoListRVAdapter.ItemClickListener{
                override fun todoCheckListener(todoId: Long, isChecked: Boolean) {
                    model.todoCheck(todoId, isChecked)
                }
            })
        }

        // 스와이프 메뉴 셋팅 -> dpToPx 함수 사용해서 해상도에 따른 비율코딩
        val swipeCallback = TodoListSwipeHelper().apply {
            setClamp(dpToPx(this@MainActivity, 110f).toFloat(), dpToPx(this@MainActivity, 55f).toFloat())
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

    private fun getFCMToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
//                Log.d("registration token", token) // 로그에 찍히기에 서버에게 보내줘야됨
//                Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
            })
    }

    override fun onResume() {
        super.onResume()
        model.getTodoList()
        model.getCalendarInfo()
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() > backpressedTime + 2000){
            backpressedTime = System.currentTimeMillis()
            Snackbar.make(binding.mainSlidingPanelLayout, "진짜 종료하실건가요..? :)", Snackbar.LENGTH_SHORT).show()
            return
        } else {
            finish()
        }
    }
}