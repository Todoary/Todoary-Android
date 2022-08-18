package com.uni.todoary.feature.category.ui.view

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uni.todoary.R
import com.uni.todoary.databinding.ItemCategoryBinding
import com.uni.todoary.feature.category.data.dto.CategoryData

class CategoryRVAdapter(private val context: Context):  RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>() {
    private var categoryDataset = arrayListOf<CategoryData>()
    val categoryColors  = context.resources.obtainTypedArray(R.array.category_array)
    lateinit var mItemSelectedListener : ItemSelectedListener
    var flag = false
    interface ItemSelectedListener{
        fun categorySelectedCallback(categoryIdx : Long)
    }

    fun setItemSelectedListener(listener : ItemSelectedListener){
        this.mItemSelectedListener = listener
    }

    // 각 항목에 필요한 기능을 구현
    inner class ViewHolder(val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun initView(position : Int, initFlag : Boolean){
            // 인덱스에 해당하는 색으로 변경
            val color = categoryColors.getColor(categoryDataset[position].color, 0)
            binding.itemCategoryTv.apply{
                text = categoryDataset[position].title
                setTextColor(color)
            }
            binding.itemCategoryCv.apply{
                strokeColor = color
                setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
            if (initFlag && position == 0){
                // 처음에 0번 카테고리로 선택되도록 초기화
                binding.itemCategoryCv.setCardBackgroundColor(color)
                binding.itemCategoryTv.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

        fun selectView(position : Int){
            val color = categoryColors.getColor(categoryDataset[position].color, 0)
            binding.itemCategoryCv.setCardBackgroundColor(color)
            binding.itemCategoryTv.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initView(position, true)
        holder.itemView.setOnClickListener {
            notifyItemRangeChanged(0, itemCount, "initCategories")
            notifyItemChanged(position, "setCategory")
            // 콜백함수로 카테고리 인덱스 전달
            mItemSelectedListener.categorySelectedCallback(categoryDataset[position].id)
        }

        //롱터치 리스너
        holder.itemView.setOnLongClickListener {
            val mIntent = Intent(holder.itemView.context,TodoSettingActivity::class.java)
            flag = true
            mIntent.putExtra("flag",flag)
            mIntent.putExtra("cateData",categoryDataset[position])
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(holder.itemView.context,mIntent,null)
            return@setOnLongClickListener(true)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (payload in payloads){
                if (payload is String){
                    val type = payload.toString()
                    if(TextUtils.equals(type, "initCategories")){
                        holder.initView(position, false)
                    }
                    if (TextUtils.equals(type, "setCategory")){
                        holder.selectView(position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryDataset.size
    }

    fun setList(dataset : ArrayList<CategoryData>){
        this.categoryDataset.clear()
        this.categoryDataset = dataset
        notifyDataSetChanged()
    }

}