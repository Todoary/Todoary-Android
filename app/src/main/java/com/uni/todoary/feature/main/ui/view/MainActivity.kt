package com.uni.todoary.feature.main.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityMainBinding
import com.uni.todoary.feature.main.data.dto.TodoListAlarm
import com.uni.todoary.feature.main.data.dto.TodoListInfo
import com.uni.todoary.feature.setting.ui.view.SettingActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.uni.todoary.feature.category.ui.view.CategoryActivity
import com.uni.todoary.util.dpToPx
import kotlin.collections.ArrayList
import com.uni.todoary.feature.category.ui.view.CategorysettingActivity
import com.uni.todoary.feature.main.ui.viewmodel.MainViewModel
import com.uni.todoary.feature.setting.ui.view.ProfileActivity
import com.uni.todoary.util.getXcesToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    val model : MainViewModel by viewModels()

    override fun initAfterBinding() {

        initView()
        setSlidingPanelHeight()

        // 달력 프래그먼트 달기
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_calendar_fl, CalendarFragment())
            .commit()

        // TODO: API 연결 시 더미데이터 부분 삭제
        val todoLists = arrayListOf<TodoListInfo>()
        todoLists.add(TodoListInfo(true, true, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "아랄아랄", TodoListAlarm(false, 6, 30)))
        todoLists.add(TodoListInfo(true, false, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "오롤오롤", TodoListAlarm(true, 7, 30)))
        todoLists.add(TodoListInfo(false, true, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "구룰구룰", TodoListAlarm(true, 6, 45)))
        todoLists.add(TodoListInfo(false, false, "뛝쁅뽥쬻뀷뀛끵꽓뜛춁뒑퉳줡뚊뀖꾧", "끼릭끼릭", TodoListAlarm(false, 6, 45)))
        setTodolist(todoLists)

        getFCMToken()
        Log.d("jwtjwt", getXcesToken()!!)


    }

    private fun initView(){
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
        val targetHeight = outMetrics.heightPixels - dpToPx(this, 500f)
        //val targetHeight = outMetrics.heightPixels - 500f
        val handler = android.os.Handler(Looper.getMainLooper())
        handler.postDelayed( {
            binding.mainSlidingPanelLayout.panelHeight = targetHeight.toInt()
        },
            20)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTodolist(todoList : ArrayList<TodoListInfo>){
        val todolistAdapter = TodoListRVAdapter()
        todolistAdapter.setTodoList(todoList)
        val swipeCallback = TodoListSwipeHelper().apply {
            setClamp(dpToPx(this@MainActivity, 110f).toFloat(), dpToPx(this@MainActivity, 55f).toFloat())
            //setClamp(110f,55f)
        }
        val swipeHelper = ItemTouchHelper(swipeCallback)
        swipeHelper.attachToRecyclerView(binding.mainSlideTodolistRv)
        binding.mainSlideTodolistRv.apply {
            adapter = todolistAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setOnTouchListener { _, _ ->
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
}