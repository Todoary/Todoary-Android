package com.uni.todoary.feature.setting.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityFontBinding

class FontActivity : AppCompatActivity(){
    lateinit var binding: ActivityFontBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, DisplayActivity::class.java)

        //툴바
        binding.settingFont.toolbarBackMainTv.text = "글꼴"
        binding.settingFont.toolbarBackIv.setOnClickListener {
            finish()
        }

        //라디오버튼 이벤트
        binding.settingFontRg.setOnCheckedChangeListener { group, i ->
            when(i){
                //ToDo: 라디오이벤트 추가
                //i -> 아이디
            }
        }

    }
}
