package com.uni.todoary.feature.setting.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityProfileEditBinding

class ProfileEditActivity : AppCompatActivity(){
    lateinit var binding: ActivityProfileEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, ProfileActivity::class.java)
        //툴바
        binding.profileEdit.toolbarBackMainTv.text = "계정"
        binding.profileEdit.toolbarBackIv.setOnClickListener {
            finish()
        }

        binding.profileditPiceditTv.setOnClickListener {
            editpic()
        }
    }

    private fun editpic() {
        //Todo: 사진변경 기능 추가
    }

}
