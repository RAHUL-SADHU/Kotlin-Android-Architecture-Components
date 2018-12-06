package com.app.rahul.baseclass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.app.rahul.model.ResponseData
import com.app.rahul.retrofit.NetworkManager
import com.app.rahul.utility.FAILURE

/**
 * Created by Rahul Sadhu.
 */
open class BaseViewModel : ViewModel() {

    lateinit var responseDataObserver: Observer<ResponseData<*>>
    private var isLoading = MutableLiveData<Boolean>()
    private val networkManager: NetworkManager = NetworkManager()

    init {
        setObservable()
    }

    internal val loading: LiveData<Boolean>
        get() = isLoading


    internal val apiResponse: LiveData<ResponseData<*>>
        get() = this.networkManager.apiResponse


    internal val apiError: LiveData<String>
        get() = this.networkManager.apiError

    fun getNetworkManager(): NetworkManager = networkManager


    private fun setObservable() {
        // removeObserve() in BaseActivity
        responseDataObserver = Observer { responseData ->
            if (responseData.status != FAILURE) {
                apiResponse(responseData)
            }

        }
        apiResponse.observeForever(responseDataObserver)
    }

    fun setLoading(loading: Boolean) {
        isLoading.postValue(loading)
    }

    open fun apiResponse(responseData: ResponseData<*>) {
        setLoading(false)
    }
}