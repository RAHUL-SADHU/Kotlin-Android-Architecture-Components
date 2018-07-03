package com.app.rahul.baseclass

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.Window
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.utility.Utils

/**
 * Created by Rahul Sadhu on 6/6/18.
 */
abstract class BaseActivity : AppCompatActivity(), com.app.rahul.interfaces.AppCompactImplMethod {
    var baseViewModel: com.app.rahul.baseclass.BaseViewModel? = null
    lateinit var viewModel: ViewModel
    private lateinit var binding: ViewDataBinding
    private var progressBarDialog: Dialog? = null

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


    fun setObserve() {
        baseViewModel?.loading?.observe(this, Observer { loading ->
            if (loading!!) showProgressDialog() else hideProgressDialog()
        })

        baseViewModel?.apiError?.observe(this, Observer { error ->
            baseViewModel!!.setLoading(false)
            apiError(error!!)
            Utils.showToast(applicationContext, error)
        })

    }

    open fun apiError(error: String) {
        // when error method use in Activity
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
        if (baseViewModel!=null && baseViewModel!!.apiResponse.hasObservers()) {
            baseViewModel?.apiResponse?.removeObserver(baseViewModel!!.responseDataObserver)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Utils.hideKeyboard(BaseActivity.this);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun showProgressDialog() {
        if (progressBarDialog == null) {
            progressBarDialog = Dialog(this)
            progressBarDialog?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_progressbar)
                val layoutParams = window.attributes
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                window.attributes = layoutParams
                window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            }

        }


        if (!progressBarDialog!!.isShowing) {
            progressBarDialog!!.show()
        }

    }

    fun hideProgressDialog() {
        if (progressBarDialog != null && progressBarDialog!!.isShowing) {
            progressBarDialog!!.dismiss()
        }
    }


}