package com.app.rahul.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import app.rahul.com.kotlinandroidarchitecturecomponent.databinding.ItemUserBinding
import com.app.rahul.baseclass.BaseViewHolder
import com.app.rahul.model.UserModel
import java.util.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.HomeItemHolder>() {

    private var data: ArrayList<UserModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemHolder {
        return HomeItemHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        val itemBinding: ItemUserBinding = holder.getBinding() as ItemUserBinding
        itemBinding.userModel = data[position]
    }

    fun updateList(list: ArrayList<UserModel>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[newItemPosition] == data[oldItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return list[newItemPosition].userid == data[oldItemPosition].userid
            }

            override fun getOldListSize(): Int = data.size

            override fun getNewListSize(): Int = list.size
        })
        this.data.clear()
        this.data.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class HomeItemHolder(view: View) : BaseViewHolder(view)
}