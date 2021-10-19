package co.id.cpn.bdmgafi.ui.worker

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import co.id.cpn.bdmgafi.util.DbMaster
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.util.Constants
import kotlinx.coroutines.delay
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 100L
    }

    override suspend fun doWork(): Result {
        var sqLiteImporter: DbMaster? = null
        sqLiteImporter = DbMaster(applicationContext)

        var dataInput: String? = null
        try {
            val args = inputData
            dataInput = args.getString(Constants.INPUT_WORK)
            
            val tagProgress = dataInput + "_progress"
            val tagProgressMax = dataInput + "_progress_max"
            val tagProgressPercent = dataInput + "_progress_percent"
            
            val firstUpdate = workDataOf(tagProgress to 0, tagProgressMax to 0, tagProgressPercent to 0)
            setProgress(firstUpdate)
            delay(delayDuration)
            
            try {
                val patUrl: String = SharedPref(applicationContext).linkDownload
                Log.w("TAG", "link: $patUrl")
                val db = Constants.MASTER_DB_ZIP
                val url = URL(patUrl)
                val c = url.openConnection() as HttpURLConnection
                c.connect()
                val PATH = Environment.getExternalStorageDirectory().toString() + Constants.PATH
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
                    delay(delayDuration)
                    fos.write(buffer, 0, len1)
                }
                fos.flush()
                fos.close()
                `is`.close()
                if (outputFile.length() > 0) {
                    if (sqLiteImporter.unpackZip(
                            Environment.getExternalStorageDirectory().toString() + Constants.PATH,
                            Constants.MASTER_DB_ZIP,
                            Constants.MASTER_DB
                        )
                    ) {
                        if (sqLiteImporter.importDataBase()) {
                            sqLiteImporter.deleteMasterData()
                            setProgress(workDataOf(tagProgress to downloaded, tagProgressMax to size, tagProgressPercent to size))
                            return Result.success()
                        }
                    }
                    return Result.failure()
                }
                return Result.failure()
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
}