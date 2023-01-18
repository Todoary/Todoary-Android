package com.uni.todoary.feature.category.ui.view

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.base.ApiResult
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityCategoryBinding
import com.uni.todoary.feature.category.data.module.TodoInfo
import com.uni.todoary.feature.category.ui.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Thread.sleep
import java.time.LocalDate
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CategoryActivity : BaseActivity<ActivityCategoryBinding>(ActivityCategoryBinding::inflate) {
    val model : TodoViewModel by viewModels()
    lateinit var categoryAdapter : CategoryRVAdapter

    override fun initAfterBinding() {
        initView()
        initObservers()
    }

    private fun initView(){
        // 카테고리 리사이클러뷰 초기화
        categoryAdapter = CategoryRVAdapter(this)
        categoryAdapter.setItemSelectedListener(object : CategoryRVAdapter.ItemSelectedListener{
            override fun categorySelectedCallback(categoryIdx: Long) {
                // 뷰모델에 아이템 인덱스 전달
                model.setCategoryIdx(categoryIdx)
            }
        })
        val categoryDecoration = CategoryRVItemDecoration(this)
        binding.categoryArrayRv.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(this@CategoryActivity, LinearLayoutManager.HORIZONTAL, false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            addItemDecoration(categoryDecoration)
        }
        // 툴바 동작 버튼
        binding.signUpToolbar.toolbarIconIv.setOnClickListener{
            finish()
        }
        binding.signUpToolbar.toolbarIconTv.setOnClickListener {
            // TODO : 삭제기능 구현
        }
        // 카테고리 만들기 버튼
//        binding.categoryArrayAddIv.setOnClickListener {
//            val intent = Intent(this, TodoSettingActivity::class.java)
//            Log.d("asdfasdfasdf", "아랄ㄹ라")
//            startActivity(intent)
//        }
        // 투두리스트 만들기 버튼
        binding.categoryTodoListsAddIv.setOnClickListener {
            val intent = Intent(this, TodoSettingActivity::class.java)
            startActivity(intent)
        }
        // 선택된 날짜로 뷰모델에 셋팅
        val date = intent.getSerializableExtra("date") as LocalDate
        model.setDate(date)
        // 카테고리 목록 불러오기
        model.initCategoryList()

    }

    private fun initObservers(){
        model.categoryList.observe(this, {
            when(it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    model.setCategoryIdx(it.data!![0].id)
                    categoryAdapter.setList(it.data)
                }
                else -> {
                    Toast.makeText(this, "카테고리 목록 조회에 실패하였습니다. 인터넷 연결을 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        })  // 카테고리 목록 셋팅
        model.categoryIdx.observe(this, {
            model.getCategoryTodo()
        })  // 투두리스트 불러오기
        model.categoryTodoResp.observe(this, {
            when(it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    initTodoLists(it.data!!)
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code){
                        2005, 2010 -> goToReLogin(this@CategoryActivity)
                        2301 -> Snackbar.make(binding.categoryTodoListsRv, "존재하지 않는 카테고리 입니다. 다시 시도해 주세요.", Snackbar.LENGTH_SHORT).show()
                        else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Get_Category_TodoList_Api_Error", it.message!!)
            }
        })  // 불러온 투두리스트들로 리사이클러뷰에 뿌리기
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
                            Snackbar.make(binding.categoryTodoListsAddIv, "존재하지 않는 투두리스트 입니다.", Snackbar.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(this, "Database Error!!!", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> Log.d("Todo_Check_Api_Error", it.message!!)
            }
        })  // 체크했을 때 API통신
    }

    private fun initTodoLists(list : ArrayList<TodoInfo>){
        val mAdapter = CategoryTodoListRVAdapter(this@CategoryActivity)
        mAdapter.setItemClickListener(object : CategoryTodoListRVAdapter.ItemClickListener{
            override fun todoCheckListener(todoId: Long, isChecked: Boolean) {
                model.todoCheck(todoId, isChecked)
            }

        })
        binding.categoryTodoListsRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@CategoryActivity, LinearLayoutManager.VERTICAL, false)
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        mAdapter.initTodoLists(list)
    }

    override fun onResume() {
        super.onResume()
        sleep(500)
        model.initCategoryList()
    }
}