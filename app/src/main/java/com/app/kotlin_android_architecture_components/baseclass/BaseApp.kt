package com.app.kotlin_android_architecture_components.baseclass

import android.app.Application
import com.app.kotlin_android_architecture_components.utility.SharedPrefsManager

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefsManager.initialize(this)
    }
}
