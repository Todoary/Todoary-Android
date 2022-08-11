package com.uni.todoary.feature.category.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.todoary.databinding.ActivityCategorysettingBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.category.data.view.CategoryAddView
import com.uni.todoary.feature.category.data.view.GetCategoryView
import com.uni.todoary.util.CategoryData
import com.uni.todoary.util.CategoryRVAdapter
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList

class CategorysettingActivity : AppCompatActivity(), GetCategoryView {
    lateinit var binding : ActivityCategorysettingBinding
    var cateLists : ArrayList<CategoryData> = arrayListOf()
    val categoryintent = intent
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorysettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categorysettingToolbarTb.toolbarBackMainTv.text = " "

        binding.categorysettingToolbarTb.toolbarBackIv.setOnClickListener {
            finish()
        }

        binding.categorysettingEditEt.setPadding(10,10,5,20)
        binding.categorysettingCatebtnLl.setOnClickListener {
            val mIntent = Intent(this, TodoSettingActivity::class.java)
            startActivity(mIntent)

        }

        binding.alarmTodoarySwitch.setOnClickListener {
            if(binding.alarmTodoarySwitch.isChecked) {
                val bottomSheet = SettingAlarmBottomSheet()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            }
        }
        binding.categorysettingDateLl.setOnClickListener {
            val bottomSheet = SettingCalendarBottomSheet()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }

        GetCategory()

    }

    override fun onResume() {
        super.onResume()
        GetCategory()
    }

    private fun GetCategory(){
        val GetCategoryService = AuthService()
        GetCategoryService.setGetCategoryView(this)
        GetCategoryService.GetCategory()
    }
    override fun GetCategoryLoading() {
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun GetCategorySuccess(result:List<CategoryData>) {
        Log.d("카테고리 조회", "성공")
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        cateLists.clear()
        val adapter = CategoryRVAdapter(this, cateLists)
        binding.categorysettingRecyclerRv.layoutManager = layoutManager
        binding.categorysettingRecyclerRv.adapter = adapter

        //리사이클러뷰에 추가
        binding.categorysettingRecyclerRv.apply {
            for(i in result){
                adapter.addItem(CategoryData(i.id, i.title, i.color))
            }
        }
        adapter.notifyDataSetChanged()
        Log.d("카테고리 사이즈", adapter.itemCount.toString())
    }

    override fun GetCategoryFailure(code: Int) {
        Log.d("카테고리 조회","실패")

    }





}
