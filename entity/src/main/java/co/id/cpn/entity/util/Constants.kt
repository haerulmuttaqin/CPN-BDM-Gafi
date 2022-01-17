package co.id.cpn.entity.util

import co.id.cpn.entity.BuildConfig

object Constants {
    const val PROGRESS = "progress"
    const val RCTL_SID = 2154
    const val DB_NAME = "bdm_gafi.db"
    const val RESPONSE_OK = "OK"
    const val RESPONSE_NO = "NO"
    const val TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val MASTER_DB = "bdm-gafi.db"
    const val MASTER_DB_ZIP = "bdm-gafi.zip"
    const val PATH = "/SADIX-GAFI/"
    const val PATH_IMG = "$PATH/img/"
    const val PATH_DOWNLOAD = "$PATH/download/"
    const val PATH_TEMP = "$PATH/.temp/"
    const val DOWNLOAD_WORK = "download_work_gafi_application"
    const val INPUT_WORK = "input_work_gafi_application"
    const val SQLITE_LINK_WORK = "sqlite_link"
    const val DELAY_TIME_MILLIS: Long = 300
    const val OUTPUT_WORK = "sqlite_link"
    const val BASE_URL = BuildConfig.BASE_URL
    const val CONTENT_TYPE = "Content-Type"
    const val APP_JSON = "application/json"
    /*Key*/
    const val KEY_AUTHORIZATION = "Authorization"
    const val KEY_RCTL_SID = "RctlSID"
    const val KEY_TOKEN = "Token"
    const val KEY_USERNAME = "username"
    const val KEY_PASSWORD = "password"
    const val KEY_KEY_S = "keyS"
    const val KEY_KEY_H = "keyH"
    const val KEY_APPVERSION = "appversion"
    const val KEY_OPRNAME = "oprname"
    const val KEY_GEOLANG = "geolang"
    const val KEY_GEOLAT = "geolat"
    const val KEY_CLIENTDATE = "clientdate"
    const val KEY_STATUS = "status"
    const val KEY_DATA = "data"
    
    /*Response Code*/
    const val CODE_UNAUTHORIZED = 401
    const val CODE_SUCCESS = 200
    const val CODE_INTERNAL_ERROR = 500
}