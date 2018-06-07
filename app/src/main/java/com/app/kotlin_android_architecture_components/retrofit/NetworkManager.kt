package com.app.kotlin_android_architecture_components.retrofit

import android.arch.lifecycle.MutableLiveData
import com.app.kotlin_android_architecture_components.utility.UtilConstants
import com.app.kotlin_android_architecture_components.baseclass.SingleLiveData
import com.app.kotlin_android_architecture_components.model.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by RahulSadhu on 21/5/18.
 */
class NetworkManager {
    public val apiError = SingleLiveData<String>();
    public val apiResponse: MutableLiveData<ResponseData<*>> = MutableLiveData();

    fun <T> RequestData(call: Call<T>, key: String) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                val data = response as Response<ResponseData<T>>
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
                val message: String

                when (t) {
                    is ConnectException -> message = "No Internet connection!"
                    is SocketTimeoutException -> message = "Please try again later…"
                    else -> message = "Unknown Error !"
                }

                apiError.postValue(message)
            }
        })
    }


    private fun <T> sendError(code: Int, response: Response<T>, call: Call<T>) {
        val message: String

        when (code) {
            404 -> message = "Not Found !"
            500 -> message = "Server Error !"
            else -> message = "Unknown Error !"
        }
        apiError.postValue(message)

    }

}