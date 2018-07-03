package app.rahul.com.kotlinandroidarchitecturecomponent.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.app.rahul.retrofit.GENERATE_KEY_URL
import com.app.rahul.retrofit.RetrofitClient
import com.app.rahul.retrofit.SIGN_IN_URL
import com.app.rahul.utility.GlobalKeys
import com.app.rahul.utility.SharedPrefsManager

/**
 * Created by Rahul Sadhu on 6/6/18.
 */
class LoginVM : com.app.rahul.baseclass.BaseViewModel() {
    private val userModelLiveData = MutableLiveData<com.app.rahul.model.UserModel>()

    fun callKeyApi() {
        val call = RetrofitClient.getApiInterface().getKey()
        getNetworkManager().RequestData(call, GENERATE_KEY_URL)
    }


    fun callSignIn(email: String, password: String, deviceId: String) {
        setLoading(true)
        val call = RetrofitClient.getApiInterface().singInService(email, password, deviceId)
        getNetworkManager().RequestData(call, SIGN_IN_URL)
    }

    fun getUserModel(): LiveData<com.app.rahul.model.UserModel> {
        return this.userModelLiveData
    }

    override fun responseData(responseData: com.app.rahul.model.ResponseData<*>?) {
        super.responseData(responseData)
        if (responseData!!.key.equals(GENERATE_KEY_URL)) {
            SharedPrefsManager.setString(GlobalKeys.KEY_X_API, responseData.data as String)
        } else if (responseData.key.equals(SIGN_IN_URL)) {
            SharedPrefsManager.setUserModel(responseData.data as com.app.rahul.model.UserModel)
            userModelLiveData.value = responseData.data as com.app.rahul.model.UserModel
        }
    }
}