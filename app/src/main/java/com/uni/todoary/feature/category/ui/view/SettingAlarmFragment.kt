package com.uni.todoary.feature.category.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.uni.todoary.databinding.FragmentAlarmsettingBinding
import com.uni.todoary.feature.category.ui.viewmodel.TodoViewModel
import com.uni.todoary.util.alarmFormatter
import java.text.DecimalFormat


class SettingAlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmsettingBinding
    private val model : TodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmsettingBinding.inflate(inflater, container, false)
        initAllPicker()
        initSubmitBtn()
        return binding.root
    }

    fun minutePicker(min: Int,max:Int, p:NumberPicker) {
        p.minValue=min
        p.maxValue=max
        p.setFormatter{i->String.format("%02d",i)}
    }
    fun hourPicker(min: Int,max:Int, p:NumberPicker) {
        p.minValue=min
        p.maxValue=max
    }
    fun initPickerWithString(min: Int, max: Int, p: NumberPicker, str: Array<String>) {
        p.minValue = min
        p.maxValue = max
        p.displayedValues = str
    }

    fun initAllPicker() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            //binding.numPickerAm.selectionDividerHeight= 0.5.toInt()
//            binding.numPickerH.selectionDividerHeight=0.5.toInt()
//            binding.numPickerM.selectionDividerHeight=0.5.toInt()
//        }
        val str = arrayOf<String>("AM", "PM")
        hourPicker(1, 12, binding.numPickerH)
        minutePicker(0, 59, binding.numPickerM)
        initPickerWithString(0, (str.size - 1), binding.numPickerAm, str)
    }

    fun initSubmitBtn(){
        binding.alarmsettingCompleteBtn.setOnClickListener {
            val hour = binding.numPickerH.value
            val minute = binding.numPickerM.value
            val ampm = binding.numPickerAm.displayedValues[binding.numPickerAm.value]
            val timeFormat = alarmFormatter(ampm, hour, minute)
            model.setAlarmInfo(true, hour, minute)
            (parentFragment as SettingAlarmBottomSheet).dismiss()
        }
    }
}