package com.app.rahul.utility

import androidx.lifecycle.Observer
import com.app.rahul.model.ResponseData
import com.app.rahul.retrofit.Status

abstract class ApiObserver<T>() : Observer<ResponseData<T>> {

    override fun onChanged(responseData: ResponseData<T>?) {

        if (responseData?.status == Status.SUCCESS) {

            responseData.data?.let { onSuccess(it) }
        } else if (responseData?.status == Status.ERROR) {
            onError(responseData)
        }
    }

    abstract fun onSuccess(data: T)

    abstract fun onError(data: ResponseData<T>)
}