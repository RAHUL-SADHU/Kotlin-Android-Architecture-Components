package com.app.rahul.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.app.rahul.baseclass.BaseViewModel
import com.app.rahul.model.AboutUsModel
import com.app.rahul.retrofit.ABOUT_US_URL
import com.app.rahul.retrofit.RetrofitClient

/**
 * Created by Rahul Sadhu
 */

class AboutUsVM : BaseViewModel() {

    private lateinit var aboutUsModel: LiveData<AboutUsModel>

    fun callAboutUs() {

        val call = RetrofitClient.getApiInterface().aboutUsService()
        getNetworkManager().requestData(call, ABOUT_US_URL)

        aboutUsModel = Transformations.map(getNetworkManager().apiResource) {
            it.data as AboutUsModel?
        }
    }


    fun getAboutUsModel(): LiveData<AboutUsModel> {
        return this.aboutUsModel
    }
}
