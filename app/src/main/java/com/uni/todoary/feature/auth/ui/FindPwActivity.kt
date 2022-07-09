package com.uni.todoary.feature.auth.ui

import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityFindPwBinding

class FindPwActivity : BaseActivity<ActivityFindPwBinding>(ActivityFindPwBinding::inflate) {
    override fun initAfterBinding() {
        binding.findPwToolbar.toolbarBackMainTv.text = "비밀번호 재설정"
        binding.findPwToolbar.toolbarBackIv.setOnClickListener {
            finish()
        }
    }
}