package co.id.cpn.bdmgafi.ui.downloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.databinding.ItemDownloadCustomerBinding
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.bdmgafi.ui.worker.DownloadRegionOperations
import co.id.cpn.entity.Region
import co.id.cpn.entity.util.Utils.createDir
import java.util.*

class DownloadCustomerAdapter(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: MainViewModel,
    private val distribution: String?
) : ListAdapter<Region, DownloadCustomerAdapter.ListItemViewHolder>(
    diffCallback
) {
    private var list = ArrayList<Region>()

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
            viewModel.applyDownloadCustomer(
                DownloadRegionOperations.Builder(context, item).build()
            )
            val tagDownload = item.regionSID + "_download"
//            viewModel2.outputStatus(tagDownload)
//                .observe(lifecycleOwner2!!, object : Observer<Any?>(tagDownload, viewModel2, item) {
//                    /* synthetic */ var `f$1`: String? = null
//                    /* synthetic */ var `f$2`: MainViewModel? = null
//                    /* synthetic */ var `f$3`: Region? = null
//                    override fun onChanged(obj: Any?) {
//                        `m643processDownload$lambda5`(
//                            this@ItemDownloadCustomerBinding,
//                            `f$1`,
//                            `f$2`,
//                            `f$3`,
//                            obj as List<*>?
//                        )
//                    }
//
//                    init {
//                        `f$1` = r2
//                        `f$2` = r3
//                        `f$3` = r4
//                    }
//                })
//            viewModel2.outputStatus(Intrinsics.stringPlus(item.regionSID, "_import")).observe(
//                lifecycleOwner2,
//                object : Observer<Any?>(
//                    ProgressDialog(
//                        binding2.download.context,
//                        C0550R.C0557style.ProgressDialogStyle
//                    ), viewModel2, item
//                ) {
//                    /* synthetic */ var `f$1`: ProgressDialog? = null
//                    /* synthetic */ var `f$2`: MainViewModel? = null
//                    /* synthetic */ var `f$3`: Region? = null
//                    override fun onChanged(obj: Any?) {
//                        `m644processDownload$lambda6`(
//                            this@ItemDownloadCustomerBinding,
//                            `f$1`,
//                            `f$2`,
//                            `f$3`,
//                            obj as List<*>?
//                        )
//                    }
//
//                    init {
//                        `f$1` = r2
//                        `f$2` = r3
//                        `f$3` = r4
//                    }
//                })
        }

//        companion object {
//            /* access modifiers changed from: private */ /* renamed from: bind$lambda-3  reason: not valid java name */
//            fun `m641bind$lambda3`(`this$0`: ListItemViewHolder, `$item`: Region?, it: View?) {
//                Intrinsics.checkNotNullParameter(`this$0`, "this$0")
//                Intrinsics.checkNotNullParameter(`$item`, "\$item")
//                `this$0`.processDownload(
//                    `this$0`.binding,
//                    `$item`,
//                    `this$0`.viewModel,
//                    `this$0`.lifecycleOwner,
//                    `this$0`.context
//                )
//            }
//
//            /* access modifiers changed from: private */ /* renamed from: bind$lambda-4  reason: not valid java name */
//            fun `m642bind$lambda4`(`this$0`: ListItemViewHolder, `$item`: Region?, it: View?) {
//                Intrinsics.checkNotNullParameter(`this$0`, "this$0")
//                Intrinsics.checkNotNullParameter(`$item`, "\$item")
//                confirmDialog(
//                    `this$0`.context,
//                    "Download Data",
//                    "This region has been downloaded, are you sure want to download again?",
//                    C0550R.C0552drawable.ic_undraw_download,
//                    "Cancel",
//                    "Yes",
//                    `DownloadCustomerAdapter$ListItemViewHolder$bind$3$1`(`this$0`, `$item`)
//                )
//            }
//            /* access modifiers changed from: private */ /* renamed from: processDownload$lambda-6  reason: not valid java name */
//            fun `m644processDownload$lambda6`(
//                `$binding`: ItemDownloadCustomerBinding,
//                `$progressDialog`: ProgressDialog?,
//                `$viewModel`: MainViewModel?,
//                `$item`: Region?,
//                listOfInfo: List<*>?
//            ) {
//                val progressDialog = `$progressDialog`
//                Intrinsics.checkNotNullParameter(`$binding`, "\$binding")
//                Intrinsics.checkNotNullParameter(`$progressDialog`, "\$progressDialog")
//                val mainViewModel = `$viewModel`
//                Intrinsics.checkNotNullParameter(`$viewModel`, "\$viewModel")
//                Intrinsics.checkNotNullParameter(`$item`, "\$item")
//                if (!Intrinsics.areEqual(listOfInfo.toString() as Any, "[]" as Any)) {
//                    `$binding`.progressBarInit.visibility = 0
//                    `$binding`.download.visibility = 4
//                    val info = listOfInfo!![0] as WorkInfo
//                    when (WhenMappings.`$EnumSwitchMapping$0`[info.state.ordinal]) {
//                        3 -> {
//                            `$progressDialog`!!.setMessage("Importing, please wait...")
//                            `$progressDialog`.setCancelable(false)
//                            if (!`$progressDialog`.isShowing) {
//                                `$progressDialog`.show()
//                            }
//                            `$viewModel`.updateDownloadStatus(
//                                `$item`!!.regionSID,
//                                "DOWNLOADING",
//                                "Completing, Please wait...",
//                                100,
//                                100
//                            )
//                            return
//                        }
//                        4 -> {
//                            `$progressDialog`!!.dismiss()
//                            `$viewModel`.updateDownloadStatus(
//                                `$item`!!.regionSID,
//                                "DOWNLOADED",
//                                "downloaded",
//                                0,
//                                0
//                            )
//                            return
//                        }
//                        5 -> {
//                            val failureOutputData = info.outputData
//                            Intrinsics.checkNotNullExpressionValue(
//                                failureOutputData,
//                                "info.outputData"
//                            )
//                            Log.w("WORK_STATUS", Intrinsics.stringPlus("", failureOutputData))
//                            `$progressDialog`!!.dismiss()
//                            `$viewModel`.updateDownloadStatus(
//                                `$item`!!.regionSID,
//                                "FAILED",
//                                "Failed, please try again",
//                                0,
//                                0
//                            )
//                            return
//                        }
//                        else -> return
//                    }
//                } else {
//                    val list = listOfInfo
//                }
//            }
//        }
//
//        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
//        init {
//            Intrinsics.checkNotNullParameter(context2, "context")
//            Intrinsics.checkNotNullParameter(lifecycleOwner2, "lifecycleOwner")
//            Intrinsics.checkNotNullParameter(viewModel2, "viewModel")
//            Intrinsics.checkNotNullParameter(binding2, "binding")
//            Intrinsics.checkNotNullParameter(distribution2, "distribution")
//            context = context2
//            lifecycleOwner = lifecycleOwner2
//            viewModel = viewModel2
//            binding = binding2
//            distribution = distribution2
//        }
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
                return oldItem == newItem
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