package co.id.cpn.bdmgafi.ui.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Resource
import co.id.cpn.entity.login.GetSqliteResponse
import co.id.cpn.entity.util.Constants
import co.id.cpn.entity.util.Utils
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class GetLinkWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        private const val TAG = "DownloadWorker"
    }

    override suspend fun doWork(): Result {
        var dataInput: String? = null
        try {
            val args = inputData
            dataInput = args.getString(Constants.INPUT_WORK)
            Log.w(TAG, "doWork getLink: executed $dataInput")

            val tagProgress = dataInput + "_progress"
            val tagProgressMax = dataInput + "_progress_max"
            val tagProgressPercent = dataInput + "_progress_percent"
            
            val firstUpdate = workDataOf(tagProgress to 0, tagProgressMax to 0, tagProgressPercent to 0)
            setProgress(firstUpdate)
            
            val body = JsonObject()
            val regions = JsonArray()
            regions.add("c2c2041a-e5f2-422b-aacf-012e75e29ad4")
            body.addProperty("distribution_sid", "3FE783FA-FC2B-4AAD-99E8-8E24B52C0836")
            body.add("list_region", regions)

            var sqLite: Result? = null

            getCustomerSQLite(
                body = body,
                token = SharedPref(applicationContext).token,
            ).collect { data ->
                val outputData = Data.Builder()
                    .putString(dataInput!!, data)
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