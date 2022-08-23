package com.uni.todoary.feature.main.ui.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpanWatcher
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.FrameLayout
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityDiaryBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.view.AddDiaryView
import com.uni.todoary.util.DiaryAdapter
import com.uni.todoary.util.SoftKeyboardDectectorView
import com.uni.todoary.util.SoftKeyboardDectectorView.OnHiddenKeyboardListener
import com.uni.todoary.util.SoftKeyboardDectectorView.OnShownKeyboardListener
import java.text.SimpleDateFormat
import java.util.*


class DiaryActivity : AppCompatActivity(), AddDiaryView {
    lateinit var binding: ActivityDiaryBinding
    var sposition = 0
    var eposition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.diaryKeytoolbarLl.visibility= View.GONE

        //리사이클러뷰 연결
        var recyclerView = binding.diaryRecyclerRv

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        var adapter = DiaryAdapter()
        recyclerView.adapter=adapter


        binding.diaryBackIv.setOnClickListener {
            finish()
        }
        binding.diaryCompleteTv.setOnClickListener {
            AddDiary()
        }



        //날짜 띄우기
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
        val strDate = dateFormat.format(date)

        binding.diaryDateTv.text=strDate

        //키보드 감지

        binding.diaryTitleEt.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                binding.diaryKeytoolbarLl.visibility = View.GONE
                binding.diaryKeytoolbar2Ll.visibility = View.GONE
                binding.diaryKeytoolbar3Ll.visibility = View.GONE
                binding.diaryKeytoolbar4Ll.visibility=View.GONE
            }
        }

        val softKeyboardDecector = SoftKeyboardDectectorView(this)
        addContentView(softKeyboardDecector, FrameLayout.LayoutParams(-1, -1))
        softKeyboardDecector.setOnShownKeyboard(object : OnShownKeyboardListener {
            override fun onShowSoftKeyboard() {
                binding.diaryDetailEt.setOnFocusChangeListener { v, hasFocus ->
                    if(hasFocus) {
                        binding.diaryKeytoolbarLl.visibility = View.VISIBLE
                        binding.keyboardTypeIv.setOnClickListener {
                            showFont()
                        }
                        binding.keyboardEditIv.setOnClickListener {
                            showHighlight()
                        }
                    }
                }

            }
        })

        softKeyboardDecector.setOnHiddenKeyboard(object : OnHiddenKeyboardListener {
            override fun onHiddenSoftKeyboard() {
                binding.diaryKeytoolbarLl.visibility = View.GONE
                binding.diaryKeytoolbar2Ll.visibility= View.GONE
                binding.diaryKeytoolbar3Ll.visibility=View.GONE
                binding.diaryKeytoolbar4Ll.visibility=View.GONE
            }
        })


