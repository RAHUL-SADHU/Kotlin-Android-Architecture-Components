package com.app.kotlin_android_architecture_components.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by RahulSadhu on 24/11/17.
 */

class RestaurantModel {
    @SerializedName("restaurant_id")
    @Expose
    var restaurantId: String? = null
    @SerializedName("business_name")
    @Expose
    var businessName: String? = null
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("phone_number")
    @Expose
    var phone_number: String? = null
    @SerializedName("distance")
    @Expose
    var distance: String? = null
    @SerializedName("imageoriginal")
    @Expose
    var imageoriginal: String? = null
    @SerializedName("imagemedium")
    @Expose
    var imagemedium: String? = null
    @SerializedName("imagesmall")
    @Expose
    var imagesmall: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
    @SerializedName("website")
    @Expose
    var website: String? = null
    @SerializedName("sitting_type")
    @Expose
    var sittingType: String? = null
    @SerializedName("is_alcoholic")
    @Expose
    var isAlcoholic: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("area")
    @Expose
    var area: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("day")
    @Expose
    var day: String? = null

    @SerializedName("ratings")
    @Expose
    var rating: String? = null


}
