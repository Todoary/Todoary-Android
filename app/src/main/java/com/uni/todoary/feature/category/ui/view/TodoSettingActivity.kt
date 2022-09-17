package com.uni.todoary.feature.category.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.uni.todoary.databinding.ActivityTodoSettingBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.category.data.dto.CategoryData
import com.uni.todoary.feature.category.data.dto.CategoryChangeRequest
import com.uni.todoary.feature.category.data.view.CategoryAddView
import com.uni.todoary.feature.category.data.view.CategoryChangeView
import com.uni.todoary.feature.category.data.view.CategoryDeleteView

class TodoSettingActivity : AppCompatActivity(), CategoryAddView, CategoryChangeView,CategoryDeleteView {
    lateinit var binding : ActivityTodoSettingBinding
    var color:Int = 0 //기본값

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.todosettingToolbarTb.toolbarIconIv.setOnClickListener {
            //startActivity(intent)
            finish()
        }

        binding.todosettingToolbarTb.toolbarIconTv.setOnClickListener {
            categoryAdd()
            //startActivity(intent)
            finish()
        }


        binding.todosettingEditEt.setPadding(40,14,5,20)

        // 팔레트 설정
        binding.todosetting0Iv.setOnClickListener {
            color=0
            Removeselect()
            binding.todosetting0Iv.isSelected=!binding.todosetting0Iv.isSelected
        }
        binding.todosetting1Iv.setOnClickListener {
            color=1
            Removeselect()
            binding.todosetting1Iv.isSelected=!binding.todosetting1Iv.isSelected
        }
        binding.todosetting17Iv.setOnClickListener {
            color=17
            Removeselect()
            binding.todosetting17Iv.isSelected=!binding.todosetting17Iv.isSelected
        }
        binding.todosetting6Iv.setOnClickListener {
            color=6
            Removeselect()
            binding.todosetting6Iv.isSelected=!binding.todosetting6Iv.isSelected
        }
        binding.todosetting11Iv.setOnClickListener {
            color=11
            Removeselect()
            binding.todosetting11Iv.isSelected=!binding.todosetting11Iv.isSelected
        }
        binding.todosetting15Iv.setOnClickListener {
            color=15
            Removeselect()
            binding.todosetting15Iv.isSelected=!binding.todosetting15Iv.isSelected
        }
        binding.todosetting9Iv.setOnClickListener {
            color=9
            Removeselect()
            binding.todosetting9Iv.isSelected=!binding.todosetting9Iv.isSelected
        }
        binding.todosetting3Iv.setOnClickListener {
            color=3
            Removeselect()
            binding.todosetting3Iv.isSelected=!binding.todosetting3Iv.isSelected
        }
        binding.todosetting14Iv.setOnClickListener {
            color=14
            Removeselect()
            binding.todosetting14Iv.isSelected=!binding.todosetting14Iv.isSelected
        }
        binding.todosetting13Iv.setOnClickListener {
            color=13
            Removeselect()
            binding.todosetting13Iv.isSelected=!binding.todosetting13Iv.isSelected
        }
        binding.todosetting2Iv.setOnClickListener {
            color=2
            Removeselect()
            binding.todosetting2Iv.isSelected=!binding.todosetting2Iv.isSelected
        }
        binding.todosetting7Iv.setOnClickListener {
            color=7
            Removeselect()
            binding.todosetting7Iv.isSelected=!binding.todosetting7Iv.isSelected
        }
        binding.todosetting10Iv.setOnClickListener {
            color=10
            Removeselect()
            binding.todosetting10Iv.isSelected=!binding.todosetting10Iv.isSelected
        }
        binding.todosetting12Iv.setOnClickListener {
            color=12
            Removeselect()
            binding.todosetting12Iv.isSelected=!binding.todosetting12Iv.isSelected
        }
        binding.todosetting16Iv.setOnClickListener {
            color=16
            Removeselect()
            binding.todosetting16Iv.isSelected=!binding.todosetting16Iv.isSelected
        }
        binding.todosetting8Iv.setOnClickListener {
            color=8
            Removeselect()
            binding.todosetting8Iv.isSelected=!binding.todosetting8Iv.isSelected
        }
        binding.todosetting4Iv.setOnClickListener {
            color=4
            Removeselect()
            binding.todosetting4Iv.isSelected=!binding.todosetting4Iv.isSelected
        }
        binding.todosetting5Iv.setOnClickListener {
            color=5
            Removeselect()
            binding.todosetting5Iv.isSelected=!binding.todosetting5Iv.isSelected
        }


