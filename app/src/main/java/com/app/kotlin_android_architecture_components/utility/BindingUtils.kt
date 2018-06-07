package com.app.kotlin_android_architecture_components.utility

import android.databinding.BindingAdapter
import android.widget.ImageView
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by RahulSadhu on 16/5/18.
 */
object BindingUtils {

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.logo)).load(url).into(view)
    }

}
