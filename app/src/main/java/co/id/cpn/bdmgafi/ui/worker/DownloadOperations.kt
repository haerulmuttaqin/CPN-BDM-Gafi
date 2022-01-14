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
internal class DownloadOperations private constructor(val continuation: WorkContinuation) {

    internal class Builder(
        private val context: Context,
        private val regions: List<Region>,
        private val downloadContainer: LinearLayout,
        private val downloadList: ArrayList<LinearLayout>,
        private val viewModel: MainViewModel,
        private val lifecycleOwner: LifecycleOwner,
    ) {
        /**
         * Creates the [WorkContinuation] depending on the list of selected filters.
         *
         * @return the instance of [WorkContinuation].
         */
        @SuppressLint("EnqueueWork", "SetTextI18n")
        fun build(): DownloadOperations {
            var continuation = WorkManager.getInstance(context)
                .beginUniqueWork(
                    Constants.DOWNLOAD_WORK,
                    ExistingWorkPolicy.REPLACE,
                    OneTimeWorkRequest.from(InitWorker::class.java)
                )

            regions.forEachIndexed { index, region ->
                val downloadItem = LayoutInflater.from(context)
                    .inflate(
                        R.layout.item_download_sqlite,
                        downloadContainer,
                        false
                    )  as LinearLayout
                val textRegion = downloadItem.findViewById<TextView>(R.id.textCustomer)
                val textProgressPercent =
                    downloadItem.findViewById<TextView>(R.id.textProgressPercent)
                val textProgressSize = downloadItem.findViewById<TextView>(R.id.textProgressSize)
                val progressBarInit = downloadItem.findViewById<LottieAnimationView>(R.id.progressBarInit)
                val progressBarDownloading = downloadItem.findViewById<LottieAnimationView>(R.id.progressBarDownloading)
                val progressBar = downloadItem.findViewById<ProgressBar>(R.id.progressBar)
                textRegion.text = region.regionName
                progressBar.progress = 0
                textProgressPercent.text = "Waiting in Queue..."

                downloadList.add(downloadItem)
                downloadContainer.addView(downloadItem)

                val tag = region.regionSID + "_TAG"
                val getLink = OneTimeWorkRequestBuilder<GetLinkWorker>()
                    .setInputData(createInputData(region.regionSID))
                    .addTag(tag)
                    .build()
                continuation = continuation.then(getLink)


                viewModel.outputStatus(tag).observe(lifecycleOwner, { listOfInfos ->
                    textProgressPercent.text = "Initialing data..."
                    if ("$listOfInfos" != "[]") {
                        val info = listOfInfos[0]
                        val finished = info.state.isFinished
                        if (finished) {
                            when (info.state) {
                                WorkInfo.State.ENQUEUED -> {

                                    textProgressPercent.text = "Waiting in Queue..."
                                    progressBar.progress = 0

                                }
                                WorkInfo.State.RUNNING -> {

                                    textProgressPercent.text = "Initialing data..."

                                }
                                WorkInfo.State.SUCCEEDED -> {

                                    /*Step download file*/
                                    val tagDownload = info.outputData.getString(region.regionSID)
                                    Log.w("WORK_STATUS", "" + tagDownload)
                                    SharedPref(context).linkDownload = tagDownload!!
//                                    SharedPref(context).linkDownload = "http://103.100.27.46/sqlite${index+1}.zip"
//                                    SharedPref(context).linkDownload = "http://212.183.159.230/10MB.zip"

                                }
                                WorkInfo.State.FAILED -> {
                                    val failureOutputData = info.outputData
                                    Log.w("WORK_STATUS", "" + failureOutputData)
                                    textProgressPercent.text = "Failed, please try again"
                                }
                                else -> {
                                    textProgressPercent.text = "Waiting in Queue..."
                                    progressBar.max = 100
                                    progressBar.progress = 0
                                }
                            }
                            progressBarInit.visibility = View.INVISIBLE
                        } else {
                            progressBarInit.visibility = View.VISIBLE
                        }
                    }
                })

                val tagDownload = region.regionSID
                val download = OneTimeWorkRequestBuilder<DownloadWorker>()
                    .setInputData(createInputData(tagDownload))
                    .addTag(tagDownload)
                    .build()
                continuation = continuation.then(download)

                viewModel.outputStatus(tagDownload).observe(lifecycleOwner, { listOfInfo ->
                    progressBar.progress = 0
                    textProgressPercent.text = "Waiting in Queue..."
                    if ("$listOfInfo" != "[]") {

                        val info = listOfInfo[0]
                        val progress = info.progress
                        val jsonProgress: JsonObject? = JsonParser().parse(Utils.serializeToJson(progress)).asJsonObject.get("mValues").asJsonObject
                        val valueProgressPercent = jsonProgress?.get(tagDownload + "_progress_percent")?.asInt
                        val valueProgressMax = jsonProgress?.get(tagDownload + "_progress_max")?.asInt
                        val valueProgress = jsonProgress?.get(tagDownload + "_progress")?.asInt
                        
                        when (info.state) {
                            WorkInfo.State.ENQUEUED -> {
                                
                                textProgressPercent.text = "Initialing data..."

                            }
                            WorkInfo.State.RUNNING -> {
                                if (valueProgress != null) {
                                    progressBarDownloading.visibility = View.VISIBLE
                                    progressBarInit.visibility = View.INVISIBLE
                                    progressBar.progress = "$valueProgress".toInt()
                                    progressBar.max = 100
                                    textProgressPercent.text = "$valueProgressPercent%"
                                    textProgressSize.text = "${valueProgress}/${valueProgressMax}KB"   
                                }
                            }
                            WorkInfo.State.SUCCEEDED -> {
                                viewModel.assetsTemp.observe(
                                    lifecycleOwner,
                                    { viewModel.insertAsset(it) })
                                viewModel.customersTemp.observe(
                                    lifecycleOwner,
                                    { viewModel.insertCustomer(it) })
                                viewModel.customerTypesTemp.observe(
                                    lifecycleOwner,
                                    { viewModel.insertCustomerType(it) })
                                viewModel.productsTemp.observe(
                                    lifecycleOwner,
                                    { viewModel.insertProduct(it) })
                                progressBar.progress = 100
                                textProgressPercent.text = "100%"
                                progressBarDownloading.visibility = View.INVISIBLE
                            }
                            WorkInfo.State.FAILED -> {
                                progressBarDownloading.visibility = View.INVISIBLE
                                val failureOutputData = info.outputData
                                textProgressPercent.text = "Failed, please try again"
                                Log.w("WORK_STATUS", "" + failureOutputData)
                            }
                        }
                    }
                })
            }

            val finishWorker = OneTimeWorkRequestBuilder<FinishWorker>()
                .addTag("TAG_FINISH")
                .build()
            continuation = continuation.then(finishWorker)
            
            return DownloadOperations(continuation)
        }

        private fun createInputData(downloadLink: String): Data {
            return workDataOf(Constants.INPUT_WORK to downloadLink)
        }
    }
}
