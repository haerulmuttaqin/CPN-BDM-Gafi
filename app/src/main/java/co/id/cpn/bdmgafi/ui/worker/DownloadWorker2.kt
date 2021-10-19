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

class DownloadWorker2(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val Progress = "Progress"
        private const val delayDuration = 100L
    }

    override suspend fun doWork(): Result {
        try {
            Log.e("WORK DOWNLOAD", "Success Work")
            return Result.success()
        } catch (exception: Exception) {
            Log.e("WORK DOWNLOAD", "Error Work", exception)
            return Result.failure()
        }
    }
}