package co.id.cpn.bdmgafi.ui.downloader

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import androidx.work.WorkInfo
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ItemDownloadCustomerBinding
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.bdmgafi.ui.worker.DownloadRegionOperations
import co.id.cpn.entity.Region
import co.id.cpn.entity.util.Utils
import co.id.cpn.entity.util.Utils.createDir
import com.google.gson.JsonParser
import java.util.*

class DownloadCustomerAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: MainViewModel,
    private val distribution: String?
) : ListAdapter<Region, DownloadCustomerAdapter.ListItemViewHolder>(
    diffCallback
) {
    var list = arrayListOf<Region>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ItemDownloadCustomerBinding.inflate(LayoutInflater.from(parent.context))
        return ListItemViewHolder(context, lifecycleOwner, viewModel, binding, distribution ?: "")
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(
        val context: Context,
        val lifecycleOwner: LifecycleOwner,
        val viewModel: MainViewModel,
        val binding: ItemDownloadCustomerBinding,
        val distribution: String
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Region?) {

            binding.textCustomer.text = item!!.regionName
            binding.textProgressSize.text = item.downloadInfo
            binding.reDownload.visibility = View.INVISIBLE

            when (item.downloadStatus) {
                "DOWNLOADED" -> {
                    binding.reDownload.visibility = View.VISIBLE
                    binding.download.visibility = View.INVISIBLE
                    binding.progressBarInit.visibility = View.INVISIBLE
                    binding.progress.max = 100
                    binding.progress.progress = 100
                }
                "QUEUE" -> {
                    binding.progressBarInit.visibility = View.VISIBLE
                    binding.download.visibility = View.INVISIBLE
                    binding.progress.visibility = View.VISIBLE
                }
                "DOWNLOADING" -> {
                    binding.progressBarInit.visibility = View.INVISIBLE
                    binding.download.visibility = View.INVISIBLE
                    binding.progress.progress = item.progress
                    binding.progress.max = item.size
                    binding.progress.secondaryProgress = item.size
                }
                "FAILED" -> {
                    binding.reDownload.visibility = View.INVISIBLE
                    binding.download.visibility = View.VISIBLE
                    binding.progressBarInit.visibility = View.INVISIBLE
                }
            }

            binding.download.setOnClickListener {
                processDownload(binding, item, viewModel, lifecycleOwner, context)
            }

            if (item.isSelected) {
                processDownload(binding, item, viewModel, lifecycleOwner, context)
            }

//            binding.reDownload.setOnClickListener(object : View.OnClickListener(item) {
//                /* synthetic */ var `f$1`: Region? = null
//                override fun onClick(view: View) {
//                    `m642bind$lambda4`(this@ListItemViewHolder, `f$1`, view)
//                }
//
//                init {
//                    `f$1` = r2
//                }
//            })
        }

        /* access modifiers changed from: private */
        fun processDownload(
            binding: ItemDownloadCustomerBinding,
            item: Region,
            viewModel: MainViewModel,
            lifecycleOwner: LifecycleOwner,
            context: Context
        ) {
            createDir()

            val tagDownload = item.regionSID + "_download"
            val tagImport = item.regionSID + "_import"

            viewModel.cancelWorkByTag(tagDownload)

            binding.progressBarInit.visibility = View.VISIBLE
            binding.download.visibility = View.INVISIBLE

            viewModel.applyDownloadCustomer(
                DownloadRegionOperations.Builder(context, item).build()
            )
            viewModel.outputStatus(tagDownload).observe(lifecycleOwner, { workInfo ->
                var progressPercent = 0
                var progress = 0
                var progressMax = 0
                if (workInfo.toString() != "[]") {
                    when (workInfo[0].state) {
                        WorkInfo.State.FAILED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "FAILED",
                                "Failed, please try again",
                                0,
                                0
                            )
                        }
                        WorkInfo.State.CANCELLED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "FAILED",
                                "Failed, please try again",
                                0,
                                0
                            )
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "DOWNLOADING",
                                "Completing...",
                                100,
                                100
                            )
                        }
                        WorkInfo.State.RUNNING -> {

                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "WAITING",
                                "Initialing data...",
                                0,
                                0
                            )

                            val data: Data = workInfo[0].progress

                            val jsonObject =
                                JsonParser().parse(Utils.serializeToJson((data as Any))).asJsonObject["mValues"].asJsonObject

                            if (jsonObject != null) {
                                progressPercent =
                                    if (jsonObject.get("${item.regionSID}_progress_percent") == null) 0 else jsonObject.get(
                                        "${item.regionSID}_progress_percent"
                                    ).asInt
                                progress =
                                    if (jsonObject.get("${item.regionSID}_progress") == null) 0 else jsonObject.get(
                                        "${item.regionSID}_progress"
                                    ).asInt
                                progressMax =
                                    if (jsonObject.get("${item.regionSID}_progress_max") == null) 0 else jsonObject.get(
                                        "${item.regionSID}_progress_max"
                                    ).asInt

                                Log.w("processDownload", ": $jsonObject")

                                if (progress > 0) {
                                    val stringBuilder = StringBuilder()
                                    stringBuilder.append(progress as Any?)
                                    stringBuilder.append('/')
                                    stringBuilder.append(progressMax)
                                    stringBuilder.append("KB \u2022 ")
                                    stringBuilder.append(progressPercent)
                                    stringBuilder.append('%')
                                    viewModel.updateDownloadStatus(
                                        item.regionSID,
                                        "DOWNLOADING",
                                        stringBuilder.toString(),
                                        progressMax,
                                        progress
                                    )
                                }

                                if (progress == 100) {
                                    viewModel.updateDownloadStatus(
                                        item.regionSID,
                                        "DOWNLOADING",
                                        "Completiong...",
                                        100,
                                        100
                                    )
                                }

                            }
                        }
                        WorkInfo.State.ENQUEUED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "QUEUE",
                                "Waiting in Queue...",
                                0,
                                0
                            )
                        }
                        WorkInfo.State.BLOCKED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "QUEUE",
                                "Waiting in Queue...",
                                0,
                                0
                            )
                        }
                        else -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "WAITING",
                                "Initialing data...",
                                0,
                                0
                            )
                        }
                    }
                }
            })

            val progressDialog = ProgressDialog(context, R.style.ProgressDialogStyle)

            viewModel.outputStatus(tagImport).observe(lifecycleOwner, { workInfo ->
                if (workInfo.toString() != "[]") {
                    when (workInfo[0].state) {
                        WorkInfo.State.FAILED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "FAILED",
                                "Failed, please try again",
                                0,
                                0
                            )
                        }
                        WorkInfo.State.CANCELLED -> {
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "FAILED",
                                "Failed, please try again",
                                0,
                                0
                            )
                        }
                        WorkInfo.State.SUCCEEDED -> {
                            progressDialog.dismiss()
                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "DOWNLOADED",
                                "downloaded",
                                0,
                                0
                            )
                        }
                        WorkInfo.State.RUNNING -> {

                            progressDialog.setTitle("Importing")
                            progressDialog.setMessage("(${item.regionName}), please wait...")
                            progressDialog.setCancelable(false)
                            if (!progressDialog.isShowing) {
                                progressDialog.show()
                            }

                            viewModel.updateDownloadStatus(
                                item.regionSID,
                                "DOWNLOADING",
                                "Completing, Please wait...",
                                100,
                                100
                            )

                        }
                    }
                }
            })
        }
    }

    fun addData(listData: List<Region>) {
        list = listData as ArrayList<Region>
        submitList(list)
    }

    fun clear() {
        list.clear()
        submitList(null as List<Region>?)
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Region>() {
            override fun areItemsTheSame(
                oldItem: Region,
                newItem: Region
            ): Boolean {
                return oldItem.regionSID == newItem.regionSID
            }

            override fun areContentsTheSame(
                oldItem: Region,
                newItem: Region
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}