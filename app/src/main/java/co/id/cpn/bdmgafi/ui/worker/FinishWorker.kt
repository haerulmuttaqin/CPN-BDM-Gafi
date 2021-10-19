package co.id.cpn.bdmgafi.ui.worker

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import co.id.cpn.bdmgafi.ui.customer.CustomerActivity
import co.id.cpn.bdmgafi.ui.dashboard.DashboardActivity
import co.id.cpn.bdmgafi.util.DownloadDataSqlite
import co.id.cpn.entity.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class FinishWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.w("TAG", "doWork: finish worker ==========================================")
//            applicationContext.startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
            Result.success()
        } catch (exception: Exception) {
            Log.e("TAG", "Error Work", exception)
            Result.failure()
        }
    }
}