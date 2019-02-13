package com.app.rahul.retrofit


import androidx.lifecycle.MutableLiveData
import com.app.rahul.model.ResponseData
import com.app.rahul.utility.*
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
object NetworkManager {
    val gson = Gson()

    fun <T> requestData(call: Call<ResponseData<T>>, key: String, responseData: MutableLiveData<ResponseData<T>>) {
        responseData.postValue(ResponseData.loading(null))
        call.enqueue(object : Callback<ResponseData<T>> {
            override fun onResponse(call: Call<ResponseData<T>>?, response: Response<ResponseData<T>>?) {
                val data = response as Response<ResponseData<T>>
                if (response.isSuccessful) {
                    data.body()?.key = key
                    responseData.postValue(ResponseData.success(response.body()))
                } else {
                    try {
                        val errorData = gson.fromJson(response.errorBody()?.charStream(), ResponseData::class.java)
                        if (data.code() == 401) {
                            responseData.postValue(ResponseData.error(SESSION_EXPIRE_MSG, null))
                        } else {
                            call?.let {
                                responseData.postValue(ResponseData.error(errorData.message.toString(), null))
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        responseData.postValue(ResponseData.error(SERVER_ERROR, null))
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
                        is SocketTimeoutException -> PLEASE_TRY_AGAIN
                        else -> SERVER_ERROR
                    }
                    responseData.postValue(ResponseData.error(message, null))
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