//        binding.diaryDetailEt.setOnFocusChangeListener { v, hasFocus ->
//            if(hasFocus){
//                softKeyboardDecector.setOnShownKeyboard(object : OnShownKeyboardListener{
//                    override fun onShowSoftKeyboard() {
//                        binding.diaryKeytoolbarLl.visibility = View.VISIBLE
//                        binding.keyboardTypeIv.setOnClickListener {
//                            showFont()
//                        }
//                        binding.keyboardEditIv.setOnClickListener {
//                            showHighlight()
//                        }
//                    }
//                })
//            }
//        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showHighlight() {
        binding.diaryKeytoolbar4Ll.visibility=View.VISIBLE
        detect()
    }

    private fun showFont() {
        binding.diaryKeytoolbar2Ll.visibility=View.VISIBLE
        var flagsT=false
        var flagsU=false
        var flagsB=false
        //기능 구현
        binding.keyboardLeftalignIv.setOnClickListener {
            binding.diaryDetailEt.gravity=Gravity.LEFT
        }
        binding.keyboardCenteralignIv.setOnClickListener {
            binding.diaryDetailEt.gravity=Gravity.CENTER_HORIZONTAL
        }
        binding.keyboardRightalignIv.setOnClickListener {
            binding.diaryDetailEt.gravity=Gravity.RIGHT
        }
        binding.keyboardTIv.setOnClickListener {
            flagsT=!flagsT
            if(flagsT)
                binding.diaryDetailEt.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            else
                binding.diaryDetailEt.setPaintFlags(binding.diaryDetailEt.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
        }

        //Tfunction()
        binding.keyboardUIv.setOnClickListener {
            flagsU=!flagsU
            if (flagsU)
                binding.diaryDetailEt.paintFlags=Paint.UNDERLINE_TEXT_FLAG
            else
                binding.diaryDetailEt.setPaintFlags(binding.diaryDetailEt.getPaintFlags() and Paint.UNDERLINE_TEXT_FLAG.inv())
        }
        binding.keyboardBIv.setOnClickListener {
            flagsB=!flagsB
            if(flagsB)
                binding.diaryDetailEt.setTypeface(null,Typeface.BOLD)
            else
                binding.diaryDetailEt.setTypeface(null, Typeface.BOLD.inv())
        }

        //초기화
        binding.keyboardXIv.setOnClickListener {
            binding.diaryDetailEt.typeface=resources.getFont(R.font.applesdgothicneom)
            binding.diaryDetailEt.setTextSize(Dimension.SP,14F)
            binding.diaryDetailEt.gravity=Gravity.LEFT
            binding.diaryDetailEt.setPaintFlags(binding.diaryDetailEt.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            binding.diaryDetailEt.setPaintFlags(binding.diaryDetailEt.getPaintFlags() and Paint.UNDERLINE_TEXT_FLAG.inv())
            binding.diaryDetailEt.setTypeface(null, Typeface.BOLD.inv())
        }

        //폰트변경 기능
        binding.keyboardFontIv.setOnClickListener {
            binding.diaryKeytoolbar2Ll.visibility=View.GONE
            binding.diaryKeytoolbar3Ll.visibility=View.VISIBLE
            binding.diaryKeytoolbar4Ll.visibility=View.GONE

            binding.keyboardFont1Iv.setOnClickListener {
                binding.diaryDetailEt.typeface=resources.getFont(R.font.applesdgothicneom)
                binding.diaryDetailEt.setTextSize(Dimension.SP,14F)
            }
            binding.keyboardFont2Iv.setOnClickListener {
                binding.diaryDetailEt.typeface=resources.getFont(R.font.ibmplexsanskrregular)
                binding.diaryDetailEt.setTextSize(Dimension.SP,14F)
            }
            binding.keyboardFont3Iv.setOnClickListener {
                binding.diaryDetailEt.typeface=resources.getFont(R.font.notoserifkrregular)
                binding.diaryDetailEt.setTextSize(Dimension.SP,14F)
            }
            binding.keyboardFont4Iv.setOnClickListener {
                binding.diaryDetailEt.typeface=resources.getFont(R.font.imhyeminregular)
                binding.diaryDetailEt.setTextSize(Dimension.SP,14F)
            }
            binding.keyboardFont5Iv.setOnClickListener {
                binding.diaryDetailEt.typeface=resources.getFont(R.font.gangwoneduall)
                binding.diaryDetailEt.setTextSize(Dimension.SP,14F)
            }
        }
    }

    private fun AddDiary() {
        val addDiaryService = AuthService()
        addDiaryService.setAddDiaryView(this)
        if (binding.diaryTitleEt.text.toString() != null && binding.diaryDetailEt.text.toString()!=null){
            val createdDate = binding.diaryDateTv.text.toString()
            val title = binding.diaryTitleEt.text.toString()
            val content = binding.diaryDetailEt.text.toString()
            val request = AddDiaryRequest(createdDate,title,content)
            addDiaryService.AddDiary(createdDate,request)
        }
    }

    override fun AddDiaryLoading() {
    }

    override fun AddDiarySuccess() {
        Log.d("다이어리 추가","성공")
    }


    override fun AddDiaryFailure(code: Int) {
        Log.d("다이어리 추가", "실패")
        Log.d("Diary_Error",code.toString())
    }

    @SuppressLint("ResourceAsColor")
    private fun detect(){
        binding.diaryDetailEt.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun sendAccessibilityEvent(host: View?, eventType: Int) {
                super.sendAccessibilityEvent(host, eventType)
                if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED){
                    Log.d("highposition_Start",binding.diaryDetailEt.selectionStart.toString())
                    Log.d("highposition_End",binding.diaryDetailEt.selectionEnd.toString())
                    sposition=binding.diaryDetailEt.selectionStart
                    eposition=binding.diaryDetailEt.selectionEnd

                }

            }
        })

        binding.keyboardHighlight1Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            ssb.apply {
                setSpan(BackgroundColorSpan(Color.RED), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight2Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            ssb.apply {
                setSpan(BackgroundColorSpan(Color.RED), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight3Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            ssb.apply {
                setSpan(BackgroundColorSpan(R.color.highlight_03), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight4Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            ssb.apply {
                setSpan(BackgroundColorSpan(R.color.highlight_04), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight5Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            ssb.apply {
                setSpan(BackgroundColorSpan(R.color.highlight_05), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight6Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            ssb.apply {
                setSpan(Color.RED, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            }
//            ssb.apply {
//                setSpan(BackgroundColorSpan(R.color.highlight_06), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            }
            binding.diaryDetailEt.text = ssb
        }
    }
    private fun Tfunction(){
        binding.diaryDetailEt.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun sendAccessibilityEvent(host: View?, eventType: Int) {
                super.sendAccessibilityEvent(host, eventType)
                if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED){
                    Log.d("Thighposition_Start",binding.diaryDetailEt.selectionStart.toString())
                    Log.d("Thighposition_End",binding.diaryDetailEt.selectionEnd.toString())
                    sposition=binding.diaryDetailEt.selectionStart
                    eposition=binding.diaryDetailEt.selectionEnd
                }
            }
        })
        var text1 = binding.diaryDetailEt.text
        val ssb1 = SpannableStringBuilder(text1)
        binding.keyboardTIv.setOnClickListener {
            //ssb.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            ssb1.setSpan(StrikethroughSpan(), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.diaryDetailEt.text = ssb1
    }
}



