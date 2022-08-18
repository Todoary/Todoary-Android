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


        //카테고리 변경 혹은 삭제 기능
        val flag = intent.getBooleanExtra("flag",false)
        if(flag){
            binding.toolbarBackDeleteIv.isVisible=true
            if(intent.hasExtra("cateData")) {
                val data = intent.getSerializableExtra("cateData") as CategoryData
                Log.d("cateData",data.toString())

                //제목 띄우기
                binding.todosettingEditEt.setText(data.title)
                //색깔 띄우기
                checkPallete()
                //카테고리 변경
                binding.todosettingCompleteTv.setOnClickListener {
                    if (binding.todosettingEditEt.text?.isNotEmpty() == true) {
                        val title = binding.todosettingEditEt.text.toString()
                        CategoryChangeRequest(title, color)
                        CategoryChange(data.id)
                    }
                }
                binding.toolbarBackDeleteIv.setOnClickListener {
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
        val data = intent.getSerializableExtra("cateData") as CategoryData
        if(data.color==0)
            binding.todosettingOrangeIv.isSelected=!binding.todosettingOrangeIv.isSelected
        else if(data.color==1)
            binding.todosettingYellowIv.isSelected=!binding.todosettingYellowIv.isSelected
        else if(data.color==2)
            binding.todosettingBisquitIv.isSelected=!binding.todosettingBisquitIv.isSelected
        else if(data.color==3)
            binding.todosettingGreenIv.isSelected=!binding.todosettingGreenIv.isSelected
        else if(data.color==4)
            binding.todosettingDarkgreenIv.isSelected=!binding.todosettingDarkgreenIv.isSelected
        else if(data.color==5)
            binding.todosettingDarkpinkIv.isSelected=!binding.todosettingDarkpinkIv.isSelected
        else if(data.color==6)
            binding.todosettingMintIv.isSelected=!binding.todosettingMintIv.isSelected
        else if(data.color==7)
            binding.todosettingLightmintIv.isSelected=!binding.todosettingLightmintIv.isSelected
        else if(data.color==8)
            binding.todosettingApricotIv.isSelected=!binding.todosettingApricotIv.isSelected
        else if(data.color==9)
            binding.todosettingLightbrownIv.isSelected=!binding.todosettingLightbrownIv.isSelected
        else if(data.color==10)
            binding.todosettingBrownIv.isSelected=!binding.todosettingBrownIv.isSelected
        else if(data.color==11)
            binding.todosettingLightorangeIv.isSelected=!binding.todosettingLightorangeIv.isSelected
        else if(data.color==12)
            binding.todosettingBlueIv.isSelected=!binding.todosettingBlueIv.isSelected
        else if(data.color==13)
            binding.todosettingDarkblueIv.isSelected=!binding.todosettingDarkblueIv.isSelected
        else if(data.color==14)
            binding.todosettingGrayIv.isSelected=!binding.todosettingGrayIv.isSelected
        else if(data.color==15)
            binding.todosettingLightgrayIv.isSelected=!binding.todosettingLightgrayIv.isSelected
        else if(data.color==16)
            binding.todosettingBabypinkIv.isSelected=!binding.todosettingBabypinkIv.isSelected
        else if(data.color==17)
            binding.todosettingPinkIv.isSelected=!binding.todosettingPinkIv.isSelected


    }

}