package com.uni.todoary.feature.setting.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.uni.todoary.databinding.ActivityProfileEditBinding
import com.uni.todoary.feature.auth.data.dto.User
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.auth.data.dto.ProfileChangeRequest
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.ProfileChangeView
import com.uni.todoary.feature.setting.ui.viewmodel.ProfileViewModel
import com.uni.todoary.util.getUser
import com.uni.todoary.util.saveUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditActivity : AppCompatActivity(), ProfileChangeView{
    lateinit var binding: ActivityProfileEditBinding
    private val userModel : ProfileViewModel by viewModels()
    lateinit var getContent : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            binding.profileImageIv.setImageURI(result.data?.data)

        }
        binding.profileeditConfirmBtn.setOnClickListener {
            userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())
        }

        initClickListeners()

        // Data Binding
        val userObserver = Observer<User>{ user ->
            binding.profileeditNameEt.setText(user.nickname)
            binding.profileeditIntroEt.setText(user.introduce)
        }
        userModel.user.observe(this, userObserver)

        setApiWatchers()
    }

    private fun setApiWatchers(){
        userModel.updateResult.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {

                }
                ApiResult.Status.SUCCESS -> {
                    Toast.makeText(this, "프로필 정보 변경에 성공했습니다.", Toast.LENGTH_SHORT).show()
                }
                else -> Toast.makeText(this, "code : ${it.code}, message : ${it.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun editpic() {
        //Todo: 사진변경 기능 추가
        val intent = Intent()
        intent.type = "image/*"
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.action = Intent.ACTION_GET_CONTENT
        getContent.launch(intent)
    }

    private fun initClickListeners(){
        //툴바
        binding.profileEdit.toolbarBackMainTv.text = "계정"
        binding.profileEdit.toolbarBackIv.setOnClickListener {
            finish()
        }

        binding.profileditPiceditTv.setOnClickListener {
            editpic()
        }
    }
    override fun onBackPressed() {
        finish()
    }

    override fun ProfileChangeLoading() {
    }

    override fun ProfileChangeSuccess() {
        Log.d("변경","성공")

        val user = getUser()!!
        user.nickname=binding.profileeditNameEt.text.toString()
        user.introduce=binding.profileeditIntroEt.text.toString()
        saveUser(user)

        val intent = Intent(this, ProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())

        startActivity(intent)
    }

    override fun ProfileChangeFailure(code: Int) {
        Log.d("변경","실패")
        Log.d("Error", code.toString())
    }

}
