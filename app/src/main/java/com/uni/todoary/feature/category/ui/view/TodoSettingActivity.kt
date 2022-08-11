package com.uni.todoary.feature.category.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityTodoSettingBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.category.data.dto.CategoryAddRequest
import com.uni.todoary.feature.category.data.dto.CategoryChangeRequest
import com.uni.todoary.feature.category.data.view.CategoryAddView
import com.uni.todoary.feature.category.data.view.CategoryChangeView
import com.uni.todoary.feature.category.data.view.CategoryDeleteView
import com.uni.todoary.util.CategoryData
import com.uni.todoary.util.CategoryRVAdapter
import java.util.ArrayList

class TodoSettingActivity : AppCompatActivity(), CategoryAddView, CategoryChangeView,CategoryDeleteView {
    lateinit var binding : ActivityTodoSettingBinding
    var color:Int = 0 //기본값

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarBackIv.setOnClickListener {
            //startActivity(intent)
            finish()
        }


        binding.todosettingCompleteTv.setOnClickListener {
            categoryAdd()
            //startActivity(intent)
            finish()
        }


        binding.todosettingEditEt.setPadding(40,14,5,20)


        binding.todosettingOrangeIv.setOnClickListener {
            color=0
            Removeselect()
            binding.todosettingOrangeIv.isSelected=!binding.todosettingOrangeIv.isSelected
        }
        binding.todosettingYellowIv.setOnClickListener {
            color=1
            Removeselect()
            binding.todosettingYellowIv.isSelected=!binding.todosettingYellowIv.isSelected
        }
        binding.todosettingPinkIv.setOnClickListener {
            color=17
            Removeselect()
            binding.todosettingPinkIv.isSelected=!binding.todosettingPinkIv.isSelected
        }
        binding.todosettingMintIv.setOnClickListener {
            color=6
            Removeselect()
            binding.todosettingMintIv.isSelected=!binding.todosettingMintIv.isSelected
        }
        binding.todosettingLightorangeIv.setOnClickListener {
            color=11
            Removeselect()
            binding.todosettingLightorangeIv.isSelected=!binding.todosettingLightorangeIv.isSelected
        }
        binding.todosettingLightgrayIv.setOnClickListener {
            color=15
            Removeselect()
            binding.todosettingLightgrayIv.isSelected=!binding.todosettingLightgrayIv.isSelected
        }
        binding.todosettingLightbrownIv.setOnClickListener {
            color=9
            Removeselect()
            binding.todosettingLightbrownIv.isSelected=!binding.todosettingLightbrownIv.isSelected
        }
        binding.todosettingGreenIv.setOnClickListener {
            color=3
            Removeselect()
            binding.todosettingGreenIv.isSelected=!binding.todosettingGreenIv.isSelected
        }
        binding.todosettingGrayIv.setOnClickListener {
            color=14
            Removeselect()
            binding.todosettingGrayIv.isSelected=!binding.todosettingGrayIv.isSelected
        }
        binding.todosettingDarkblueIv.setOnClickListener {
            color=13
            Removeselect()
            binding.todosettingDarkblueIv.isSelected=!binding.todosettingDarkblueIv.isSelected
        }
        binding.todosettingBisquitIv.setOnClickListener {
            color=2
            Removeselect()
            binding.todosettingBisquitIv.isSelected=!binding.todosettingBisquitIv.isSelected
        }
        binding.todosettingLightmintIv.setOnClickListener {
            color=7
            Removeselect()
            binding.todosettingLightmintIv.isSelected=!binding.todosettingLightmintIv.isSelected
        }
        binding.todosettingBrownIv.setOnClickListener {
            color=10
            Removeselect()
            binding.todosettingBrownIv.isSelected=!binding.todosettingBrownIv.isSelected
        }
        binding.todosettingBlueIv.setOnClickListener {
            color=12
            Removeselect()
            binding.todosettingBlueIv.isSelected=!binding.todosettingBlueIv.isSelected
        }
        binding.todosettingBabypinkIv.setOnClickListener {
            color=16
            Removeselect()
            binding.todosettingBabypinkIv.isSelected=!binding.todosettingBabypinkIv.isSelected
        }
        binding.todosettingApricotIv.setOnClickListener {
            color=8
            Removeselect()
            binding.todosettingApricotIv.isSelected=!binding.todosettingApricotIv.isSelected
        }
        binding.todosettingDarkgreenIv.setOnClickListener {
            color=4
            Removeselect()
            binding.todosettingDarkgreenIv.isSelected=!binding.todosettingDarkgreenIv.isSelected
        }
        binding.todosettingDarkpinkIv.setOnClickListener {
            color=5
            Removeselect()
            binding.todosettingDarkpinkIv.isSelected=!binding.todosettingDarkpinkIv.isSelected
        }

