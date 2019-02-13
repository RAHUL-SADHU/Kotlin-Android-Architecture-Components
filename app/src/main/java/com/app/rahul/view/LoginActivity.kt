package com.app.rahul.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import app.rahul.com.kotlinandroidarchitecturecomponent.databinding.ActivityLoginBinding
import com.app.rahul.baseclass.BaseActivity
import com.app.rahul.model.ResponseData
import com.app.rahul.model.UserModel
import com.app.rahul.utility.ApiObserver
import com.app.rahul.utility.Utils
import com.app.rahul.viewmodel.LoginVM

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

class LoginActivity : BaseActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var loginVM: LoginVM

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
    }

    fun clickBtnSignIn(view: View) {
        if (signInCheckValidation()) {
            showProgressDialog()
            loginVM.callSignIn(mBinding.editEmail.text.toString().trim(),
                    mBinding.editPassword.text.toString().trim(),
                    Utils.getDeviceId(this)).observe(this, object : ApiObserver<UserModel>() {
                override fun onSuccess(data: UserModel) {
                    hideProgressDialog()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startNewActivity(intent)
                }

                override fun onError(data: ResponseData<UserModel>) {
                    hideProgressDialog()
                    Utils.showToast(this@LoginActivity, data.message.toString())
                }

            })
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