        //카테고리 변경 혹은 삭제 기능
        val flag = intent.getBooleanExtra("flag",false)
        if(flag){
            binding.todosettingToolbarTb.toolbarIconTv.isVisible=true
            if(intent.hasExtra("cateData")) {
                val data = intent.getSerializableExtra("cateData") as CategoryData
                Log.d("cateData",data.toString())

                //제목 띄우기
                binding.todosettingEditEt.setText(data.title)
                //색깔 띄우기
                color = data.color
                checkPallete()
                //카테고리 변경
                binding.todosettingCompleteTv.setOnClickListener {
                    if (binding.todosettingEditEt.text?.isNotEmpty() == true) {
                        val title = binding.todosettingEditEt.text.toString()
                        CategoryChangeRequest(title, color)
                        CategoryChange(data.id)
                    }
                }
                binding.todosettingToolbarTb.toolbarIconTv.setOnClickListener {
                    val checkbuilder = android.app.AlertDialog.Builder(this)
                    checkbuilder.setTitle("알림")
                        .setMessage("카테고리를 삭제 하시겠습니까?")
                        .setNegativeButton("아니오", null)
                        .setPositiveButton("네") { dialogInterface, i ->
                            categoryDelete(data.id)
                            val intent = Intent(this, CategorysettingActivity::class.java)
                            //startActivity(intent)
                            finish()
                        }
                        .show()
                }
            }
        } else checkPallete()

    }




    private fun Removeselect() {
        binding.todosetting0Iv.isSelected=false
        binding.todosetting1Iv.isSelected=false
        binding.todosetting2Iv.isSelected=false
        binding.todosetting3Iv.isSelected=false
        binding.todosetting4Iv.isSelected=false
        binding.todosetting5Iv.isSelected=false
        binding.todosetting6Iv.isSelected=false
        binding.todosetting7Iv.isSelected=false
        binding.todosetting8Iv.isSelected=false
        binding.todosetting9Iv.isSelected=false
        binding.todosetting10Iv.isSelected=false
        binding.todosetting11Iv.isSelected=false
        binding.todosetting12Iv.isSelected=false
        binding.todosetting13Iv.isSelected=false
        binding.todosetting14Iv.isSelected=false
        binding.todosetting15Iv.isSelected=false
        binding.todosetting16Iv.isSelected=false
        binding.todosetting17Iv.isSelected=false
    }

    ////////////카테고리 추가///////////////
    private fun categoryAdd() {
        val categoryAddService = AuthService()
        categoryAddService.setCategoryAddView(this)
        ///RecyclerView///
        if(binding.todosettingEditEt.text?.isNotEmpty() == true){
            val title = binding.todosettingEditEt.text.toString()
            val request = CategoryChangeRequest(title, color)
            categoryAddService.CategoryAdd(request)
        }
    }

    override fun CategoryAddLoading() {
    }

    override fun CategoryAddSuccess() {
        val intent=Intent(this,CategorysettingActivity::class.java)
        Log.d("카테고리 생성","성공")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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

    fun checkPallete(){
        when (color) {
            0 -> binding.todosetting0Iv.isSelected=!binding.todosetting0Iv.isSelected
            1 -> binding.todosetting1Iv.isSelected=!binding.todosetting1Iv.isSelected
            2 -> binding.todosetting2Iv.isSelected=!binding.todosetting2Iv.isSelected
            3 -> binding.todosetting3Iv.isSelected=!binding.todosetting3Iv.isSelected
            4 -> binding.todosetting4Iv.isSelected=!binding.todosetting4Iv.isSelected
            5 -> binding.todosetting5Iv.isSelected=!binding.todosetting5Iv.isSelected
            6 -> binding.todosetting6Iv.isSelected=!binding.todosetting6Iv.isSelected
            7 -> binding.todosetting7Iv.isSelected=!binding.todosetting7Iv.isSelected
            8 -> binding.todosetting8Iv.isSelected=!binding.todosetting8Iv.isSelected
            9 -> binding.todosetting9Iv.isSelected=!binding.todosetting9Iv.isSelected
            10 -> binding.todosetting10Iv.isSelected=!binding.todosetting10Iv.isSelected
            11 -> binding.todosetting11Iv.isSelected=!binding.todosetting11Iv.isSelected
            12 -> binding.todosetting12Iv.isSelected=!binding.todosetting12Iv.isSelected
            13 -> binding.todosetting13Iv.isSelected=!binding.todosetting13Iv.isSelected
            14 -> binding.todosetting14Iv.isSelected=!binding.todosetting14Iv.isSelected
            15 -> binding.todosetting15Iv.isSelected=!binding.todosetting15Iv.isSelected
            16 -> binding.todosetting16Iv.isSelected=!binding.todosetting16Iv.isSelected
            17 -> binding.todosetting17Iv.isSelected=!binding.todosetting17Iv.isSelected
        }


    }

}