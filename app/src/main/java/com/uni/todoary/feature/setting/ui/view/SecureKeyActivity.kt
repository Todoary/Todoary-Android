package com.uni.todoary.feature.setting.ui.view

import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivitySettingPwBinding

class SecureKeyActivity : BaseActivity<ActivitySettingPwBinding>(ActivitySettingPwBinding::inflate) {
    override fun initAfterBinding() {
        binding.settingPwSwitch.setSwitchTextAppearance()
    }
}