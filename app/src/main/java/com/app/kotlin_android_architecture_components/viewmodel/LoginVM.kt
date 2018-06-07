package app.rahul.com.kotlinandroidarchitecturecomponent.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.app.kotlin_android_architecture_components.baseclass.BaseViewModel
import com.app.kotlin_android_architecture_components.model.ResponseData
import com.app.kotlin_android_architecture_components.model.UserModel
import com.app.kotlin_android_architecture_components.retrofit.GENERATE_KEY_URL
import com.app.kotlin_android_architecture_components.retrofit.RetrofitClient
import com.app.kotlin_android_architecture_components.retrofit.SIGN_IN_URL
import com.app.kotlin_android_architecture_components.utility.GlobalKeys
import com.app.kotlin_android_architecture_components.utility.SharedPrefsManager

/**
 * Created by Rahul Sadhu on 6/6/18.
 */
class LoginVM : BaseViewModel() {
    private val userModelLiveData = MutableLiveData<UserModel>()

    fun callKeyApi() {
        val call = RetrofitClient.getApiInterface().getKey()
        getNetworkManager().RequestData(call, GENERATE_KEY_URL)
    }


    fun callSignIn(email: String, password: String, deviceId: String) {
        setLoading(true)
        val call = RetrofitClient.getApiInterface().singInService(email, password, deviceId)
        getNetworkManager().RequestData(call, SIGN_IN_URL)
    }

    fun getUserModel(): LiveData<UserModel> {
        return this.userModelLiveData
    }

    override fun responseData(responseData: ResponseData<*>?) {
        super.responseData(responseData)
        if (responseData!!.key.equals(GENERATE_KEY_URL)) {
            SharedPrefsManager.setString(GlobalKeys.KEY_X_API, responseData.data as String)
        } else if (responseData.key.equals(SIGN_IN_URL)) {
            SharedPrefsManager.setUserModel(responseData.data as UserModel)
            userModelLiveData.setValue(responseData.data as UserModel)
        }
    }
}