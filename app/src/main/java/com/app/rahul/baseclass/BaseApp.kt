package com.app.rahul.baseclass

import android.app.Application
import com.app.rahul.utility.SharedPrefsManager

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPrefsManager.initialize(this)
    }
}
