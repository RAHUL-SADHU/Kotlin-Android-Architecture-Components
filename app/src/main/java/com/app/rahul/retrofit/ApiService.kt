package com.app.rahul.retrofit

import com.app.rahul.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by RahulSadhu
 */


const val GENERATE_KEY_URL = "key"
const val SIGN_IN_URL = "signin"
const val ABOUT_US_URL = "fda9a"
const val USER_LIST_URL = "bjwyg"

interface ApiService {

    @POST(GENERATE_KEY_URL)
    fun getKey(): Call<ResponseData<String>>

    @FormUrlEncoded
    @POST(SIGN_IN_URL)
    fun singInService(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("device_token") deviceToken: String
    ): Call<ResponseData<UserModel>>

    @GET(ABOUT_US_URL)
    fun aboutUsService(): Call<ResponseData<AboutUsModel>>

    @GET(USER_LIST_URL)
    fun getUserList(): Call<ResponseData<ArrayList<UserModel>>>





}
