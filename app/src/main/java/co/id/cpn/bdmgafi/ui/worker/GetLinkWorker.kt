package co.id.cpn.bdmgafi.ui.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.util.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetLinkWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val TAG = "DownloadWorker"
    }

    override suspend fun doWork(): Result {
        var regionSid: String? = null
        var distributionSid: String? = null
        try {
            val args = inputData
            regionSid = args.getString(Constants.REGION)
            distributionSid = args.getString(Constants.DISTRIBUTION)
            Log.w(TAG, "doWork getLink: executed $regionSid")

            /*
            val tagProgress = dataInput + "_progress"
            val tagProgressMax = dataInput + "_progress_max"
            val tagProgressPercent = dataInput + "_progress_percent"
            val firstUpdate = workDataOf(tagProgress to 0, tagProgressMax to 0, tagProgressPercent to 0)
            setProgress(firstUpdate)
            */
            
            val body = JsonObject()
            val regions = JsonArray()
            regions.add(regionSid)
            body.addProperty("distribution_sid", distributionSid)
            body.add("list_region", regions)

            var sqLite: Result? = null

            getCustomerSQLite(
                body = body,
                token = SharedPref(applicationContext).token,
            ).collect { data ->
                val outputData = Data.Builder()
                    .putString(regionSid!!, data)
                    .build()
                sqLite = Result.success(outputData)
            }
            
            return sqLite!!
        } catch (exception: Exception) {
            Log.e(TAG, "Error Work", exception)
            return Result.failure()
        }
    }

    fun getCustomerSQLite(body: JsonObject, token: String): Flow<String> =
        flow {
            val response = AppUtils.getApi().getCustomerSqliteWork(body = body, token = token)
            try {
                if (response.body()?.status == Constants.RESPONSE_OK) {
                    emit(response.body()!!.data.dataZip)
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