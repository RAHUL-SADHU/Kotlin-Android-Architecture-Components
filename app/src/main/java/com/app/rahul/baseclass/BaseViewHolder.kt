package com.app.rahul.baseclass

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Rahul Sadhu
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var binding: ViewDataBinding? = null

    init {
        setBinding(itemView)
    }

    fun getBinding(): ViewDataBinding? {
        return binding
    }

    private fun setBinding(itemView: View) {
        binding = DataBindingUtil.bind(itemView)
    }
}