        if(intent.hasExtra("cateData")) {
            val data = intent.getSerializableExtra("cateData") as CategoryData
            Log.d("cateData",data.toString())
            if (intent.hasExtra("cateData")) {
                //제목 띄우기
                binding.todosettingEditEt.setText(data.title)

                //카테고리 변경
                binding.todosettingCompleteTv.setOnClickListener {
                    if (binding.todosettingEditEt.text?.isNotEmpty() == true) {
                        val title = binding.todosettingEditEt.text.toString()
                        CategoryChangeRequest(title, color)
                        CategoryChange(data.id)
                    }
                }
            }
            binding.toolbarBackDeleteIv.setOnClickListener {
                val checkbuilder = android.app.AlertDialog.Builder(this)

                checkbuilder.setTitle("알림")
                    .setMessage("카테고리를 삭제 하시겠습니까?")
                    .setNegativeButton("아니오",null)
                    .setPositiveButton("네") { dialogInterface, i ->
                        categoryDelete(data.id)
                        val intent=Intent(this,CategorysettingActivity::class.java)
                        //startActivity(intent)
                        finish()
                    }
                    .show()
            }
        }


    }




    private fun Removeselect() {
        binding.todosettingApricotIv.isSelected=false
        binding.todosettingBabypinkIv.isSelected=false
        binding.todosettingBisquitIv.isSelected=false
        binding.todosettingBlueIv.isSelected=false
        binding.todosettingBrownIv.isSelected=false
        binding.todosettingDarkgreenIv.isSelected=false
        binding.todosettingDarkblueIv.isSelected=false
        binding.todosettingDarkpinkIv.isSelected=false
        binding.todosettingGrayIv.isSelected=false
        binding.todosettingGreenIv.isSelected=false
        binding.todosettingLightbrownIv.isSelected=false
        binding.todosettingLightgrayIv.isSelected=false
        binding.todosettingLightmintIv.isSelected=false
        binding.todosettingLightorangeIv.isSelected=false
        binding.todosettingMintIv.isSelected=false
        binding.todosettingOrangeIv.isSelected=false
        binding.todosettingPinkIv.isSelected=false
        binding.todosettingYellowIv.isSelected=false
    }

    ////////////카테고리 추가///////////////
    private fun categoryAdd() {
        val categoryAddService = AuthService()
        categoryAddService.setCategoryAddView(this)
        ///RecyclerView///
        if(binding.todosettingEditEt.text?.isNotEmpty() == true){
            val title = binding.todosettingEditEt.text.toString()
            val request = CategoryAddRequest(title,color)
            categoryAddService.CategoryAdd(request)
        }
    }

    override fun CategoryAddLoading() {
    }

    override fun CategoryAddSuccess() {
        val intent=Intent(this,CategorysettingActivity::class.java)
        Log.d("카테고리 생성","성공")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        startActivity(intent)
//        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)

    }


    override fun CategoryAddFailure(code: Int) {
        Log.d("Error",code.toString())
    }

    override fun CategoryChangeLoading() {
    }

    private fun CategoryChange(categoryId:Long){
        val categoryChangeService = AuthService()
        categoryChangeService.setCategoryChangeView(this)
        if(binding.todosettingEditEt.text?.isNotEmpty() == true){
            val title = binding.todosettingEditEt.text.toString()
            val request = CategoryChangeRequest(title,color)
            categoryChangeService.CategoryChange(categoryId,request)
        }
    }

    override fun CategoryChangeSuccess() {
        val intent=Intent(this,CategorysettingActivity::class.java)
        Log.d("카테고리 변경","성공")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
//        overridePendingTransition(0, 0)
//        startActivity(intent)
//        overridePendingTransition(0, 0)
    }

    override fun CategoryChangeFailure(code: Int) {
        Log.d("Error",code.toString())
    }


    private fun categoryDelete(categoryId: Long) {
        val categoryDeleteService = AuthService()
        categoryDeleteService.setCategoryDeleteView(this)
        if(binding.todosettingEditEt.text?.isNotEmpty() == true){
            categoryDeleteService.CategoryDelete(categoryId)
        }
    }

    override fun CategoryDeleteLoading() {
    }

    override fun CategoryDeleteSuccess() {

    }

    override fun CategoryDeleteFailure(code: Int) {

    }

}