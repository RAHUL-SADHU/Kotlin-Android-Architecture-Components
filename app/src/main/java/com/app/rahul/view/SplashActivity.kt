package com.app.rahul.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.utility.KEY_USER_ID
import com.app.rahul.utility.KEY_X_API
import com.app.rahul.utility.SharedPrefsManager
import com.app.rahul.utility.X_API_KEY

class SplashActivity : com.app.rahul.baseclass.BaseActivity() {
    private var myHandler: Handler? = null
    private lateinit var myRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_splash)
    }

    override fun initVariable() {
        myHandler = Handler()
    }

    override fun loadData() {
        if (!SharedPrefsManager.containsKey(KEY_X_API)) {
            SharedPrefsManager.setString(KEY_X_API, X_API_KEY)
        }

        myRunnable = Runnable {
            if (SharedPrefsManager.containsKey(KEY_USER_ID)) {
                startHomeActivity()
            } else {
                startLoginActivity()
            }
        }
        myHandler?.postDelayed(myRunnable, 2000)

    }

    override fun onStop() {
        super.onStop()
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        myHandler?.removeCallbacks(myRunnable)

    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startNewActivity(intent)
    }

    private fun startHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startNewActivity(intent)
    }

}


