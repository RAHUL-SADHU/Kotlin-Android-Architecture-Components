package app.rahul.com.kotlinandroidarchitecturecomponent.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.utility.GlobalKeys
import com.app.rahul.utility.SharedPrefsManager
import com.app.rahul.utility.UtilConstants
import com.app.rahul.view.LoginActivity

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
        if (!SharedPrefsManager.containsKey(GlobalKeys.KEY_X_API)) {
            SharedPrefsManager.setString(GlobalKeys.KEY_X_API, UtilConstants.X_API_KEY)
        }

        myRunnable = Runnable {
            if (SharedPrefsManager.containsKey(GlobalKeys.KEY_USER_ID)) {
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


