package com.app.rahul.baseclass

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.app.rahul.retrofit.NetworkManager

/**
 * Created by Rahul Sadhu on 21/5/18.
 */
open class BaseViewModel : ViewModel() {

    lateinit var responseDataObserver: Observer<com.app.rahul.model.ResponseData<*>>
    private var isLoading = MutableLiveData<Boolean>()
    private val networkManager: NetworkManager = NetworkManager()

    init {
        setObservable()
    }

    internal val loading: LiveData<Boolean>
        get() = isLoading


    internal val apiResponse: LiveData<com.app.rahul.model.ResponseData<*>>
        get() = this.networkManager.apiResponse


    internal val apiError: com.app.rahul.baseclass.SingleLiveData<String>
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

    open fun responseData(responseData: com.app.rahul.model.ResponseData<*>?) {
        setLoading(false)
    }

}