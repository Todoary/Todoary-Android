package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.uni.todoary.base.BaseDialog
import com.uni.todoary.databinding.ActivityProfileBinding
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.ui.FindPwActivity
import com.uni.todoary.feature.auth.ui.PwLockViewModel
import com.uni.todoary.util.getUser

class ProfileActivity : AppCompatActivity(){
    lateinit var binding: ActivityProfileBinding
    private val userModel : ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //툴바
        binding.settingProfile.toolbarBackMainTv.text = "계정"
        binding.settingProfile.toolbarBackIv.setOnClickListener {
            finish()
        }

        //닉넴 및 한줄소개 변경
        Applychange()

        // Data Bidning
        initView()

        binding.profilePwdBtn.setOnClickListener {
            val mIntent = Intent(this, FindPwActivity::class.java)
            startActivity(mIntent)
        }
        binding.profileLogoutLl.setOnClickListener {
            val dialog = BaseDialog()
            val btnData = arrayOf("취소", "로그아웃")
            dialog.arguments = bundleOf(
                "titleContext" to "알림",
                "bodyContext" to "로그아웃 하시겠습니까?",
                "btnData" to btnData
            )
            dialog.setButtonClickListener(object: BaseDialog.OnButtonClickListener{
                override fun onButton1Clicked() {

                }
                override fun onButton2Clicked() {
                    logout()
                }
            })
            dialog.show(supportFragmentManager, "logout_dialog")
        }
        binding.profileDeleteLl.setOnClickListener {
            val dialog = BaseDialog()
            val btnData = arrayOf("아니오", "네")
            dialog.arguments = bundleOf(
                "titleContext" to "알림",
                "bodyContext" to "정말 계정을 탈퇴하시겠습니까?\n" +
                        "삭제된 데이터는 복구할 수 없습니다.",
                "btnData" to btnData
            )
            dialog.setButtonClickListener(object: BaseDialog.OnButtonClickListener{
                override fun onButton1Clicked() {

                }
                override fun onButton2Clicked() {
                    delete()
                }
            })
            dialog.show(supportFragmentManager, "destroy_id_dialog")
        }
        binding.profileEditBtn.setOnClickListener {
            val editintent = Intent(this, ProfileEditActivity::class.java)
            startActivity(editintent)
        }
    }

    private fun Applychange() {
        val name = binding.profileNameTv
        val intro = binding.profileIntroTv
        //val oriname=binding.profileNameTv.text.toString()

        val namevalue=intent.getStringExtra("user_name")
        val introvalue=intent.getStringExtra("user_intro")
        if (namevalue != null && introvalue != null) {
            Log.d("user_name: ",namevalue)
            Log.d("user_name: ",introvalue)
            name.text=namevalue
            intro.text=introvalue
        }

    }

    private fun logout() {
        //ToDo: 로그아웃 기능
    }

    private fun delete() {
        //ToDo: 계정삭제 기능
    }

    private fun initView(){
        binding.profileIdTv.isSelected = true
        val userObserver = Observer<User>{user ->
            binding.profileNameTv.text = user.name
            binding.profileIntroTv.text = user.intro
            binding.profileIdTv.text = user.email
        }
        userModel.user.observe(this, userObserver)
    }


}
