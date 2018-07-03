package com.app.rahul.retrofit

import android.arch.lifecycle.MutableLiveData
import com.app.rahul.utility.UtilConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by RahulSadhu on 21/5/18.
 */
class NetworkManager {
    val apiError = com.app.rahul.baseclass.SingleLiveData<String>()
    val apiResponse: MutableLiveData<com.app.rahul.model.ResponseData<*>> = MutableLiveData()

    fun <T> RequestData(call: Call<T>, key: String) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                val data = response as Response<com.app.rahul.model.ResponseData<T>>
                if (response.isSuccessful) {
                    if (response.body()!!.success == UtilConstants.SUCCESS) {
                        //success
                        data.body()?.key = key
                        apiResponse.postValue(data.body())
                    } else {
                        //failure
                        apiError.postValue(data.body()?.message)
                    }
                } else {
                    call?.let { sendError<T>(response.code(), response, it) }
                }

            }

            override fun onFailure(call: Call<T>?, t: Throwable?) {
                val message: String = when (t) {
                    is ConnectException -> "No Internet connection!"
                    is SocketTimeoutException -> "Please try again later…"
                    else -> "Unknown Error !"
                }

                apiError.postValue(message)
            }
        })
    }


    private fun <T> sendError(code: Int, response: Response<T>, call: Call<T>) {
        val message: String = when (code) {
            404 -> "Not Found !"
            500 -> "Server Error !"
            else -> "Unknown Error !"
        }

        apiError.postValue(message)

    }

}