package com.app.kotlin_android_architecture_components.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.support.graphics.drawable.VectorDrawableCompat.create
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**
 * Created by Rahul Sadhu
 */

object Utils {

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return  Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun Log(string: String) {
        android.util.Log.w("####", string)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getDrawable(context: Context, resourceId: Int): Drawable? {
        var drawable: Drawable? = null
        try {
            drawable = create(context.resources, resourceId, context.theme)
        } catch (e: Exception) {
            drawable = ContextCompat.getDrawable(context, resourceId)
        }

        return drawable
    }


    @Suppress("DEPRECATION")
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

}
