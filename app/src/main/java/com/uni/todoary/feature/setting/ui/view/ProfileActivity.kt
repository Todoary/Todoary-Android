package com.uni.todoary.feature.setting.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.uni.todoary.base.ApiResult
import com.uni.todoary.base.BaseDialog
import com.uni.todoary.databinding.ActivityProfileBinding
import com.uni.todoary.feature.auth.data.dto.User
import com.uni.todoary.feature.auth.ui.view.FindPwActivity
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.auth.data.view.DeleteMemberView
import com.uni.todoary.feature.auth.ui.view.LoginActivity
import com.uni.todoary.feature.setting.ui.viewmodel.ProfileViewModel
import com.uni.todoary.util.removeRefToken
import com.uni.todoary.util.removeUser
import com.uni.todoary.util.removeXcesToken
import com.uni.todoary.util.saveIsAutoLogin
import com.uni.todoary.feature.main.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Data Bidning
        initView()

        binding.profilePwdLl.setOnClickListener {
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
                    userModel.logOut()
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
                    userModel.deleteUser()
                }
            })
            dialog.show(supportFragmentManager, "destroy_id_dialog")
        }
        binding.profileEditBtn.setOnClickListener {
            val editintent = Intent(this, ProfileEditActivity::class.java)
            startActivity(editintent)
        }
    }


    private fun initView(){
        binding.profileIdTv.isSelected = true
        val userObserver = Observer<User>{user ->
            binding.profileNameTv.text = user.nickname
            binding.profileIntroTv.text = user.introduce
            binding.profileIdTv.text = user.email
        }
        userModel.user.observe(this, userObserver)

        userModel.deleteResult.observe(this, {
            when (it.status){
                ApiResult.Status.SUCCESS ->{
                    val dialog = BaseDialog()
                    val btnData = arrayOf("네")
                    dialog.arguments = bundleOf(
                        "titleContext" to "알림",
                        "bodyContext" to "계정삭제가 정상적으로 처리되었습니다\n"+
                                        "로그인 화면으로 돌아갑니다.",
                        "btnData" to btnData
                    )
                    dialog.setButtonClickListener(object: BaseDialog.OnButtonClickListener{
                        override fun onButton1Clicked() {
                            goToReLogin()
                        }
                        override fun onButton2Clicked() {

                        }
                    })
                    dialog.show(supportFragmentManager, "destroy_id_result_dialog")
                }
                else -> {
                    Toast.makeText(this, "잘못된 요청입니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    goToReLogin()
                }
            }
        })
        userModel.logOutResult.observe(this, {
            when (it.status){
                ApiResult.Status.SUCCESS ->{
                    val dialog = BaseDialog()
                    val btnData = arrayOf("네")
                    dialog.arguments = bundleOf(
                        "titleContext" to "알림",
                        "bodyContext" to "로그아웃 했습니다. 다시 로그인 해주세요.",
                        "btnData" to btnData
                    )
                    dialog.setButtonClickListener(object: BaseDialog.OnButtonClickListener{
                        override fun onButton1Clicked() {
                            goToReLogin()
                        }
                        override fun onButton2Clicked() {

                        }
                    })
                    dialog.show(supportFragmentManager, "logout_result_dialog")
                }
                else -> {}
            }
        })
    }

    fun goToReLogin(){
        val mIntent = Intent(this, LoginActivity::class.java)
        mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mIntent)
    }

    override fun onResume() {
        super.onResume()
        userModel.getUser()
    }
}