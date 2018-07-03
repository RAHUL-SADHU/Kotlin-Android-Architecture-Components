package com.app.rahul.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.app.rahul.retrofit.ABOUT_US_URL
import com.app.rahul.retrofit.RetrofitClient

/**
 * Created by Rahul Sadhu
 */

class AboutUsVM : com.app.rahul.baseclass.BaseViewModel() {

    private val aboutUsModel = MutableLiveData<com.app.rahul.model.AboutUsModel>()

    fun callAboutUs() {
        setLoading(true)
        val call = RetrofitClient.getApiInterface().aboutUsService()
        getNetworkManager().RequestData(call, ABOUT_US_URL)
    }

    override fun responseData(responseData: com.app.rahul.model.ResponseData<*>?) {
        super.responseData(responseData)
        aboutUsModel.value = responseData?.data as com.app.rahul.model.AboutUsModel
    }

    fun getAboutUsModel(): LiveData<com.app.rahul.model.AboutUsModel> {
        return this.aboutUsModel
    }

}
