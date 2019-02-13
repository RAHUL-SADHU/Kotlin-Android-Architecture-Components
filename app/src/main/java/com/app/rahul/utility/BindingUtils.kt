package com.app.rahul.utility

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by RahulSadhu on 16/5/18.
 */


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context).setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.logo)).load(url).into(view)
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("htmlText")
fun htmlText(textView: TextView, text: String?) {
    textView.text = text?.let { Utils.fromHtml(it) }

}


