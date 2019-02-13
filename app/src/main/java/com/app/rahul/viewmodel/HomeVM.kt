package com.app.rahul.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.rahul.model.ResponseData
import com.app.rahul.model.UserModel
import com.app.rahul.retrofit.NetworkManager
import com.app.rahul.retrofit.RetrofitClient
import com.app.rahul.retrofit.USER_LIST_URL

class HomeVM : ViewModel() {

    var userListLiveData = MutableLiveData<ResponseData<ArrayList<UserModel>>>()

    fun callUserList(): MutableLiveData<ResponseData<ArrayList<UserModel>>> {
        val call = RetrofitClient.getApiInterface().getUserList()
        NetworkManager.requestData(call, USER_LIST_URL, userListLiveData)
        return userListLiveData
    }

    fun retry() {
        callUserList()
    }

}