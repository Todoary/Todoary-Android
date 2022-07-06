package com.uni.todoary.feature.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityTermscheckBinding
import com.uni.todoary.feature.auth.SignupActivity

class TermscheckActivity : AppCompatActivity() {
    lateinit var binding: ActivityTermscheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermscheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val backbtn = findViewById<ImageView>(R.id.termscheck_back_btn)
        val backintent = Intent(this, SignupActivity::class.java)
        val allcheck = findViewById<CheckBox>(R.id.termscheck_all_check)
        val nextbtn = findViewById<Button>(R.id.termscheck_next_btn)
        val check1 = findViewById<CheckBox>(R.id.termscheck_1_check)
        val check2 = findViewById<CheckBox>(R.id.termscheck_2_check)
        val check3 = findViewById<CheckBox>(R.id.termscheck_3_check)
        backbtn.setOnClickListener{
            startActivity(backintent)
        }
        allcheck.setOnClickListener {
            AllCheck()
        }
        nextbtn.setOnClickListener {
            if(check1.isChecked==true&&check2.isChecked==true&&check3.isChecked){
                Toast.makeText(applicationContext, "다 체크됨",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(applicationContext, "체크부터 해줘 ",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun AllCheck(){
        val allcheck = findViewById<CheckBox>(R.id.termscheck_all_check)
        val check1 = findViewById<CheckBox>(R.id.termscheck_1_check)
        val check2 = findViewById<CheckBox>(R.id.termscheck_2_check)
        val check3 = findViewById<CheckBox>(R.id.termscheck_3_check)
        if(allcheck.isChecked){
            check1.isChecked = true
            check2.isChecked = true
            check3.isChecked = true
        }

    }
}