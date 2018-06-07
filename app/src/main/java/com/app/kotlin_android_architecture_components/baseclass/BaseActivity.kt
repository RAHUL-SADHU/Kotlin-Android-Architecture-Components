package com.app.kotlin_android_architecture_components.baseclass

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
import com.app.kotlin_android_architecture_components.interfaces.AppCompactImplMethod
import com.app.kotlin_android_architecture_components.utility.Utils

/**
 * Created by Rahul Sadhu on 6/6/18.
 */
abstract class BaseActivity : AppCompatActivity(), AppCompactImplMethod {
    var baseViewModel: BaseViewModel? = null
    lateinit var viewModel: ViewModel
    private lateinit var binding: ViewDataBinding;
    private var progress_bar_dialog: Dialog? = null

    protected fun setView(layoutResId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutResId);
        try {
            initVariable()
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.showToast(this@BaseActivity, e.toString())
        }
    }


    // set ViewModel when BaseViewModel use
    inline fun <reified T : BaseViewModel> AppCompatActivity.getViewModel(): BaseViewModel {
        baseViewModel = ViewModelProviders.of(this)[T::class.java]
        setObserve()
        return baseViewModel as T
    }

    // set viewModel when BaseViewModel not use
    inline fun <reified T : ViewModel> AppCompatActivity.setViewModel(): ViewModel {
        viewModel = ViewModelProviders.of(this)[T::class.java]
        return viewModel
    }


    public fun setObserve() {
        baseViewModel?.loading?.observe(this, Observer { loading ->
            if (loading!!) showProgressDialog() else hideProgressDialog()
        })

        baseViewModel?.apiError?.observe(this, Observer { error ->
            baseViewModel!!.setLoading(false)
            apiError(error!!)
            Utils.showToast(applicationContext, error)
        })

    }

    public open fun apiError(error: String) {
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
        if (progress_bar_dialog == null) {
            progress_bar_dialog = Dialog(this)
            progress_bar_dialog?.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_progressbar)
                val layoutParams = getWindow().attributes
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                getWindow().attributes = layoutParams
                getWindow().setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            }

        }


        if (!progress_bar_dialog!!.isShowing()) {
            progress_bar_dialog!!.show()
        }

    }

    fun hideProgressDialog() {
        if (progress_bar_dialog != null && progress_bar_dialog!!.isShowing()) {
            progress_bar_dialog!!.dismiss()
        }
    }


}