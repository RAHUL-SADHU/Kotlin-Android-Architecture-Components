package com.app.kotlin_android_architecture_components.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.app.kotlin_android_architecture_components.baseclass.BaseViewModel
import com.app.kotlin_android_architecture_components.model.AboutUsModel
import com.app.kotlin_android_architecture_components.model.ResponseData
import com.app.kotlin_android_architecture_components.retrofit.ABOUT_US_URL
import com.app.kotlin_android_architecture_components.retrofit.RetrofitClient

/**
 * Created by Rahul Sadhu
 */

class AboutUsVM : BaseViewModel() {

    private val aboutUsModel = MutableLiveData<AboutUsModel>()

    fun callAboutUs() {
        setLoading(true)
        val call = RetrofitClient.getApiInterface().aboutUsService()
        getNetworkManager().RequestData(call, ABOUT_US_URL)
    }

   override fun responseData(responseData: ResponseData<*>?) {
        super.responseData(responseData)
        aboutUsModel.setValue(responseData?.data as AboutUsModel)
    }

    fun getAboutUsModel(): LiveData<AboutUsModel> {
        return this.aboutUsModel
    }

}
