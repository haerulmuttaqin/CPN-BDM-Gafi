package co.id.cpn.data.local.spref

import android.content.Context
import co.id.cpn.entity.TaskSQLite
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import com.google.gson.Gson

class SharedPref(context: Context) {
    private val gson = Gson()
    private val INSTANCE by lazy {
        context.getSharedPreferences(SPFKeys.NAME, Context.MODE_PRIVATE)
    }

    var haveShowSplash: Boolean
        get() = INSTANCE.getBoolean(SPFKeys.SPLASH, false)
        set(value) {
            INSTANCE.edit().putBoolean(SPFKeys.SPLASH, value).apply()
        }

    var loggedIn: Boolean
        get() = INSTANCE.getBoolean(SPFKeys.LOGIN, false)
        set(value) {
            INSTANCE.edit().putBoolean(SPFKeys.LOGIN, value).apply()
        }

    var user: LoginResponse
        get() {
            val json = INSTANCE.getString(SPFKeys.USER, "{}")!!
            return gson.fromJson<LoginResponse>(json, LoginResponse::class.java)
        }
        set(value) {
            INSTANCE.edit().putString(SPFKeys.USER, gson.toJson(value)).apply()
        }

    var token: String
        get() {
            return INSTANCE.getString(SPFKeys.TOKEN, "")!!
        }
        set(value) {
            INSTANCE.edit().putString(SPFKeys.TOKEN, value).apply()
        }

    var linkDownload: String
        get() {
            return INSTANCE.getString(SPFKeys.LINK_DOWNLOAD, "")!!
        }
        set(value) {
            INSTANCE.edit().putString(SPFKeys.LINK_DOWNLOAD, value).apply()
        }

    var userKey: LoginRequest
        get() {
            val json = INSTANCE.getString(SPFKeys.USER, "{}")!!
            return gson.fromJson<LoginRequest>(json, LoginRequest::class.java)
        }
        set(value) {
            INSTANCE.edit().putString(SPFKeys.USER, gson.toJson(value)).apply()
        }

    var taskSQLite: List<TaskSQLite>
        get() {
            val json = INSTANCE.getString(SPFKeys.SQLITE, "{}")!!
            return gson.fromJson<List<TaskSQLite>>(json, Array<TaskSQLite>::class.java).toList()
        }
        set(value) {
            INSTANCE.edit().putString(SPFKeys.SQLITE, gson.toJson(value)).apply()
        }

    /*var lastCoordinate: LatLng
        get() {
            val json = INSTANCE.getString(SPFKeys.LAST_COORDINATE, "{}")!!
            return gson.fromJson<LatLng>(json, LatLng::class.java)
        }
        set(value) {
            INSTANCE.edit().putString(SPFKeys.LAST_COORDINATE, gson.toJson(value)).apply()
        }*/
}