package com.app.rahul.baseclass

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.interfaces.BaseFragmentImplMethod


/**
 * Created by Rahul Sadhu
 */
abstract class BaseFragment : Fragment(), BaseFragmentImplMethod {
    private var layoutId: Int = 0
    private lateinit var binding: ViewDataBinding


    private val progressDialog: Dialog by lazy {
        Dialog(requireContext()).apply {
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


    inline fun <reified T : ViewModel> getViewModel(): ViewModel {
        return ViewModelProviders.of(this)[T::class.java]
    }

    private fun showProgressDialog() {
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