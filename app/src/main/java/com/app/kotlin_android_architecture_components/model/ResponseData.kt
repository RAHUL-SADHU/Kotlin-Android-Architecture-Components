package com.app.kotlin_android_architecture_components.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rahul Sadhu
 */

data class ResponseData<T>(

    @SerializedName("MESSAGE")
    var message: String? = null,
    @SerializedName("SUCCESS")
    var success: Int = 0,
    @SerializedName("DATA")
    var data: T? = null,
    @SerializedName("STATUS")
    var status: String? = null,
    var key: String? = null

)