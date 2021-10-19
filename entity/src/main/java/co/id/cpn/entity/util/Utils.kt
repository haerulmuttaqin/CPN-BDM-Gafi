package co.id.cpn.entity.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.sqlcipher.BuildConfig
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun getDeviceID(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return ""
            }
            val imei = telephonyManager.deviceId
            Log.e("imei", "=$imei")
            if (imei != null && !imei.isEmpty()) {
                return imei
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return Build.getSerial()
                }
            }
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
            e.printStackTrace()
        }
        return ""
    }

    fun getIMEIDeviceId(context: Context): String {
        val deviceId: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            val mTelephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return ""
                }
            }
            assert(mTelephony != null)
            if (mTelephony.deviceId != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mTelephony.imei
                } else {
                    mTelephony.deviceId
                }
            } else {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            }
        }
        Log.d("deviceId", deviceId)
        return deviceId
    }

    fun getStringPreference(context: Context?, key: String?): String? {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getString(key, null)
    }

    fun getIntegerPreference(context: Context?, key: Int): Int {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getInt(key.toString(), 0)
    }

    fun getLongPreference(context: Context?, key: String?): Long {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getLong(key, 0)
    }

    fun getIntegerPreference(context: Context?, key: String?): Int {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getInt(key, 0)
    }

    fun getBooleanPreference(context: Context?, key: String?): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getBoolean(key, false)
    }

    fun putPreference(context: Context?, key: String?, value: String?) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun putPreference(context: Context?, key: String?, value: Float) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        editor.putFloat(key, value)
        editor.apply()
        editor.commit()
    }

    fun putPreferenceCallback(context: Context?, key: String?, value: String?): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        editor.putString(key, value)
        editor.apply()
        return editor.commit()
    }

    fun putPreference(context: Context?, key: String?, value: Long) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        editor.putLong(key, value)
        editor.apply()
        editor.commit()
    }

    fun putPreference(context: Context?, key: String?, value: Boolean) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        editor.putBoolean(key, value)
        editor.apply()
        editor.commit()
    }

    fun putPreference(context: Context?, key: String?, value: Int) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPrefs.edit()
        editor.putInt(key, value)
        editor.apply()
        editor.commit()
    }

    fun currentDateTime(): String {
        return SimpleDateFormat(
            Constants.TIMESTAMP_FORMAT,
            Locale.US
        ).format(
            Date()
        )
    }

    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(v: View?, message: String?) {
        Snackbar.make(v!!, message!!, Snackbar.LENGTH_SHORT).show()
    }
    
    fun appVersion(context: Context): String {
        var version = ""
        try {
            val pInfo: PackageInfo =
                context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            version = BuildConfig.VERSION_NAME
            e.printStackTrace()
        }
        return version
    }
    
    fun getOprName(context: Context): String {
        var result = ""
        try {
            result = (context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager)?.networkOperatorName ?: "unknown"
        } catch (e: PackageManager.NameNotFoundException) {
            result = "unknown"
            e.printStackTrace()
        }
        return result
    }

    fun serializeToJson(data: Any): String {
        val gson = Gson()
        return gson.toJson(data)
    }
    
    fun <T> deserializeFromJson(data: String): Any {
        val gson = Gson()
        val fooType: Type = object : TypeToken<Class<T?>?>() {}.type
        return gson.fromJson(data, fooType)
    }
}