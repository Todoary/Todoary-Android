package com.uni.todoary.feature.setting.ui.view

import android.content.Intent
import android.view.View
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivitySettingPwBinding
import com.uni.todoary.util.getSecureKey
import com.uni.todoary.util.saveSecureKey

class SecureKeyActivity : BaseActivity<ActivitySettingPwBinding>(ActivitySettingPwBinding::inflate) {
    override fun initAfterBinding() {
        if(getSecureKey() != null){
            binding.settingPwSwitch.isChecked = true
            binding.settingPwChangeLayout.visibility = View.VISIBLE
            binding.settingPwLine2.visibility = View.VISIBLE
        } else {
            binding.settingPwSwitch.isChecked = false
            binding.settingPwChangeLayout.visibility = View.GONE
            binding.settingPwLine2.visibility = View.GONE
        }
        binding.settingPwSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(!isChecked){
                saveSecureKey(null)
                binding.settingPwChangeLayout.visibility = View.GONE
                binding.settingPwLine2.visibility = View.GONE
            } else {
                binding.settingPwChangeLayout.visibility = View.VISIBLE
                binding.settingPwLine2.visibility = View.VISIBLE
            }
        }
        binding.settingPwChangeLayout.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        //툴바
        binding.settingPwToolbar.toolbarBackMainTv.text = "암호"
        binding.settingPwToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }
    }
}