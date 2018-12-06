package com.app.rahul.baseclass

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.interfaces.AppCompactImplMethod
import com.app.rahul.model.ResponseData
import com.app.rahul.utility.FAILURE
import com.app.rahul.utility.SESSION_EXPIRE_MSG
import com.app.rahul.utility.SharedPrefsManager
import com.app.rahul.utility.Utils
import com.app.rahul.view.LoginActivity

/**
 * Created by Rahul Sadhu on 6/6/18.
 */
abstract class BaseActivity : AppCompatActivity(), AppCompactImplMethod {
    lateinit var baseViewModel: BaseViewModel
    lateinit var viewModel: ViewModel
    private lateinit var binding: ViewDataBinding
    private lateinit var progressDialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            disableAutoFill()
        }
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


    // set ViewModel when BaseViewModel use
    inline fun <reified T : com.app.rahul.baseclass.BaseViewModel> AppCompatActivity.getViewModel(): com.app.rahul.baseclass.BaseViewModel {
        baseViewModel = ViewModelProviders.of(this)[T::class.java]
        setObserve()
        return baseViewModel as T
    }

    // set viewModel when BaseViewModel not use
    inline fun <reified T : ViewModel> AppCompatActivity.setViewModel(): ViewModel {
        viewModel = ViewModelProviders.of(this)[T::class.java]
        return viewModel
    }

    /* fun setActionBar(action_bar: View, title: String?, isBack: Boolean) {
         action_bar.txt_title.text = title
         if (!isBack) {
             action_bar.img_back.visibility = View.INVISIBLE
         }
         action_bar.img_back.setOnClickListener { onBackPressed() }
     }*/


    fun setObserve() {
        baseViewModel.loading.observe(this, Observer { loading ->
            if (loading) showProgressDialog() else hideProgressDialog()
        })

        baseViewModel.apiResponse.observe(this, Observer {
            hideProgressDialog()
            if (it.status == FAILURE) {
                apiResponseError(it)
            } else {
                apiResponse(it)

            }

        })

        baseViewModel.apiError.observe(this, Observer { error ->
            baseViewModel.setLoading(false)
            apiError(error)
            if (!TextUtils.isEmpty(error)) {
                Utils.showToast(applicationContext, error)
                if (error == SESSION_EXPIRE_MSG) {
                    SharedPrefsManager.clearPrefs()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startNewActivity(intent)
                }
            }

        })

    }

    open fun apiResponseError(it: ResponseData<*>?) {
        if (!TextUtils.isEmpty(it?.message)) {
            Utils.showToast(applicationContext, it?.message.toString())
        }
    }

    open fun apiError(error: String) {
        // when error call this method use in Activity
    }

    open fun apiResponse(response: ResponseData<*>) {
        // when response call this method use in Activity
    }

    protected fun getBinding(): ViewDataBinding {
        return binding
    }

    fun startNewActivity(intent: Intent) {
        //  Utils.hideKeyboard(BaseActivity.this);
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }


    override fun onDestroy() {
        super.onDestroy()
        // remove observer for BaseViewModel
        if (::baseViewModel.isInitialized && baseViewModel.apiResponse.hasObservers()) {
            baseViewModel.apiResponse.removeObserver(baseViewModel.responseDataObserver)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Utils.hideKeyboard(BaseActivity.this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun showProgressDialog(isCancel: Boolean = false) {
        if (!::progressDialog.isInitialized) {
            progressDialog = Dialog(this)
            progressDialog.apply {
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

        if (::progressDialog.isInitialized && !progressDialog.isShowing) {
            progressDialog.show()
        }

    }

    private fun hideProgressDialog() {
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun disableAutoFill() {
        window.decorView.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
    }


}