package com.uni.todoary.feature.main.ui.view

import android.content.Intent
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.uni.todoary.base.BaseActivity
import com.uni.todoary.databinding.ActivityOnBoardBinding
import com.uni.todoary.feature.auth.ui.view.LoginActivity

private lateinit var onBoardVPadapter: OnBoardVPAdapter

class OnBoardActivity : BaseActivity<ActivityOnBoardBinding>(ActivityOnBoardBinding::inflate) {
    override fun initAfterBinding() {

        onBoardVPadapter = OnBoardVPAdapter(this)
        //ViewPager2와 Adapter 연동
        binding.onBoardContentVp.adapter = onBoardVPadapter
        binding.onBoardContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.onBoardContentVp.offscreenPageLimit = 4

        // indicator 설정
        binding.onBoardTb.count = 4
        binding.onBoardTb.selection = 0
        binding.onBoardContentVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                if(position==3){
                    binding.signupFinBtn.visibility = View.VISIBLE
                }else{
                    binding.signupFinBtn.visibility = View.INVISIBLE
                }

                super.onPageSelected(position)
                binding.onBoardTb.selection = position

            }
        })

        binding.signupFinBtn.setOnClickListener {
            val mIntent = Intent(this, LoginActivity::class.java)
            startActivity(mIntent)
            finish()
        }
    }
}