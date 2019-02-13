package com.app.rahul.utility

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.create
import app.rahul.com.kotlinandroidarchitecturecomponent.BuildConfig
import app.rahul.com.kotlinandroidarchitecturecomponent.R
import com.app.rahul.baseclass.BaseActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.security.MessageDigest
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rahul Sadhu
 */

object Utils {

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String {
        return  Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    fun log(string: String) {
        if (BuildConfig.DEBUG) {
            android.util.Log.e("####", string)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getDrawable(context: Context, resourceId: Int): Drawable? {
        var drawable: Drawable? = null
        try {
            drawable = create(context.resources, resourceId, context.theme)
        } catch (e: Exception) {
            drawable = ContextCompat.getDrawable(context, resourceId)
        }

        return drawable
    }


    @Suppress("DEPRECATION")
    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

    fun startNewActivity(context: Context, intent: Intent) {
        try {
            (context as BaseActivity).startNewActivity(intent)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    fun generateSSHKey(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.getEncoder().encode(md.digest()))
                Utils.log("key:$hashKey=")
            }
        } catch (e: Exception) {
            Utils.log("error:$e")
        }

    }


    /*fun checkPlayServices(mContext: Context): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(mContext)
        if (result != ConnectionResult.SUCCESS) {
            //Google Play Services app is not available or version is not up to date. Error the
            // error condition here
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(mContext as Activity, result,
                        6).show()
            }
            return false
        }
        //Google Play Services is available. Return true.
        return true
    }*/

    fun convertCalenderToFormat(calendar: Calendar, format: String): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun createPartFromString(descriptionString: String): RequestBody {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString)
    }

    fun prepareFilePart(partName: String, file: File): MultipartBody.Part {
        /*val file = File(fileUri.toString())
        val requestFile = RequestBody.create(
                MediaType.parse(getMimeType(context,fileUri).toString()),
                file
        )*/
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }


    fun convertDate(inFormat: String, outFormat: String, setDate: String): String {
        val inDateFormat = SimpleDateFormat(inFormat, Locale.getDefault())
        val outputFormat = SimpleDateFormat(outFormat, Locale.getDefault())
        var date = Date()
        var output = ""
        try {

            date = inDateFormat.parse(setDate)
            output = outputFormat.format(date)
            return output
        } catch (pe: ParseException) {
            pe.printStackTrace()
        }
        return output
    }


    fun isGoogleMapsInstalled(context: Context): Boolean {
        return try {
            val info = context.packageManager.getApplicationInfo("com.google.android.apps.maps", 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


    fun getCalender(date: String, format: String = "yyyy-MM-dd"): Calendar {
        val calendar = Calendar.getInstance()
        try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            calendar.time = sdf.parse(date)
        } catch (e: Exception) {

        }
        return calendar
    }


    fun getTimeAgo(context: Context, msgTimeMillis: Long, numericDates: Boolean): String {
        val messageTime = Calendar.getInstance()
        messageTime.timeInMillis = msgTimeMillis
        val now = Calendar.getInstance()

        val diffDate = now.get(Calendar.DATE) - messageTime.get(Calendar.DATE)
        val diffWeek = now.get(Calendar.WEEK_OF_YEAR) - messageTime.get(Calendar.WEEK_OF_YEAR)
        val diffYear = now.get(Calendar.YEAR) - messageTime.get(Calendar.YEAR)
        val diffMonth = now.get(Calendar.MONTH) - messageTime.get(Calendar.MONTH)
        val diffHour = now.get(Calendar.HOUR_OF_DAY) - messageTime.get(Calendar.HOUR_OF_DAY)
        val diffMinute = now.get(Calendar.MINUTE) - messageTime.get(Calendar.MINUTE)
        val diffSec = now.get(Calendar.SECOND) - messageTime.get(Calendar.SECOND)


        return if (diffYear >= 2) {
            diffYear.toString() + " " + context.getString(R.string.years_ago)
        } else if (diffYear >= 1) {
            if (numericDates) {
                context.getString(R.string.one_year_ago)
            } else {
                context.getString(R.string.last_year)
            }
        } else if (diffMonth >= 2) {
            diffMonth.toString() + " " + context.getString(R.string.months_ago)
        } else if (diffMonth >= 1) {
            if (numericDates) {
                context.getString(R.string.one_month_ago)
            } else {
                context.getString(R.string.last_month)
            }
        } else if (diffWeek >= 2 && diffDate >= 7) {
            diffWeek.toString() + " " + context.getString(R.string.weeks_ago)
        } else if (diffWeek >= 1 && diffDate >= 7) {
            if (numericDates) {
                context.getString(R.string.one_week_ago)
            } else {
                context.getString(R.string.last_week)
            }
        } else if (diffDate >= 2) {
            diffDate.toString() + " " + context.getString(R.string.days_ago)

        } else if (diffDate >= 1) {
            if (numericDates) {
                context.getString(R.string.one_day_ago)
            } else {
                context.getString(R.string.yesterday)
            }
        } else if (diffHour >= 2) {
            diffHour.toString() + " " + context.getString(R.string.hours_ago)
        } else if (diffHour >= 1) {
            if (numericDates) {
                context.getString(R.string.hours_ago)
            } else {
                context.getString(R.string.an_hour_ago)
            }
        } else if (diffMinute >= 2) {
            diffMinute.toString() + " " + context.getString(R.string.minutes_ago)
        } else if (diffMinute >= 1) {
            if (numericDates) {
                context.getString(R.string.one_minute_ago)
            } else {
                context.getString(R.string.a_minute_ago)
            }
        } else if (diffSec >= 3) {
            diffSec.toString() + " " + context.getString(R.string.seconds_ago)
        } else {
            context.getString(R.string.just_now)
        }
    }
}
