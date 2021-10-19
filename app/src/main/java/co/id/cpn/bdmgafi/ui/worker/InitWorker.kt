package co.id.cpn.bdmgafi.ui.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import co.id.cpn.bdmgafi.util.DownloadDataSqlite
import co.id.cpn.entity.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class InitWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.w("TAG", "doWork: init worker")
            Result.success()
        } catch (exception: Exception) {
            Log.e("TAG", "Error Work", exception)
            Result.failure()
        }
    }
}