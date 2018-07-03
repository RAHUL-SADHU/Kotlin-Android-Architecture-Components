package com.app.rahul.retrofit

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * Created by RahulSadhu
 */


const val GENERATE_KEY_URL = "key"
const val SIGN_IN_URL = "signin"
const val ABOUT_US_URL = "content/about_us"
const val FRIENDS_PROFILE_DATA_URL = "users"
const val RESTAURANT_LIST_URL = "restaurant/"

interface ApiService {

    @POST(GENERATE_KEY_URL)
    fun getKey(): Call<com.app.rahul.model.ResponseData<String>>

    @FormUrlEncoded
    @POST(SIGN_IN_URL)
    fun singInService(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("device_token") deviceToken: String
    ): Call<com.app.rahul.model.ResponseData<com.app.rahul.model.UserModel>>

    @POST(ABOUT_US_URL)
    fun aboutUsService(): Call<com.app.rahul.model.ResponseData<com.app.rahul.model.AboutUsModel>>

    @FormUrlEncoded
    @POST(FRIENDS_PROFILE_DATA_URL)
    fun friendsProfileListService(
            @Field("page") page: Int
    ): Call<com.app.rahul.model.ResponseData<com.app.rahul.model.PaginationModel<com.app.rahul.model.UserModel>>>

    @Multipart
    @POST(RESTAURANT_LIST_URL)
    fun restaurantListService(
            @PartMap partMap: Map<String, RequestBody>
    ): Call<com.app.rahul.model.ResponseData<ArrayList<com.app.rahul.model.RestaurantModel>>>


}
