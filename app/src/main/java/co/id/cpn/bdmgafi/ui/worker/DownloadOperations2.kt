package co.id.cpn.bdmgafi.ui.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.Region
import co.id.cpn.entity.util.Constants
import co.id.cpn.entity.util.Utils
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject

/**
 * Builds and holds WorkContinuation based on supplied filters.
 */
internal class DownloadOperations2 private constructor(val continuation: WorkContinuation) {

    internal class Builder(
        private val context: Context
    ) {
        /**
         * Creates the [WorkContinuation] depending on the list of selected filters.
         *
         * @return the instance of [WorkContinuation].
         */
        @SuppressLint("EnqueueWork", "SetTextI18n")
        fun build(): DownloadOperations2 {

            Log.w("WORK continuation", "build")
            
            val continuation = WorkManager.getInstance(context)
                .beginUniqueWork(
                    Constants.DOWNLOAD_WORK,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,
                    OneTimeWorkRequest.from(InitWorker::class.java)
                )
            
            return DownloadOperations2(continuation)
        }

        private fun createInputData(downloadLink: String): Data {
            return workDataOf(Constants.INPUT_WORK to downloadLink)
        }
    }
}
