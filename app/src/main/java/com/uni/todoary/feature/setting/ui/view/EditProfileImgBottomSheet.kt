package com.uni.todoary.feature.setting.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uni.todoary.R
import com.uni.todoary.databinding.BottomsheetEditProfileSettingBinding

class EditProfileImgBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetEditProfileSettingBinding

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
        binding = BottomsheetEditProfileSettingBinding.inflate(inflater, container, false)
        binding.profileEditGalleryTv.setOnClickListener {
            (activity as ProfileEditActivity).editpic()
            dismiss()
        }
        binding.profileEditDeleteTv.setOnClickListener {
            (activity as ProfileEditActivity).deletepic()
            dismiss()

        }
        return binding.root
    }
}