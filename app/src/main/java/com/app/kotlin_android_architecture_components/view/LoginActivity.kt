package com.app.kotlin_android_architecture_components.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import app.rahul.com.kotlinandroidarchitecturecomponent.databinding.ActivityLoginBinding
import app.rahul.com.kotlinandroidarchitecturecomponent.view.MainActivity
import app.rahul.com.kotlinandroidarchitecturecomponent.viewmodel.LoginVM
import com.app.kotlin_android_architecture_components.baseclass.BaseActivity
import com.app.kotlin_android_architecture_components.utility.Utils

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

class LoginActivity : BaseActivity() {
    private lateinit var mBinding :ActivityLoginBinding
    private lateinit var loginVM:LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView(R.layout.activity_login)

    }


    override fun initVariable() {
        mBinding = getBinding() as ActivityLoginBinding
        loginVM = getViewModel<LoginVM>() as LoginVM

    }

    override fun loadData() {
      loginVM.callKeyApi()
      loginVM.getUserModel().observe(this, Observer { userModel ->
          val intent = Intent(this@LoginActivity, MainActivity::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
          startNewActivity(intent)
      })
    }

    fun clickBtnSignIn(view: View) {
        if (signInCheckValidation()) {
            loginVM.callSignIn(mBinding.editEmail.text.toString().trim(),
                    mBinding.editPassword.text.toString().trim(),
                    Utils.getDeviceId(this))
        }
    }

    private fun signInCheckValidation(): Boolean {
        if (TextUtils.isEmpty(mBinding.editEmail.text.toString().trim())) {
            mBinding.editEmail.error = getString(R.string.please_enter_valid_email)
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mBinding.editEmail.text.toString().trim()).matches()) {
            mBinding.editEmail.error = getString(R.string.please_enter_valid_email)
            return false
        } else if (TextUtils.isEmpty(mBinding.editPassword.text.toString().trim())) {
            mBinding.editPassword.error = getString(R.string.please_enter_password)
            return false
        } else if (mBinding.editPassword.text.toString().trim().length < 5) {
            mBinding.editPassword.error = getString(R.string.please_enter_minimum_password)
            return false
        }
        return true
    }

}
