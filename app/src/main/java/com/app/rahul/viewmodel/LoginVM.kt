package com.app.rahul.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.rahul.baseclass.BaseViewModel
import com.app.rahul.model.ResponseData
import com.app.rahul.model.UserModel
import com.app.rahul.retrofit.GENERATE_KEY_URL
import com.app.rahul.retrofit.RetrofitClient
import com.app.rahul.retrofit.SIGN_IN_URL
import com.app.rahul.utility.KEY_X_API
import com.app.rahul.utility.SharedPrefsManager

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

class LoginVM : BaseViewModel() {
    private val userModelLiveData = MutableLiveData<UserModel>()

    fun callKeyApi() {
        val call = RetrofitClient.getApiInterface().getKey()
        getNetworkManager().requestData(call, GENERATE_KEY_URL)
    }


    fun callSignIn(email: String, password: String, deviceId: String) {
        val call = RetrofitClient.getApiInterface().singInService(email, password, deviceId)
        getNetworkManager().requestData(call, SIGN_IN_URL)
    }

    fun getUserModel(): LiveData<UserModel> {
        return this.userModelLiveData
    }

}