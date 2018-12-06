package com.app.rahul.retrofit


import androidx.lifecycle.MutableLiveData
import com.app.rahul.model.ResponseData
import com.app.rahul.utility.NETWORK_ERROR
import com.app.rahul.utility.SERVER_ERROR
import com.app.rahul.utility.SESSION_EXPIRE_MSG
import com.app.rahul.utility.Utils
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Created by RahulSadhu.
 */
class NetworkManager {
    val apiError = MutableLiveData<String>()
    val apiResponse: MutableLiveData<ResponseData<*>> = MutableLiveData()
    private lateinit var call: Call<*>
    val gson = Gson()

    fun <T> requestData(call: Call<T>, key: String) {
        this.call = call
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                val data = response as Response<ResponseData<T>>
                if (response.isSuccessful) {
                    data.body()?.key = key
                    apiResponse.postValue(data.body())
                } else {
                    val errorData = gson.fromJson(response.errorBody()?.charStream(), ResponseData::class.java)
                    if (errorData != null) {
                        if (data.code() == 401) {
                            apiError.postValue(SESSION_EXPIRE_MSG)
                        } else {
                            call?.let { apiError.postValue(errorData.message) }
                        }

                    } else {
                        apiError.postValue(SERVER_ERROR)
                    }
                }

            }

            override fun onFailure(call: Call<T>, t: Throwable?) {
                Utils.log("onFailure ${t?.localizedMessage}")
                if (call.isCanceled) {
                    Utils.log("request was cancelled")
                } else {

                    val message: String = when (t) {
                        is ConnectException -> NETWORK_ERROR
                        is UnknownHostException -> NETWORK_ERROR
                        is SocketTimeoutException -> "Please try again later…"
                        else -> SERVER_ERROR
                    }

                    apiError.postValue(message)
                }


            }
        })
    }

    fun cancelCall() {
        if (!call.isCanceled) {
            call.cancel()
        }
    }
}