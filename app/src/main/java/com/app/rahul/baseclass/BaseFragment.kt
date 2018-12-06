package com.app.rahul.baseclass

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.interfaces.BaseFragmentImplMethod
import com.app.rahul.model.ResponseData
import com.app.rahul.utility.FAILURE
import com.app.rahul.utility.SESSION_EXPIRE_MSG
import com.app.rahul.utility.SharedPrefsManager
import com.app.rahul.utility.Utils
import com.app.rahul.view.LoginActivity


/**
 * Created by Rahul Sadhu
 */
abstract class BaseFragment : Fragment(), BaseFragmentImplMethod {
    lateinit var baseViewModel: BaseViewModel
    lateinit var viewModel: ViewModel
    private var layoutId: Int = 0
    private lateinit var binding: ViewDataBinding
    private lateinit var progressDialog: Dialog

    protected fun setView(layoutId: Int) {
        this.layoutId = layoutId
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            initVariable()
            loadData()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /* fun setActionBar(action_bar: View, title: String, isBack: Boolean) {
         action_bar.txt_title.text = title
         if (!isBack) {
             action_bar.img_back.visibility = View.INVISIBLE
         } else {
             action_bar.img_back.setOnClickListener {
                 requireActivity().onBackPressed()
             }
         }
     }*/


    protected fun getBinding(): ViewDataBinding {
        return binding
    }

    // set ViewModel when BaseViewModel use
    inline fun <reified T : BaseViewModel> getViewModel(): BaseViewModel {
        baseViewModel = ViewModelProviders.of(this)[T::class.java]
        setObserve()
        return baseViewModel as T
    }

    // set viewModel when BaseViewModel not use
    inline fun <reified T : ViewModel> setViewModel(): ViewModel {
        viewModel = ViewModelProviders.of(this)[T::class.java]
        return viewModel
    }

    // get Activity ViewModel
    inline fun <reified T : BaseViewModel> getActivityViewModel(): BaseViewModel {
        baseViewModel = ViewModelProviders.of(requireActivity())[T::class.java]
        return baseViewModel as T
    }


    fun setObserve() {
        baseViewModel.loading.observe(requireActivity(), Observer { loading ->
            if (loading!!) showProgressDialog() else hideProgressDialog()
        })


        baseViewModel.apiResponse.observe(requireActivity(), Observer {
            hideProgressDialog()
            if (it.status == FAILURE) {
                apiResponseError(it)
            } else {
                apiResponse(it)
            }

        })

        baseViewModel.apiError.observe(requireActivity(), Observer { error ->
            baseViewModel.setLoading(false)
            apiError(error)
            if (!TextUtils.isEmpty(error) && isAdded) {
                Utils.showToast(requireContext(), error)
                if (error == SESSION_EXPIRE_MSG) {
                    SharedPrefsManager.clearPrefs()
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    Utils.startNewActivity(requireContext(), intent)
                }
            }


        })

    }

    open fun apiResponseError(it: ResponseData<*>?) {

    }

    open fun apiError(error: String) {
        // when error method use in Activity
    }

    open fun apiResponse(response: ResponseData<*>) {
        // when response call this method use in Activity
    }

    private fun showProgressDialog() {
        if (!::progressDialog.isInitialized && isAdded) {
            progressDialog = Dialog(requireContext())
            progressDialog.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_progressbar)
                val layoutParams = window?.attributes
                layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                window?.attributes = layoutParams
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                /*setOnDismissListener {
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

}