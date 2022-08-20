package com.uni.todoary.feature.category.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uni.todoary.R
import com.uni.todoary.feature.category.ui.viewmodel.TodoViewModel

class SettingAlarmBottomSheet() : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottomsheet_alarm_setting, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val settingAlarmFragment = SettingAlarmFragment()
        super.onActivityCreated(savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.bottomsheet_frame_fl, settingAlarmFragment)
            .commit()
    }
}