package com.app.rahul.retrofit

import app.rahul.com.kotlinandroidarchitecturecomponent.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Rahul Sadhu on 5/6/18.
 */

object RetrofitClient {

    private val httpClient = OkHttpClient.Builder()
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val headerInterceptor: HeaderInterceptor = HeaderInterceptor()
    private var apiService: ApiService


    init {
        val gson = GsonBuilder()
                .setLenient()
                .create()
        httpClient.connectTimeout(25, TimeUnit.SECONDS)
        httpClient.readTimeout(1, TimeUnit.SECONDS)
        httpClient.retryOnConnectionFailure(true)
        addInterceptors()
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()
        apiService = retrofit.create(ApiService::class.java)

    }


    fun getApiInterface(): ApiService {
        return apiService
    }

    private fun addInterceptors() {
        if (!httpClient.interceptors().contains(headerInterceptor)) {
            httpClient.addInterceptor(headerInterceptor)
        }

        if (BuildConfig.DEBUG && !httpClient.networkInterceptors().contains(loggingInterceptor)) {
            httpClient.addNetworkInterceptor(loggingInterceptor)
        }
    }

}