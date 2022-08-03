package com.uni.todoary.feature.category.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.databinding.FragmentAlarmsettingBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class SettingAlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmsettingBinding
    //lateinit var selectedDate: LocalDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmsettingBinding.inflate(inflater, container, false)
        initAllPicker()
        binding.alarmsettingCompleteBtn.setOnClickListener {

        }
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
}