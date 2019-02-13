package com.app.rahul.model

import androidx.recyclerview.widget.DiffUtil

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by RahulSadhu
 */

class UserModel {

    @SerializedName("id")
    @Expose
    var userid: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("country_id")
    @Expose
    private val countryId: String? = null
    @SerializedName("phonecode")
    @Expose
    private val phonecode: String? = null
    @SerializedName("phone_number")
    @Expose
    private val phoneNumber: String? = null
    @SerializedName("referral_code")
    @Expose
    private val referralCode: String? = null
    @SerializedName("otp_verified")
    @Expose
    private val otpVerified: Int? = null
    @SerializedName("facebook_id")
    @Expose
    private val facebookId: String? = null
    @SerializedName("google_id")
    @Expose
    private val googleId: String? = null
    @SerializedName("device_token")
    @Expose
    private val deviceToken: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("status")
    @Expose
    private val status: String? = null
    @SerializedName("is_visible")
    @Expose
    private val isVisible: Int? = null
    @SerializedName("food_type_id")
    @Expose
    private val foodTypeId: String? = null
    @SerializedName("preference_name")
    @Expose
    private val preferenceName: String? = null
    @SerializedName("date_created")
    @Expose
    private val dateCreated: String? = null
    @SerializedName("accesstoken")
    @Expose
    var accesstoken: String? = null
    @SerializedName("isExistingUser")
    @Expose
    private val isExistingUser: Int? = null
    @SerializedName("is_social_login")
    @Expose
    private val isSocialLogin: Int? = null
    @SerializedName("profession")
    @Expose
    private val profession: String? = null

    companion object {

        var diffCallBack: DiffUtil.ItemCallback<UserModel> = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.userid == newItem.userid
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem === newItem
            }
        }
    }
}

