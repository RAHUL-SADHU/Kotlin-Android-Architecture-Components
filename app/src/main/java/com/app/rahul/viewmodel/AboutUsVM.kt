package com.app.rahul.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.rahul.baseclass.BaseViewModel
import com.app.rahul.model.AboutUsModel
import com.app.rahul.model.ResponseData
import com.app.rahul.retrofit.ABOUT_US_URL
import com.app.rahul.retrofit.RetrofitClient

/**
 * Created by Rahul Sadhu
 */

class AboutUsVM : BaseViewModel() {

    private val aboutUsModel = MutableLiveData<AboutUsModel>()

    fun callAboutUs() {
        setLoading(true)
        val call = RetrofitClient.getApiInterface().aboutUsService()
        getNetworkManager().requestData(call, ABOUT_US_URL)
    }

    override fun apiResponse(responseData: ResponseData<*>) {
        super.apiResponse(responseData)
        aboutUsModel.value = responseData.data as AboutUsModel
    }


    fun getAboutUsModel(): LiveData<AboutUsModel> {
        return this.aboutUsModel
    }

}
