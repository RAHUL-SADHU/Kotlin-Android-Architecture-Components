package com.app.rahul.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.rahul.model.ResponseData
import com.app.rahul.model.UserModel
import com.app.rahul.retrofit.GENERATE_KEY_URL
import com.app.rahul.retrofit.NetworkManager
import com.app.rahul.retrofit.RetrofitClient
import com.app.rahul.retrofit.SIGN_IN_URL

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

class LoginVM : ViewModel() {
    private var userModelLiveData = MutableLiveData<ResponseData<UserModel>>()
    private var apiKeyLiveData = MutableLiveData<ResponseData<String>>()

    fun callKeyApi() {
        val call = RetrofitClient.getApiInterface().getKey()
        NetworkManager.requestData(call, GENERATE_KEY_URL, apiKeyLiveData)
    }


    fun callSignIn(email: String, password: String, deviceId: String): LiveData<ResponseData<UserModel>> {
        val call = RetrofitClient.getApiInterface().singInService(email, password, deviceId)
        NetworkManager.requestData(call, SIGN_IN_URL, userModelLiveData)
        return userModelLiveData
    }

}