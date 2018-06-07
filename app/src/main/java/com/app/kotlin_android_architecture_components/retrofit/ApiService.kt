package com.app.kotlin_android_architecture_components.retrofit

import com.app.kotlin_android_architecture_components.model.*
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
    fun getKey() : Call<ResponseData<String>>

    @FormUrlEncoded
    @POST(SIGN_IN_URL)
    fun singInService(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("device_token") deviceToken: String
    ): Call<ResponseData<UserModel>>

    @POST(ABOUT_US_URL)
    fun aboutUsService(): Call<ResponseData<AboutUsModel>>

    @FormUrlEncoded
    @POST(FRIENDS_PROFILE_DATA_URL)
    fun friendsProfileListService(
            @Field("page") page: Int
    ): Call<ResponseData<PaginationModel<UserModel>>>

    @Multipart
    @POST(RESTAURANT_LIST_URL)
    fun restaurantListService(
            @PartMap partMap: Map<String, RequestBody>
    ): Call<ResponseData<ArrayList<RestaurantModel>>>


}
