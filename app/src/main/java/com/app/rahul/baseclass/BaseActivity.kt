package com.app.rahul.baseclass

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
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
import com.app.rahul.retrofit.Status
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
    private val progressDialog: Dialog by lazy {
        Dialog(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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


    /**
     *  get ViewModel when extend BaseViewModel in your ViewModel
     */
    inline fun <reified T : BaseViewModel> AppCompatActivity.getViewModel(): BaseViewModel {
        baseViewModel = ViewModelProviders.of(this)[T::class.java]
        setObserve()
        return baseViewModel as T
    }


    /**
     * set viewModel when not extend BaseViewMode in your viewModel
     */
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
        baseViewModel.getNetworkManager().apiResource.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showProgressDialog()
                }

                Status.SUCCESS -> {
                    hideProgressDialog()
                    if (it.success == FAILURE) {
                        apiResponseError(it)
                    } else {
                        apiResponse(it)
                    }
                }

                Status.ERROR -> {
                    hideProgressDialog()
                    val error = it.message.toString()
                    apiError(error)
                    if (error == SESSION_EXPIRE_MSG) {
                        SharedPrefsManager.clearPrefs()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startNewActivity(intent)
                    }
                }
            }
        })
    }


    /**
     *   this method call when status fail
     *   like success = 0  or success = false in Response
     */
    open fun apiResponseError(it: ResponseData<*>?) {
        if (!TextUtils.isEmpty(it?.message)) {
            Utils.showToast(applicationContext, it?.message.toString())
        }
    }

    /**
     *  any error for call
     */
    open fun apiError(error: String) {

    }


    /**
     *  get Response
     */
    open fun apiResponse(response: ResponseData<*>?) {

    }

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

    private fun showProgressDialog(isCancel: Boolean = false) {
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


        if (!progressDialog.isShowing) {
            progressDialog.show()
        }

    }

    private fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }


}