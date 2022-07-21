package com.uni.todoary.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.uni.todoary.databinding.DialogCustomBinding

class BaseDialog : DialogFragment() {
    private var _binding: DialogCustomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  DialogCustomBinding.inflate(inflater, container, false)
        val view = binding.root
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        initDialog()
        return view
    }

    fun initDialog(){
        binding.customDialogTitle.text = arguments?.getString("titleContext")
        binding.customDialogMsg.text=arguments?.getString("bodyContext")
        val btnBundle = arguments?.getStringArray("btnData")

        binding.customDialogBtn1.setOnClickListener {
            buttonClickListener. onButton1Clicked()
            dismiss()
        }
        binding.customDialogBtn1.text=btnBundle?.get(0)

        if(btnBundle?.size==1){
            binding.customDialogBtn2.visibility= View.GONE

        }else{
            binding.customDialogBtn2.setOnClickListener {
                buttonClickListener.onButton2Clicked()
                dismiss()
            }
            binding.customDialogBtn2.text=btnBundle?.get(1)
        }
    }

    interface OnButtonClickListener{
        fun onButton1Clicked()
        fun onButton2Clicked()
    }

    override fun onResume() {
        // 화면의 가로 세로 크기 가져오는 코드
        val height = resources.displayMetrics.heightPixels
        val width = resources.displayMetrics.widthPixels

        // 화면 가로의 80% 비율로 다이얼로그 width설정
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes    // 다이얼로그 창의 attributes 가져오기
        val deviceWidth = resources.displayMetrics.widthPixels * 0.8        // 80% 화면비율 크기
        params?.width = deviceWidth.toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

        super.onResume()
    }

    override fun onStart() {
        super.onStart();
        val lp : WindowManager.LayoutParams  =  WindowManager.LayoutParams()
        lp.copyFrom(dialog!!.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        val window: Window = dialog!!.window!!
        window.attributes = lp
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }
    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener
}