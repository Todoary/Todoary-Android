package com.uni.todoary.feature.main.ui.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.todoary.GetStickerView
import com.uni.todoary.R
import com.uni.todoary.SetStickerView
import com.uni.todoary.StickerService
import com.uni.todoary.databinding.ActivityDiaryBinding
import com.uni.todoary.feature.auth.data.service.AuthService
import com.uni.todoary.feature.main.data.dto.*
import com.uni.todoary.feature.main.data.view.AddDiaryView
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.sticker
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.stickerD
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.stickerM
import com.uni.todoary.sticker.*
import com.uni.todoary.util.DiaryAdapter
import com.uni.todoary.util.SoftKeyboardDectectorView
import com.uni.todoary.util.SoftKeyboardDectectorView.OnHiddenKeyboardListener
import com.uni.todoary.util.SoftKeyboardDectectorView.OnShownKeyboardListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DiaryActivity : AppCompatActivity(), AddDiaryView, SetStickerView, GetStickerView {
    lateinit var binding: ActivityDiaryBinding

    //Singleton
    object StickerList {
        var sticker = ArrayList<Sticker>() //new sticker
        var stickerM = ArrayList<Sticker>() //modified sticker
        var stickerD = ArrayList<Sticker>() //deleted sticker
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.diaryKeytoolbarLl.visibility= View.INVISIBLE

        //리사이클러뷰 연결
        var recyclerView = binding.diaryRecyclerRv

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        var adapter = DiaryAdapter()
        recyclerView.adapter=adapter


        binding.diaryToolbar.toolbarTextIv.setOnClickListener {
            finish()
        }
        binding.diaryToolbar.toolbarTextTv.setOnClickListener {
            AddDiary()
        }


        //날짜 띄우기
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
        val strDate = dateFormat.format(date)

        binding.diaryDateTv.text=strDate

        //키보드 감지

        val softKeyboardDecector = SoftKeyboardDectectorView(this)
        addContentView(softKeyboardDecector, FrameLayout.LayoutParams(-1, -1))

        softKeyboardDecector.setOnShownKeyboard(object : OnShownKeyboardListener {
            override fun onShowSoftKeyboard() {
                binding.diaryKeytoolbarLl.visibility= View.VISIBLE
            }
        })

        softKeyboardDecector.setOnHiddenKeyboard(object : OnHiddenKeyboardListener {
            override fun onHiddenSoftKeyboard() {
                binding.diaryKeytoolbarLl.visibility= View.INVISIBLE
            }
        })

        //스티커 조회 -> 일기 조회 코드로 이동
        val getStickerService = StickerService()
        getStickerService.setGetStickerView(this)
        getStickerService.GetSticker(strDate)

        //sticker
        //BitmapStickerIcon(아이콘이미지 drawable,아이콘 위치)
        //수정사항 : image 모두 변경
        sticker.clear()
        stickerM.clear()
        stickerD.clear()

        val deleteIcon= BitmapStickerIcon(
            ContextCompat.getDrawable(this, R.drawable.ic_delete)
            ,BitmapStickerIcon.RIGHT_TOP)
        val flipIcon=BitmapStickerIcon(ContextCompat.getDrawable(this,com.uni.todoary.R.drawable.ic_delete)
            ,BitmapStickerIcon.LEFT_TOP)
        val scaleIcon=BitmapStickerIcon(ContextCompat.getDrawable(this,com.uni.todoary.R.drawable.ic_delete)
            ,BitmapStickerIcon.RIGHT_BOTOM)

        deleteIcon.iconEvent = DeleteIconEvent()
        flipIcon.iconEvent= FlipHorizontallyEvent() //현재 : 가로뒤집기
        scaleIcon.iconEvent = ZoomIconEvent()

        //만든 아이콘을 리스트로 만들어주기
        val iconList=listOf(deleteIcon,flipIcon,scaleIcon)
        binding.stickerView.icons = iconList

        //add new sticker
        //sticker 키보드 제작 후 수정필요
        //sticker에 따라 16가지 setOnClickListener 작성
        binding.tempBtnAddSticker.setOnClickListener {
            ContextCompat.getDrawable(this,R.drawable.ic_delete)?.let { it1 -> addSticker(it1, 0) }
        }

    }

    //get sticker
    private fun getSticker(stickerList : List<GetSticker>) {
        for(i in stickerList){
            if(i.stickerId == 1){
                val drawableSticker= DrawableSticker(ContextCompat.getDrawable(this, R.drawable.ic_delete))
                binding.stickerView.showSticker(drawableSticker, i)
                stickerM.add(drawableSticker)
            }else if(i.stickerId == 2){
                val drawableSticker= DrawableSticker(ContextCompat.getDrawable(this, R.drawable.ic_delete))
                binding.stickerView.showSticker(drawableSticker, i)
                stickerM.add(drawableSticker)
            } //... 경우의 수 모두 작성 (수정필요)

        }

    }

    //add sticker
    private fun addSticker(drawable: Drawable, stickerId: Int) {
        val drawableSticker= DrawableSticker(drawable)
        binding.stickerView.addSticker(drawableSticker, stickerId)
        sticker.add(drawableSticker)
        Log.d("sticker", sticker.toString())
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
            modifiedSticker.add(ModifiedSticker(i.id.toInt(), i.mappedBound.left.toDouble(), i.mappedBound.top.toDouble(), i.currentWidth.toDouble(), i.currentHeight.toDouble(), i.currentAngle.toDouble(), i.isFlippedHorizontally))
        }

        //스티커 삭제
        var deletedSticker = ArrayList<DeletedSticker>()
        for (i in stickerD){
            deletedSticker.add(DeletedSticker(i.id.toInt()))
        }

        //api 연결
        setStickerService.SetSticker(binding.diaryDateTv.text.toString(), SetSticker(createdSticker, null, null))
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

            //sticker
            setSticker()
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
}