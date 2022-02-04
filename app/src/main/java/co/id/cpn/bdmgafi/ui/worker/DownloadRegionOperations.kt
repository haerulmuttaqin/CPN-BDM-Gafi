package co.id.cpn.bdmgafi.ui.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.*
import co.id.cpn.entity.Region
import co.id.cpn.entity.util.Constants

/**
 * Builds and holds WorkContinuation based on supplied filters.
 */
internal class DownloadRegionOperations private constructor(val continuation: WorkContinuation) {

    internal class Builder(
        private val context: Context,
        private val region: Region
    ) {
        /**
         * Creates the [WorkContinuation] depending on the list of selected filters.
         *
         * @return the instance of [WorkContinuation].
         */
        @SuppressLint("EnqueueWork", "SetTextI18n")
        fun build(): DownloadRegionOperations {

            Log.w("WORK continuation", "build")
            
            val continuation = WorkManager.getInstance(context)
                .beginUniqueWork(
                    Constants.DOWNLOAD_WORK,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,
                    OneTimeWorkRequest.from(InitWorker::class.java)
                )
            
            return DownloadRegionOperations(continuation)
        }

        private fun createInputData(downloadLink: String): Data {
            return workDataOf(Constants.INPUT_WORK to downloadLink)
        }
    }
}
