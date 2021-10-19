package co.id.cpn.bdmgafi.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import co.id.cpn.bdmgafi.R
import co.id.cpn.data.remote.ApiServices
import co.id.cpn.entity.util.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppUtils {
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

    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(v: View?, message: String?) {
        Snackbar.make(v!!, message!!, Snackbar.LENGTH_SHORT).show()
    }

    fun showDialog(context: Context, title: String?, message: String?, image: Int) {
        val view: View = (context as Activity).layoutInflater.inflate(R.layout.dialog_info, null)
        val a = view.findViewById<Button>(R.id.a_btn)
        val i = view.findViewById<ImageView>(R.id.image)
        val t = view.findViewById<TextView>(R.id.title)
        val m = view.findViewById<TextView>(R.id.message)
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        if (image != 0) {
            i.setImageResource(image)
        }
        t.text = title
        m.text = message
        a.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(view)
        dialog.show()
    }

    fun errorDialog(context: Context, message: String?) {
        val view: View = (context as Activity).layoutInflater.inflate(R.layout.dialog_info, null)
        val a = view.findViewById<Button>(R.id.a_btn)
        val i = view.findViewById<ImageView>(R.id.image)
        val t = view.findViewById<TextView>(R.id.title)
        val m = view.findViewById<TextView>(R.id.message)
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        i.setImageResource(R.drawable.id_cloud_error)
        i.setColorFilter(Color.LTGRAY)
        t.text = context.getString(R.string.something_wrong)
        m.text = message
        a.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(view)
        dialog.show()
    }

    fun showDialog(
        context: Context,
        title: String?,
        message: String?,
        image: Int,
        listener: ConfirmListener?
    ) {
        val view = (context as Activity).layoutInflater.inflate(R.layout.dialog_info, null)
        val a = view.findViewById<Button>(R.id.a_btn)
        val i = view.findViewById<ImageView>(R.id.image)
        val t = view.findViewById<TextView>(R.id.title)
        val m = view.findViewById<TextView>(R.id.message)
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        i.setImageResource(image)
        t.text = title
        m.text = message
        a.setOnClickListener { v: View? ->
            dialog.dismiss()
            listener?.onDialogCompleted(true)
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    fun confirmDialog(
        context: Context,
        title: String?,
        message: String?,
        icon: Int,
        btnLeft: String?,
        btnRight: String?,
        listener: LeftRightConfirmListener?
    ) {
        val view: View = (context as Activity).layoutInflater.inflate(R.layout.dialog_confirm, null)
        val a = view.findViewById<Button>(R.id.a_btn)
        val b = view.findViewById<Button>(R.id.b_btn)
        val i = view.findViewById<ImageView>(R.id.image)
        val t = view.findViewById<TextView>(R.id.title)
        val m = view.findViewById<TextView>(R.id.message)
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        i.setImageResource(icon)
        t.text = title
        m.text = message
        b.text = btnRight
        b.setOnClickListener { v: View? ->
            dialog.dismiss()
            listener?.onRightRight()
        }
        a.text = btnLeft
        a.setOnClickListener { v: View? ->
            dialog.dismiss()
            listener?.onLeftClick()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    interface ConfirmListener {
        fun onDialogCompleted(answer: Boolean)
    }

    interface LeftRightConfirmListener {
        fun onLeftClick()
        fun onRightRight()
    }


    fun getAPIClient(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(provideOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    fun getApi(): ApiServices {
        return getAPIClient().create(ApiServices::class.java)
    }

    private fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val builder = chain.request().newBuilder()
                builder.addHeader(Constants.CONTENT_TYPE, Constants.APP_JSON)
                builder.method(original.method(), original.body())
                chain.proceed(builder.build())
            }
            .addNetworkInterceptor(provideLoggingInterceptor())
            .build()
    }

    private fun provideLoggingInterceptor(): Interceptor {
        val localHttpLoggingInterceptor = HttpLoggingInterceptor()
        localHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return localHttpLoggingInterceptor
    }

}