package com.uni.todoary.sticker

import android.util.Log
import android.view.MotionEvent
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.sticker
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.stickerD
import com.uni.todoary.feature.main.ui.view.DiaryActivity.StickerList.stickerM

class DeleteIconEvent() : StickerIconEvent {

    override fun onActionDown(stickerView: StickerView, event: MotionEvent?) {

    }

    override fun onActionMove(stickerView: StickerView, event: MotionEvent?) {

    }

    override fun onActionUp(stickerView: StickerView, event: MotionEvent?) {

        if(stickerView.currentSticker?.isNew == true){
            //새로 생성된 스티커 배열에서 삭제
            sticker.remove(stickerView.currentSticker)
            Log.d("sticker", sticker.toString())
        }else{
            //기존 스티커 수정 배열에서 삭제
            stickerM.remove(stickerView.currentSticker)
            //기존 스티커 삭제 배열에 추가
            stickerView.currentSticker?.let { stickerD.add(it) }
        }

        //화면에서 스티커 삭제
        stickerView.removeCurrentSticker()

    }

}