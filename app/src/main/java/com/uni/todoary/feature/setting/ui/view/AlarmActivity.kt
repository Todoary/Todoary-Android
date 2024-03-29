package com.uni.todoary.feature.setting.ui.view

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.R
import com.uni.todoary.base.ApiResult
import com.uni.todoary.databinding.ActivityAlarmBinding
import com.uni.todoary.util.dpToPx
import com.uni.todoary.feature.setting.ui.viewmodel.AlarmViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmActivity : AppCompatActivity(){
    lateinit var binding: ActivityAlarmBinding
    private val alarmModel : AlarmViewModel by viewModels()

    enum class AlarmType{
        TODO,
        DIARY,
        REMIND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        //툴바
        binding.settingAlarmToolbar.toolbarBackMainTv.text = "알림"
        binding.settingAlarmToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }

        setQuestionMarks()
        initObservers()
        initAlarmSwitches()
    }

    private fun initObservers(){
        alarmModel.alarmApiResult.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    binding.alarmTodoarySwitch.isChecked = it.data!!.isTodoAlarmChecked
                    binding.alarmDaySwitch.isChecked = it.data.isDiaryAlarmChecked
                    binding.alarmRemindSwitch.isChecked = it.data.isRemindAlarmChecked
                }
                ApiResult.Status.API_ERROR -> {
                    binding.alarmTodoarySwitch.isChecked = false
                    binding.alarmDaySwitch.isChecked = false
                    binding.alarmRemindSwitch.isChecked = false
                    Snackbar.make(binding.alarmRemindIv, "데이터 베이스에 연결 실패했습니다.", Snackbar.LENGTH_SHORT).show()
                }
                ApiResult.Status.NETWORK_ERROR -> {
                    Log.d("GetAlarm_Api_Error", it.message!!)
                }
            }
        })
        alarmModel.updateAlarmApiResult.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {

                }
                ApiResult.Status.API_ERROR -> {
                    Snackbar.make(binding.alarmRemindIv, "인터넷 연결을 확인해 주세요.", Snackbar.LENGTH_SHORT).show()
                    when (it.data){
                        AlarmType.TODO -> {
                            binding.alarmTodoarySwitch.isChecked = !binding.alarmTodoarySwitch.isChecked
                        }
                        AlarmType.DIARY -> {
                            binding.alarmDaySwitch.isChecked = !binding.alarmDaySwitch.isChecked
                        }
                        AlarmType.REMIND -> {
                            binding.alarmRemindSwitch.isChecked = !binding.alarmRemindSwitch.isChecked
                        }
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> {
                    Log.d("UpdateAlarm_Api_Error", it.message!!)
                }
            }
        })
    }

    private fun initAlarmSwitches(){
        binding.alarmTodoarySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            alarmModel.updateAlarmStatus(AlarmType.TODO, isChecked)
        }
        binding.alarmDaySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            alarmModel.updateAlarmStatus(AlarmType.DIARY, isChecked)
        }
        binding.alarmRemindSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            alarmModel.updateAlarmStatus(AlarmType.REMIND, isChecked)
        }
    }

    // 물음표 눌렀을 때 자세히 보기 설명 (AlertDialog 띄우기)
    private fun setQuestionMarks(){
        binding.alarmTodoaryIv.setOnClickListener {
            val msg = "Todo list의 시간알림입니다.\n" +
                    "알림을 해제하면 모든 Todo list\n" +
                    "알림을 받으실 수 있습니다."
            makeMoreDialog(binding.alarmTodoaryIv, msg)
        }

        binding.alarmDayIv.setOnClickListener {
            val msg = "하루를 매일 기록할 수 있도록 도와주는\n" +
                    "알림입니다. 매일 설정한 시간에 알람이\n" +
                    "올 수 있도록 설정할 수 있습니다."
            makeMoreDialog(binding.alarmDayIv, msg)
        }

        binding.alarmRemindIv.setOnClickListener {
            val msg = "일주일동안 기록을 하지 않으면\n" +
                    "Todoary가 알려줍니다."
            makeMoreDialog(binding.alarmRemindIv, msg)
        }
    }

    fun makeMoreDialog(questionView : ImageView, text : String){
        val builder = AlertDialog.Builder(this, R.style.Dialog_more_Theme)
        val view : View = LayoutInflater.from(this).inflate(R.layout.dialog_more, findViewById(R.id.dialog_more_layout))
        builder.setView(view)
        view.findViewById<TextView>(R.id.dialog_more_tv).text = text
        val dialog = builder.create()
        val params = dialog.window!!.attributes

        // 물음표 상자 위치 구해서 해당 위치로 AlertDialog 옮기기
        val location : IntArray = IntArray(2)
        questionView.getLocationOnScreen(location)      // 절대 위치 구해서 IntArray에 x, y 좌표 저장

        // display의 크기를 가져옴 -> outMetrics
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
        val centerXpos = outMetrics.widthPixels / 2     // 화면 x축 중앙 px값
        val xTarget = centerXpos - dpToPx(this@AlarmActivity, 120f)       // 옮길 px값

        params.x = -xTarget.toInt()     // AlertDialog가 기본적으로 가운데정렬되어서 가운데가 x = 0 으로 되어있기 때문에 그냥 150만큼 뒤로 미룸 (계산하기 귀찮 ㅎㅎ)
        params.y = location[1]
        dialog.window!!.apply {
            attributes = params
            setGravity(Gravity.TOP)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        dialog.show()

        // 원래 다이얼로그 형태 지우기 (커스텀 레이아웃 적용)
        if (dialog.window != null){
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
    }
}
