package com.app.kotlin_android_architecture_components.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by RahulSadhu on 18/1/18.
 */

class PaginationModel<T> {

    @SerializedName("data")
    @Expose
    var data: ArrayList<T>? = null
    @SerializedName("total")
    @Expose
    var total: Int = 0
}
