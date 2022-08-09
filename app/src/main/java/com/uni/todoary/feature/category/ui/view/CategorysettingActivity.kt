package com.uni.todoary.feature.category.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
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
            val list = arrayListOf<CategoryData>()
            list.add(CategoryData("아랄아랄", 1))
            list.add(CategoryData("오롤", 2))
            list.add(CategoryData("구루룩구루룩", 3))
            list.add(CategoryData("게렐게렐게렐게렐", 4))
            list.add(CategoryData("아랄라", 5))
            list.add(CategoryData("롤롤ㄹ로롤ㄹㄹ", 6))
            list.add(CategoryData("오롤오롤", 7))


            val mAdapter=CategoryRVAdapter(this)
            val mLayoutManager = FlexboxLayoutManager(this)
            mLayoutManager.apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
            }
            mAdapter.setItemSelectedListener(object : CategoryRVAdapter.ItemSelectedListener{
                override fun categorySelectedCallback(colorIdx: Int) {
                    // TODO : 뷰모델에 아이템 인덱스 전달
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
    }
}