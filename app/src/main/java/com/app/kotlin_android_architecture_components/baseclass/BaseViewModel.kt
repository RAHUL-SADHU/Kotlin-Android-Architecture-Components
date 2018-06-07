package com.app.kotlin_android_architecture_components.baseclass

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.app.kotlin_android_architecture_components.model.ResponseData
import com.app.kotlin_android_architecture_components.retrofit.NetworkManager

/**
 * Created by Rahul Sadhu on 21/5/18.
 */
open class BaseViewModel : ViewModel() {

    lateinit var responseDataObserver: Observer<ResponseData<*>>
    private var isLoading = MutableLiveData<Boolean>()
    private val networkManager: NetworkManager

    init {
        networkManager = NetworkManager()
        setObservable()
    }

    internal val loading: LiveData<Boolean>
        get() = isLoading


    internal val apiResponse: LiveData<ResponseData<*>>
        get() = this.networkManager.apiResponse


    internal val apiError: SingleLiveData<String>
        get() = this.networkManager.apiError

    protected fun getNetworkManager(): NetworkManager = networkManager


    private fun setObservable() {
        // removeObserve() in BaseActivity
        responseDataObserver = Observer { responseData -> responseData(responseData) }
        apiResponse.observeForever(responseDataObserver)
    }

    fun setLoading(loading: Boolean) {
        isLoading.postValue(loading)
    }

    open fun responseData(responseData: ResponseData<*>?) {
        setLoading(false)
    }

}