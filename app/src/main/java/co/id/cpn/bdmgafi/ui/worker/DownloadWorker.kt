package co.id.cpn.bdmgafi.ui.worker

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.bdmgafi.util.DbMaster
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.util.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        var sqLiteImporter: DbMaster? = null
        sqLiteImporter = DbMaster(applicationContext)

        var regionSid: String? = null
        var distributionSid: String? = null
        try {
            Log.w("TAG", "doWork: download worker")

            val args = inputData
            regionSid = args.getString(Constants.REGION)
            distributionSid = args.getString(Constants.DISTRIBUTION)
            
            val tagProgress = regionSid + "_progress"
            val tagProgressMax = regionSid + "_progress_max"
            val tagProgressPercent = regionSid + "_progress_percent"
            
            val firstUpdate = workDataOf(tagProgress to 0, tagProgressMax to 0, tagProgressPercent to 0)
            setProgress(firstUpdate)

            val body = JsonObject()
            val regions = JsonArray()
            regions.add(regionSid)
            body.addProperty("distribution_sid", distributionSid)
            body.add("list_region", regions)

            var sqLiteResult: Result? = Result.failure()

            try {
                getCustomerSQLite(
                    body = body,
                    token = SharedPref(applicationContext).token,
                ).collectLatest { data ->
//                    val patUrl: String = SharedPref(applicationContext).linkDownload
                    val patUrl: String = data
                    Log.w("TAG", "link: $patUrl")
                    val db = "${regionSid}.zip"
                    val url = URL(patUrl)
                    val c = url.openConnection() as HttpURLConnection
                    c.connect()
                    val PATH = Environment.getExternalStorageDirectory().toString() + Constants.PATH_TEMP
                    val file = File(PATH)
                    if (!file.exists()) {
                        file.mkdirs()
                    }
                    val outputFile = File(file, db)
                    if (outputFile.exists()) {
                        outputFile.delete()
                    }
                    val fos = FileOutputStream(outputFile, true)
                    //           InputStream is = c.getInputStream();
                    val `is`: InputStream = BufferedInputStream(c.inputStream, 1024)
                    val size = c.contentLength / (1024)
                    val buffer = ByteArray(1024 * 4)
                    var downloaded: Long = 0
                    var len1: Int
                    while (`is`.read(buffer).also { len1 = it } != -1) {
                        downloaded += len1
                        val progress = ((downloaded / (1024)) * 100 / size)
                        setProgress(workDataOf(tagProgress to downloaded / (1024), tagProgressMax to size, tagProgressPercent to progress))
                        fos.write(buffer, 0, len1)
                    }
                    fos.flush()
                    fos.close()
                    `is`.close()
                    if (outputFile.length() > 0) {
                        if (sqLiteImporter.unpackZip(
                                Environment.getExternalStorageDirectory().toString() + Constants.PATH_TEMP,
                                "${regionSid}.zip",
                                "${regionSid}.db"
                            )
                        ) {
                            setProgress(workDataOf(tagProgress to downloaded, tagProgressMax to size, tagProgressPercent to size))
                            sqLiteImporter.deleteDatabaseFile("${regionSid}.zip")
                            sqLiteResult = Result.success()
                        } else {
                            sqLiteResult = Result.failure()
                        }
                    }
                }
                return sqLiteResult!!
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                setProgress(firstUpdate)
                return Result.failure()
            }
        } catch (exception: Exception) {
            Log.e("TAG", "Error Work", exception)
            return Result.failure()
        }
    }

    fun getCustomerSQLite(body: JsonObject, token: String): Flow<String> =
        flow {
            val response = AppUtils.getApi().getCustomerSqliteWork(body = body, token = token)
            try {
                if (response.body()?.status == Constants.RESPONSE_OK) {
                    emit(response.body()!!.data.dataZip)
                } else {
                    if (response.code() == Constants.CODE_UNAUTHORIZED) {
                        val tokenResponse = AppUtils.getApi().getToken(auth = SharedPref(applicationContext).user.authorization)
                        if (tokenResponse.body()?.status == Constants.RESPONSE_OK) {
                            val tokenData = tokenResponse.body()!!.data.asJsonObject.get("TokenAuth").asString
                            SharedPref(applicationContext).token = tokenData
                            val responseRetrying = AppUtils.getApi().getCustomerSqliteWork(body = body, token = tokenData)
                            Log.w("token", "${tokenResponse.body()!!.data.get("data").asJsonObject.get("TokenAuth").toString()}", )
                            if (responseRetrying.body()?.status == Constants.RESPONSE_OK) {
                                emit(responseRetrying.body()!!.data.dataZip)
                            }
                        }
                    }
                }
            } catch (t: Throwable) {
                if (response.code() == Constants.CODE_UNAUTHORIZED) {
                    emit("You are not authorized")
                } else {
                    emit(t.localizedMessage)
                }
            }
        }.flowOn(Dispatchers.IO)
}