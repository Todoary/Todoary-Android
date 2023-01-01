package com.uni.todoary.feature.main.ui.view

import android.graphics.drawable.Drawable
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
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
import androidx.core.content.ContextCompat
import androidx.core.text.toHtml
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.todoary.GetStickerView
import com.uni.todoary.R
import com.uni.todoary.SetStickerView
import com.uni.todoary.StickerService
import com.uni.todoary.databinding.ActivityDiaryBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.main.data.dto.*
import com.uni.todoary.feature.main.data.view.AddDiaryView
import com.uni.todoary.feature.main.data.view.GetDiaryView
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.sticker
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.stickerD
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.stickerM
import com.uni.todoary.sticker.*
import com.uni.todoary.util.DiaryAdapter
import com.uni.todoary.util.KeyboardVisibilityUtils
import com.uni.todoary.util.SoftKeyboardDectectorView
import com.uni.todoary.util.SoftKeyboardDectectorView.OnHiddenKeyboardListener
import com.uni.todoary.util.SoftKeyboardDectectorView.OnShownKeyboardListener
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class DiaryActivity : AppCompatActivity(), AddDiaryView, SetStickerView, GetStickerView, GetDiaryView {
    lateinit var binding: ActivityDiaryBinding
    lateinit var date: LocalDate

    //Singleton
    object StickerList {
        var sticker = ArrayList<Sticker>() //new sticker
        var stickerM = ArrayList<Sticker>() //modified sticker
        var stickerD = ArrayList<Sticker>() //deleted sticker
    }


    lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    var sposition = 0
    var eposition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        date = intent.getSerializableExtra("date") as LocalDate



        //sticker
        //BitmapStickerIcon(아이콘이미지 drawable,아이콘 위치)
        sticker.clear()
        stickerM.clear()
        stickerD.clear()

        //아래 버튼 3개 이미지 변경 필요 (수정사항)
        //스티커 삭제 버튼
        val deleteIcon= BitmapStickerIcon(
            ContextCompat.getDrawable(this, R.drawable.ic_diary_delete)
            ,BitmapStickerIcon.RIGHT_TOP)
        //스티커 뒤집기 버튼
        val flipIcon=BitmapStickerIcon(ContextCompat.getDrawable(this,com.uni.todoary.R.drawable.ic_diary_delete)
            ,BitmapStickerIcon.LEFT_TOP)
        //스티커 크기 변경 버튼
        val scaleIcon=BitmapStickerIcon(ContextCompat.getDrawable(this,com.uni.todoary.R.drawable.ic_diary_delete)
            ,BitmapStickerIcon.RIGHT_BOTOM)

        deleteIcon.iconEvent = DeleteIconEvent()
        flipIcon.iconEvent= FlipHorizontallyEvent() //현재 : 가로뒤집기
        scaleIcon.iconEvent = ZoomIconEvent()

        //만든 아이콘을 리스트로 만들어주기
        val iconList=listOf(deleteIcon,flipIcon,scaleIcon)
        binding.stickerView.icons = iconList

        //add new sticker
        //sticker 키보드 제작 후 수정필요
        //sticker에 따라 16가지 setOnClickListener 작성 (아래 버튼은 임시 : 수정필요) : 키보드 만들고 안에 넣어주면될듯
        binding.tempBtnAddSticker.setOnClickListener {
            Log.d("StickerEnter","Ok")
            ContextCompat.getDrawable(this,R.drawable.ic_diary_delete)?.let { it1 -> addSticker(it1, 1) }
        }

        //다이어리 조회
        GetDiary(date.toString())


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


    //add sticker
    private fun addSticker(drawable: Drawable, stickerId: Int) {

        val drawableSticker= DrawableSticker(drawable)
        binding.stickerView.addSticker(drawableSticker, stickerId)
        sticker.add(drawableSticker)
        Log.d("addSticker",stickerId.toString())
    }


    //set sticker (API)
    private fun setSticker(){
        val setStickerService = StickerService()
        setStickerService.setSetStickerView(this)

        //스티커 생성
        var createdSticker = ArrayList<CreatedSticker>()
        for (i in sticker){
            createdSticker.add(CreatedSticker(i.stickerId, i.mappedBound.left.toDouble(), i.mappedBound.top.toDouble(), i.currentWidth.toDouble(), i.currentHeight.toDouble(), i.currentAngle.toDouble(), i.isFlippedHorizontally))
        }

        //스티커 수정
        var modifiedSticker = ArrayList<ModifiedSticker>()
        for (i in stickerM){
            modifiedSticker.add(ModifiedSticker(i.id.toInt(), i.stickerId, i.mappedBound.left.toDouble(), i.mappedBound.top.toDouble(), i.currentWidth.toDouble(), i.currentHeight.toDouble(), i.currentAngle.toDouble(), i.isFlippedHorizontally))
        }

        //스티커 삭제
        var deletedSticker = ArrayList<Int>()
        for (i in stickerD){
            deletedSticker.add(i.id.toInt())
        }

        //api 연결
        Log.d("apiSticker", SetSticker(createdSticker, modifiedSticker, deletedSticker).toString())
        setStickerService.SetSticker(binding.diaryDateTv.text.toString(), SetSticker(createdSticker, modifiedSticker, deletedSticker))
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

        //sticker
        setSticker()
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

    //set sticker
    override fun SetStickerLoading() {

    }

    override fun SetStickerSuccess() {
        Log.d("스티커 추가","성공")
    }

    override fun SetStickerFailure(code: Int) {
        Log.d("스티커 추가", "실패")
        Log.d("Sticker_Error",code.toString())
    }

    //get sticker
    private fun getSticker(stickerList : List<GetSticker>) {
        for(i in stickerList){
            Log.d("sticker2",i.toString())
            val drawableSticker= DrawableSticker(ContextCompat.getDrawable(this, R.drawable.ic_diary_delete))
            binding.stickerView.showSticker(drawableSticker, i)
            stickerM.add(drawableSticker)
            if(i.stickerId == 1){
                val drawableSticker= DrawableSticker(ContextCompat.getDrawable(this, R.drawable.ic_diary_delete))
                binding.stickerView.showSticker(drawableSticker, i)
                DiaryActivity.StickerList.stickerM.add(drawableSticker)
            }else if(i.stickerId == 2){
                val drawableSticker= DrawableSticker(ContextCompat.getDrawable(this, R.drawable.ic_diary_delete))
                binding.stickerView.showSticker(drawableSticker, i)
                DiaryActivity.StickerList.stickerM.add(drawableSticker)
            }//... 경우의 수 모두 작성 (수정필요)

        }

    }

    //get sticker
    override fun GetStickerLoading() {

    }

    override fun GetStickerSuccess(result: List<GetSticker>?) {
        Log.d("스티커 조회","성공")
        if(result!=null){
            getSticker(result)
        }
    }

    override fun GetStickerFailure(code: Int) {
        Log.d("스티커 조회", "실패")
        Log.d("Sticker_Error",code.toString())
    }

    private fun GetDiary(createdDate:String){
        val getDiaryService = AuthService()
        getDiaryService.setGetDiaryView(this)
        getDiaryService.GetDiary(createdDate)
    }

    override fun GetDiaryLoading() {
    }

    override fun GetDiarySuccess(code: Int, result: GetDiaryRequest) {
        if(code==1000){
            //binding.diaryTitleEt.text=result.title
            binding.diaryDetailEt.setText(result.title)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //binding.diaryDetailEt.text= Html.fromHtml(result.content, Html.FROM_HTML_MODE_LEGACY).toString()
                binding.diaryDetailEt.setText(Html.fromHtml(result.content, Html.FROM_HTML_MODE_LEGACY).toString())
            } else {
                //binding.diaryDetailEt.text= Html.fromHtml(result.content).toString()
                binding.diaryDetailEt.setText(Html.fromHtml(result.content).toString())
            }
            //스티커 조회 -> 일기 조회 코드로 이동

            val getStickerService = StickerService()
            getStickerService.setGetStickerView(this)
            getStickerService.GetSticker(date.toString())
        }
    }

    override fun GetDiaryFailure(code: Int) {
        Log.d("Getdiary_error",code.toString())
    }


}