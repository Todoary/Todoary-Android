package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity(){
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, SettingActivity::class.java)
        //툴바
        binding.settingProfile.toolbarBackMainTv.text = "계정"
        binding.settingProfile.toolbarBackIv.setOnClickListener {
            startActivity(intent)
        }
        binding.profilePwdBtn.setOnClickListener {
            //ToDo: 비밀번호 재설정 이벤트
        }
        binding.profileLogoutLl.setOnClickListener {
            //ToDo: 로그아웃 이벤트
            logout()
        }
        binding.profileDeleteLl.setOnClickListener {
            //ToDo: 계정 삭제 이벤트
            delete()
        }
        binding.profileEditBtn.setOnClickListener {
            val editintent = Intent(this, ProfileEditActivity::class.java)
            startActivity(editintent)
        }
    }

    private fun logout() {
        //다이얼로그
        val codebuilder = android.app.AlertDialog.Builder(this)
        codebuilder.setTitle("알림")
            .setMessage("로그아웃 하시겠습니까?")
            .setNegativeButton("취소",null)
            .setPositiveButton("로그아웃",null)
            .show()

        //ToDo: 로그아웃 기능
    }

    private fun delete() {
        //다이얼로그
        val codebuilder = android.app.AlertDialog.Builder(this)
        codebuilder.setTitle("알림")
            .setMessage("정말 계정을 탈퇴하시겠습니까?\n" +
                    "삭제된 데이터는 복구할 수 없습니다.")
            .setNegativeButton("아니오",null)
            .setPositiveButton("네",null)
            .show()

        //ToDo: 계정삭제 기능
    }
}
