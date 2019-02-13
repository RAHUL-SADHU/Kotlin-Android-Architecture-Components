package com.app.rahul.baseclass

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.interfaces.AppCompactImplMethod
import com.app.rahul.utility.Utils

/**
 * Created by Rahul Sadhu on 6/6/18.
 */
abstract class BaseActivity : AppCompatActivity(), AppCompactImplMethod {
    private lateinit var binding: ViewDataBinding

    private val progressDialog: Dialog by lazy {
        Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_progressbar)
            val layoutParams = window?.attributes
            layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            window?.attributes = layoutParams
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            /* setOnDismissListener {
                      baseViewModel.getNetworkManager().cancelCall()
                      hideProgressDialog()
             }*/
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
    }


    protected fun setView(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutResId)
        try {
            initVariable()
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.showToast(this@BaseActivity, e.toString())
        }
    }


    inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(): ViewModel {
        return ViewModelProviders.of(this)[T::class.java]
    }

    /* fun setActionBar(action_bar: View, title: String?, isBack: Boolean) {
         action_bar.txt_title.text = title
         if (!isBack) {
             action_bar.img_back.visibility = View.INVISIBLE
         }
         action_bar.img_back.setOnClickListener { onBackPressed() }
     }*/

    protected fun getBinding(): ViewDataBinding {
        return binding
    }

    fun startNewActivity(intent: Intent) {
        //  Utils.hideKeyboard(BaseActivity.this);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        // Utils.hideKeyboard(BaseActivity.this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    public fun showProgressDialog(isCancel: Boolean = false) {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }

    }

    public fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }


}