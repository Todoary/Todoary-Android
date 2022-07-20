package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.uni.todoary.databinding.ActivityProfileEditBinding
import com.uni.todoary.feature.auth.data.dto.User

class ProfileEditActivity : AppCompatActivity(){
    lateinit var binding: ActivityProfileEditBinding
    private val userModel : ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClickListeners()

        // Data Binding
        val userObserver = Observer<User>{ user ->
            binding.profileeditNameEt.setText(user.name)
            binding.profileeditIntroEt.setText(user.intro)
        }
        userModel.user.observe(this, userObserver)
    }

    private fun editpic() {
        //Todo: 사진변경 기능 추가
    }

    private fun initClickListeners(){
        //툴바
        binding.profileEdit.toolbarBackMainTv.text = "계정"
        binding.profileEdit.toolbarBackIv.setOnClickListener {
            intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.profileditPiceditTv.setOnClickListener {
            editpic()
        }
    }

    override fun onBackPressed() {
        userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())
        finish()
    }
}
