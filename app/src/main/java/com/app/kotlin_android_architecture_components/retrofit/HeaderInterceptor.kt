package com.app.kotlin_android_architecture_components.retrofit

import com.app.kotlin_android_architecture_components.utility.GlobalKeys
import com.app.kotlin_android_architecture_components.utility.SharedPrefsManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Rahul Sadhu
 */

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
                .method(original.method(), original.body())
        requestBuilder.addHeader(GlobalKeys.KEY_X_API, SharedPrefsManager.getString(GlobalKeys.KEY_X_API))
        if (SharedPrefsManager.containsKey(GlobalKeys.KEY_ACCESS_TOKEN)) {
            requestBuilder.addHeader(GlobalKeys.KEY_ACCESS_TOKEN, SharedPrefsManager.getString(GlobalKeys.KEY_ACCESS_TOKEN))
        }
        if (SharedPrefsManager.containsKey(GlobalKeys.KEY_USER_ID)) {
            requestBuilder.addHeader(GlobalKeys.KEY_USER_ID, SharedPrefsManager.getString(GlobalKeys.KEY_USER_ID))
        }
        return chain.proceed(requestBuilder.build())
    }
}
