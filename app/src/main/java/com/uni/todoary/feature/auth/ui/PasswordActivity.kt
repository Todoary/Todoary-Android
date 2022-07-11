package com.uni.todoary.feature.auth.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.uni.todoary.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity(){
    lateinit var binding: ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
