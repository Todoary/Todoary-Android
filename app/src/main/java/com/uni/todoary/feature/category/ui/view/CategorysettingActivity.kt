package com.uni.todoary.feature.category.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uni.todoary.databinding.ActivityCategorysettingBinding

class CategorysettingActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategorysettingBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorysettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
            binding.categorysettingToolbarTb.toolbarBackMainTv.text = " "

            binding.categorysettingToolbarTb.toolbarBackIv.setOnClickListener {
                finish()
            }

        binding.categorysettingEditEt.setPadding(10,10,5,20)
        binding.categorysettingAddLayout.setOnClickListener {
            val mIntent = Intent(this, TodoSettingActivity::class.java)
            startActivity(mIntent)

        }
//            if(binding.alarmTodoarySwitch.isChecked){
            binding.alarmTodoarySwitch.setOnClickListener {
                if(binding.alarmTodoarySwitch.isChecked) {
                    val bottomSheet = SettingAlarmBottomSheet()
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                }
            }
            binding.categorysettingDateLayout.setOnClickListener {
                val bottomSheet = SettingCalendarBottomSheet()
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }

            //mainactivity에서 참고
            //val list
            //val adapter=CategoryRVAdapter(this, list)
            binding.categorysettingRecyclerRv.apply {

            }


    }

    override fun onResume() {
        super.onResume()
    }

}