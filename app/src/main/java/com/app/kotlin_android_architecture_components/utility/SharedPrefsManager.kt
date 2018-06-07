package com.app.kotlin_android_architecture_components.utility

import android.content.Context
import android.content.SharedPreferences
import com.app.kotlin_android_architecture_components.model.UserModel

/**
 * Created by Rahul Sadhu
 */

object SharedPrefsManager {

    private val PREF_NAME = SharedPreferences::class.java.`package`.name
    lateinit var prefs: SharedPreferences


    fun initialize(appContext: Context?) {
        prefs = appContext!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    /**
     * Clears all data in SharedPreferences
     */
    fun clearPrefs() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun setString(key: String, value: String?) {
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun removeKey(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun containsKey(key: String): Boolean {
        return prefs.contains(key)
    }

    fun getString(key: String): String {
        return prefs.getString(key, "")
    }

    fun setUserModel(userModel: UserModel) {
        setString(GlobalKeys.KEY_USER_ID, userModel.userid)
        setString(GlobalKeys.KEY_ACCESS_TOKEN, userModel.accesstoken)
    }

    }



