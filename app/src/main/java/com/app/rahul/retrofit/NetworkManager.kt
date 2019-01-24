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
    val apiResource = MutableLiveData<ResponseData<*>>()
    val gson = Gson()

    fun <T> requestData(call: Call<ResponseData<T>>, key: String) {
        apiResource.postValue(ResponseData.loading(null))
        call.enqueue(object : Callback<ResponseData<T>> {


            override fun onResponse(call: Call<ResponseData<T>>?, response: Response<ResponseData<T>>?) {
                val data = response as Response<ResponseData<T>>
                if (response.isSuccessful) {
                    data.body()?.key = key
                    apiResource.postValue(ResponseData.success(response.body()))
                } else {
                    val errorData = gson.fromJson(response.errorBody()?.charStream(), ResponseData::class.java)
                    if (errorData != null) {
                        if (data.code() == 401) {
                            apiResource.postValue(ResponseData.error(SESSION_EXPIRE_MSG, null))
                        } else {
                            call?.let {
                                apiResource.postValue(ResponseData.error(errorData.message.toString(), null))
                            }
                        }

                    } else {
                        apiResource.postValue(ResponseData.error(SERVER_ERROR, null))
                    }
                }

            }

            override fun onFailure(call: Call<ResponseData<T>>, t: Throwable?) {
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
                    apiResource.postValue(ResponseData.error(message, null))
                }


            }
        })
    }

    /* fun cancelCall() {
         if (!call.isCanceled) {
             call.cancel()
         }
     }*/
}