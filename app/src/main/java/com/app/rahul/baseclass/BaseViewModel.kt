package com.app.rahul.baseclass

import androidx.lifecycle.ViewModel
import com.app.rahul.retrofit.NetworkManager

/**
 * Created by Rahul Sadhu.
 */
open class BaseViewModel : ViewModel() {

    private val networkManager: NetworkManager = NetworkManager()

    fun getNetworkManager(): NetworkManager = networkManager
}