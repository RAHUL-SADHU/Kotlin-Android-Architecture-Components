package com.app.rahul.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rahul Sadhu
 */

data class ResponseData<T>(

        @SerializedName("MESSAGE")
        var message: String?,
        @SerializedName("SUCCESS")
    var success: Int = 0,
        @SerializedName("DATA")
        var data: T?,
        @SerializedName("STATUS")
        var status: Int,
        var key: String?

)