package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.uni.todoary.databinding.ActivityProfileEditBinding
import com.uni.todoary.feature.auth.data.dto.User
import android.app.Activity
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.uni.todoary.databinding.ActivityProfileBinding
import com.uni.todoary.feature.auth.data.dto.ProfileChangeRequest
import com.uni.todoary.feature.auth.data.dto.SignInRequest
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.ProfileChangeView
import com.uni.todoary.util.getUser
import com.uni.todoary.util.saveUser


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



        initClickListeners()

        // Data Binding
        val userObserver = Observer<User>{ user ->
            binding.profileeditNameEt.setText(user.nickname)
            binding.profileeditIntroEt.setText(user.introduce)
        }
        userModel.user.observe(this, userObserver)
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
        val mIntent = Intent(this,ProfileActivity::class.java)
        binding.profileEdit.toolbarBackMainTv.text = "계정"
        binding.profileEdit.toolbarBackIv.setOnClickListener {
            ProfileChange()
            userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())
            finish()
        }

        binding.profileditPiceditTv.setOnClickListener {
            editpic()
        }
    }
    override fun onBackPressed() {
        ProfileChange()
        userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())
        finish()
    }

    //닉네임, 한줄소개 변경
    private fun ProfileChange() {
        val nickname = binding.profileeditNameEt.text.toString()
        val introduce = binding.profileeditIntroEt.text.toString()
        val ProfileChangeService = AuthService()
        ProfileChangeService.setProfileChangeView(this)
        val request = ProfileChangeRequest(nickname,introduce)
        ProfileChangeService.ProfileChange(request)

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
