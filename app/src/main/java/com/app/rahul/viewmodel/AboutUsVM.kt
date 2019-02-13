package com.app.rahul.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.rahul.listener.RetryCallback
import com.app.rahul.model.AboutUsModel
import com.app.rahul.model.ResponseData
import com.app.rahul.retrofit.ABOUT_US_URL
import com.app.rahul.retrofit.NetworkManager
import com.app.rahul.retrofit.RetrofitClient

/**
 * Created by Rahul Sadhu
 */

class AboutUsVM : ViewModel() {

    var responseData = MutableLiveData<ResponseData<AboutUsModel>>()

    fun callAboutUs() {
        val call = RetrofitClient.getApiInterface().aboutUsService()
        NetworkManager.requestData(call, ABOUT_US_URL, responseData)
    }

    fun retry() {
        callAboutUs()
    }


}
