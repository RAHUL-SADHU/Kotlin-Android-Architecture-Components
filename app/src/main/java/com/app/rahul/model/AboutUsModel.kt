package com.app.rahul.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Rahul Sadhu on 6/6/18.
 */

data class AboutUsModel (
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("content")
    @Expose
    var content: String? = null
)
