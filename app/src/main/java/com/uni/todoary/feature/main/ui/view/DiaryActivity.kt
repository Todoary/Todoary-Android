package com.uni.todoary.feature.main.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.Dimension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toHtml
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.todoary.R
import com.uni.todoary.databinding.ActivityDiaryBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.main.data.dto.AddDiaryRequest
import com.uni.todoary.feature.main.data.view.AddDiaryView
import com.uni.todoary.util.DiaryAdapter
import com.uni.todoary.util.KeyboardVisibilityUtils
import com.uni.todoary.util.SoftKeyboardDectectorView
import com.uni.todoary.util.SoftKeyboardDectectorView.OnHiddenKeyboardListener
import com.uni.todoary.util.SoftKeyboardDectectorView.OnShownKeyboardListener
import java.time.LocalDate
import java.util.*


class DiaryActivity : AppCompatActivity(), AddDiaryView {
    lateinit var binding: ActivityDiaryBinding
    lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    var sposition = 0
    var eposition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //키보드 실행시 화면 스크롤
        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
        onShowKeyboard = {keyboardHeight ->
            binding.diaryScrollviewSv.run {
                smoothScrollTo(scrollX,scrollY+keyboardHeight)
            }

        })

        //binding.diaryKeytoolbarLl.visibility= View.GONE

        binding.diaryToolbarTb.toolbarBackIv.setOnClickListener {
            finish()
        }

        binding.diaryToolbarTb.toolbarBackMainTv.text=""
        binding.diaryRegisterTv.setOnClickListener {
            AddDiary()
        }

        //리사이클러뷰 연결
        var recyclerView = binding.diaryRecyclerRv

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        var adapter = DiaryAdapter()
        recyclerView.adapter=adapter


        //날짜 띄우기
        val date = intent.getSerializableExtra("date") as LocalDate

        binding.diaryDateTv.text=date.toString()

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
                if(binding.diaryDetailEt.hasFocus()){
                    binding.diaryKeytoolbarLl.visibility=View.VISIBLE
                }
                //binding.diaryKeytoolbarLl.visibility=View.VISIBLE
                Log.d("키보드 활성화","O")
                binding.diaryTitleEt.setOnClickListener {
                    Log.d("타이틀","누름2")
                    binding.diaryKeytoolbarLl.visibility=View.GONE
                }
                binding.diaryTitleEt.setOnFocusChangeListener { v, hasFocus ->
                    if (hasFocus){
                        binding.diaryKeytoolbarLl.visibility=View.GONE
                    } else{
                        binding.diaryKeytoolbarLl.visibility=View.VISIBLE
                    }
                }
                binding.keyboardTypeIv.setOnClickListener {
                    showFont()
                }
                binding.keyboardEditIv.setOnClickListener {
                    showHighlight()
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

        binding.keyboardXIv.setOnClickListener {
            Log.d("x실행","완")
            hideKeyboard()
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun showHighlight() {
        binding.diaryKeytoolbar2Ll.visibility=View.GONE
        binding.diaryKeytoolbar3Ll.visibility=View.GONE
        binding.diaryKeytoolbar4Ll.visibility=View.VISIBLE
        detect()
    }

    private fun showFont() {
        var flagsT = false
        var flagsU=false
        var flagsB = false

        binding.diaryKeytoolbar4Ll.visibility=View.GONE
        binding.diaryKeytoolbar3Ll.visibility=View.GONE
        binding.diaryKeytoolbar2Ll.visibility=View.VISIBLE

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

            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val tspans = ssb.getSpans(0, ssb.length, StrikethroughSpan::class.java)
            flagsT != flagsT
            if(flagsT){
                ssb.apply {
                    ssb.setSpan(StrikethroughSpan(), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }else{
                for (ts in tspans) {
                    ssb.removeSpan(ts)
                }
                Log.d("strikeSpan","삭제중")
            }
            binding.diaryDetailEt.text = ssb

        }

        binding.keyboardUIv.setOnClickListener {
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

            val text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val uspans = ssb.getSpans(0, ssb.length, UnderlineSpan::class.java)
            flagsU=!flagsU
            if (flagsU) {
                ssb.apply {
                    ssb.setSpan(UnderlineSpan(), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            } else{
                for (us in uspans) {
                    ssb.removeSpan(us)
                }
                Log.d("underlineSpan","삭제중")
            }

            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardBIv.setOnClickListener {
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
            val text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val allSpans:Array<StyleSpan> = ssb.getSpans(0,ssb.length,StyleSpan::class.java)
            flagsB=!flagsB
            if(flagsB){
                ssb.apply {
                    ssb.setSpan(StyleSpan(Typeface.BOLD), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            } else {
                Log.d("bold", "삭제중")
                for(span in allSpans)
                    if(span.style == Typeface.BOLD){
                        ssb.apply {
                            ssb.removeSpan(span)
                    }
                }
            }
            binding.diaryDetailEt.text = ssb
            Log.d("Bold",flagsB.toString())
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


    //////다이어리 생성//////
    private fun AddDiary() {
        val addDiaryService = AuthService()
        addDiaryService.setAddDiaryView(this)
        if (binding.diaryTitleEt.text.toString() != null && binding.diaryDetailEt.text.toString()!=null){
            val createdDate = binding.diaryDateTv.text.toString()
            val title = binding.diaryTitleEt.text.toString()
            val content = binding.diaryDetailEt.text.toHtml()
            Log.d("html로 변환",content)
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
            val color = resources.getColor(R.color.highlight_01)
            val bcs = BackgroundColorSpan(color)
            ssb.apply {
                setSpan(bcs, sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }

        binding.keyboardHighlight2Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val color = resources.getColor(R.color.highlight_02)
            val bcs = BackgroundColorSpan(color)
            ssb.apply {
                setSpan(bcs, sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight3Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val color = resources.getColor(R.color.highlight_03)
            val bcs = BackgroundColorSpan(color)
            ssb.apply {
                setSpan(bcs, sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight4Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val color = resources.getColor(R.color.highlight_04)
            val bcs = BackgroundColorSpan(color)
            ssb.apply {
                setSpan(bcs, sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight5Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val color = resources.getColor(R.color.highlight_05)
            val bcs = BackgroundColorSpan(color)
            ssb.apply {
                setSpan(bcs, sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
        binding.keyboardHighlight6Iv.setOnClickListener {
            var text = binding.diaryDetailEt.text
            val ssb = SpannableStringBuilder(text)
            val color = resources.getColor(R.color.highlight_06)
            val bcs = BackgroundColorSpan(color)
            ssb.apply {
                ssb.setSpan(bcs, sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                //ssb.setSpan(StrikethroughSpan(), sposition, eposition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.diaryDetailEt.text = ssb
        }
    }


    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.diaryDetailEt.windowToken, 0)
    }

}



