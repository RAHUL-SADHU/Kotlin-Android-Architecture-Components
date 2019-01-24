package com.app.rahul.model

import com.app.rahul.retrofit.Status
import com.google.gson.annotations.SerializedName

/**
 * Created by Rahul Sadhu
 */

data class ResponseData<T>(

        @SerializedName("MESSAGE")
        var message: String? = "",
        @SerializedName("SUCCESS")
        var success: Int = 0,
        @SerializedName("DATA")
        var data: T?,
        @SerializedName("STATUS")
        var status: Status = Status.LOADING,
        var key: String? = ""

) {
    companion object {
        fun <T> success(data: ResponseData<T>?): ResponseData<T> {
            return if (data is ResponseData<T>) {
                data.status = Status.SUCCESS
                data
            } else {
                error("Data not Available", null)
            }
        }

        fun <T> error(msg: String, data: T?): ResponseData<T> {
            return ResponseData(message = msg, data = data)
        }

        fun <T> loading(data: T?): ResponseData<T> {
            return ResponseData(data = data)
        }
    }
}