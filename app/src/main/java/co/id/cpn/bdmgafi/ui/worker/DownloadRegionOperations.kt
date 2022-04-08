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
class DownloadRegionOperations constructor(val continuation: WorkContinuation) {

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

            var continuation = WorkManager.getInstance(context)
                .beginUniqueWork(
                    Constants.DOWNLOAD_WORK,
                    ExistingWorkPolicy.APPEND_OR_REPLACE,
                    OneTimeWorkRequest.from(InitWorker::class.java)
                )

            val tag = region.regionSID + "_download"
            val download = OneTimeWorkRequestBuilder<DownloadWorker>()
                .setInputData(workDataOf(
                    Constants.REGION to region.regionSID,
                    Constants.DISTRIBUTION to region.distributionSid)
                )
                .addTag(tag)
                .build()
            continuation = continuation.then(download)

            val tagImport = region.regionSID + "_import"
            val import = OneTimeWorkRequestBuilder<ImportWorker>()
                .setInputData(workDataOf(
                    Constants.REGION to region.regionSID,
                    Constants.DISTRIBUTION to region.distributionSid)
                )
                .addTag(tagImport)
                .build()
            continuation = continuation.then(import)

            return DownloadRegionOperations(continuation)
        }
    }
}
