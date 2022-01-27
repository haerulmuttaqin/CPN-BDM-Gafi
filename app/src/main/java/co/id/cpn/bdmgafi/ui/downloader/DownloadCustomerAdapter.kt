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
import co.id.cpn.entity.Region
import co.id.cpn.entity.util.Utils.createDir
import java.util.*
import kotlin.jvm.internal.Intrinsics

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
            Intrinsics.checkNotNullParameter(item, "item")
            binding.textCustomer.text = item!!.regionName
            binding.textProgressSize.text = item.downloadInfo
            binding.reDownload.visibility = View.INVISIBLE
//            val `$this$bind_u24lambda_u2d2_u24lambda_u2d1` = ContextCompat.getDrawable(
//                context, R.drawable.ic_home_work
//            )
//            if (`$this$bind_u24lambda_u2d2_u24lambda_u2d1` != null) {
//                val `$this$bind_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0` =
//                    DrawableCompat.wrap(`$this$bind_u24lambda_u2d2_u24lambda_u2d1`)
//                if (Build.VERSION.SDK_INT >= 23) {
//                    `$this$bind_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0`.setTint(
//                        context.getColor(
//                            R.color.gray_100
//                        )
//                    )
//                } else {
//                    `$this$bind_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0`.setTint(-3355444)
//                }
//                `$this$bind_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0`.setTintMode(PorterDuff.Mode.SRC_IN)
//                binding.icon.setImageDrawable(
//                    `$this$bind_u24lambda_u2d2_u24lambda_u2d1_u24lambda_u2d0`
//                )
//            }

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
            if (item.isSelected) {
                processDownload(binding, item, viewModel, lifecycleOwner, context)
            }
//            binding.download.setOnClickListener(object : View.OnClickListener(item) {
//                /* synthetic */ var `f$1`: Region? = null
//                override fun onClick(view: View) {
//                    `m641bind$lambda3`(this@ListItemViewHolder, `f$1`, view)
//                }
//
//                init {
//                    `f$1` = r2
//                }
//            })
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
//            viewModel2.`apply2$app_debug`(
//                DownloadOperations.Builder(
//                    context2!!, item
//                ).build()
//            )
//            val tagDownload = Intrinsics.stringPlus(item!!.regionSID, "_download")
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
//
//            /* access modifiers changed from: private */ /* JADX WARNING: Code restructure failed: missing block: B:5:0x006a, code lost:
//            r4 = r13.get(kotlin.jvm.internal.Intrinsics.stringPlus(r1, "_progress_percent"));
//         */
//            /* renamed from: processDownload$lambda-5  reason: not valid java name */ /* Code decompiled incorrectly, please refer to instructions dump. */
//            fun `m643processDownload$lambda5`(
//                r19: p000co.p001id.cpn.bdmgafi.databinding.ItemDownloadCustomerBinding?,
//                r20: String?,
//                r21: MainViewModel?,
//                r22: p000co.p001id.cpn.entity.Region?,
//                r23: List<*>?
//            ) {
//                /*
//                r0 = r19
//                r1 = r20
//                java.lang.String r2 = "$binding"
//                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r2)
//                java.lang.String r2 = "$tagDownload"
//                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r2)
//                java.lang.String r2 = "$viewModel"
//                r9 = r21
//                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r2)
//                java.lang.String r2 = "$item"
//                r10 = r22
//                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r2)
//                java.lang.String r2 = java.lang.String.valueOf(r23)
//                java.lang.String r3 = "[]"
//                boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2, (java.lang.Object) r3)
//                if (r2 != 0) goto L_0x01c5
//                com.airbnb.lottie.LottieAnimationView r2 = r0.progressBarInit
//                r3 = 0
//                r2.setVisibility(r3)
//                android.widget.ImageView r2 = r0.download
//                r4 = 4
//                r2.setVisibility(r4)
//                r2 = r23
//                java.lang.Object r3 = r2.get(r3)
//                r11 = r3
//                androidx.work.WorkInfo r11 = (androidx.work.WorkInfo) r11
//                androidx.work.Data r3 = r11.getProgress()
//                java.lang.String r4 = "info.progress"
//                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
//                r12 = r3
//                com.google.gson.JsonParser r3 = new com.google.gson.JsonParser
//                r3.<init>()
//                co.id.cpn.entity.util.Utils r4 = p000co.p001id.cpn.entity.util.Utils.INSTANCE
//                java.lang.String r4 = r4.serializeToJson(r12)
//                com.google.gson.JsonElement r3 = r3.parse((java.lang.String) r4)
//                com.google.gson.JsonObject r3 = r3.getAsJsonObject()
//                java.lang.String r4 = "mValues"
//                com.google.gson.JsonElement r3 = r3.get(r4)
//                com.google.gson.JsonObject r3 = r3.getAsJsonObject()
//                r13 = r3
//                r3 = 0
//                if (r13 != 0) goto L_0x006a
//            L_0x0068:
//                r4 = r3
//                goto L_0x007f
//            L_0x006a:
//                java.lang.String r4 = "_progress_percent"
//                java.lang.String r4 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r4)
//                com.google.gson.JsonElement r4 = r13.get(r4)
//                if (r4 != 0) goto L_0x0077
//                goto L_0x0068
//            L_0x0077:
//                int r4 = r4.getAsInt()
//                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
//            L_0x007f:
//                r14 = r4
//                if (r13 != 0) goto L_0x0084
//            L_0x0082:
//                r4 = r3
//                goto L_0x0099
//            L_0x0084:
//                java.lang.String r4 = "_progress_max"
//                java.lang.String r4 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r4)
//                com.google.gson.JsonElement r4 = r13.get(r4)
//                if (r4 != 0) goto L_0x0091
//                goto L_0x0082
//            L_0x0091:
//                int r4 = r4.getAsInt()
//                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
//            L_0x0099:
//                r15 = r4
//                if (r13 != 0) goto L_0x009d
//            L_0x009c:
//                goto L_0x00b2
//            L_0x009d:
//                java.lang.String r4 = "_progress"
//                java.lang.String r4 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r4)
//                com.google.gson.JsonElement r4 = r13.get(r4)
//                if (r4 != 0) goto L_0x00aa
//                goto L_0x009c
//            L_0x00aa:
//                int r3 = r4.getAsInt()
//                java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
//            L_0x00b2:
//                r8 = r3
//                androidx.work.WorkInfo$State r3 = r11.getState()
//                int[] r4 = p000co.p001id.cpn.bdmgafi.p003ui.downloader.DownloadCustomerAdapter.ListItemViewHolder.WhenMappings.$EnumSwitchMapping$0
//                int r3 = r3.ordinal()
//                r3 = r4[r3]
//                switch(r3) {
//                    case 1: goto L_0x01b2;
//                    case 2: goto L_0x019f;
//                    case 3: goto L_0x011d;
//                    case 4: goto L_0x00f7;
//                    case 5: goto L_0x00c5;
//                    default: goto L_0x00c2;
//                }
//            L_0x00c2:
//                r0 = r8
//                goto L_0x01c7
//            L_0x00c5:
//                androidx.work.Data r3 = r11.getOutputData()
//                java.lang.String r4 = "info.outputData"
//                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
//                r7 = r3
//                java.lang.String r3 = ""
//                java.lang.String r3 = kotlin.jvm.internal.Intrinsics.stringPlus(r3, r7)
//                java.lang.String r4 = "WORK_STATUS"
//                android.util.Log.w(r4, r3)
//                java.lang.String r4 = r22.getRegionSID()
//                r16 = 0
//                r17 = 0
//                java.lang.String r5 = "FAILED"
//                java.lang.String r6 = "Failed, please try again"
//                r3 = r21
//                r18 = r7
//                r7 = r16
//                r0 = r8
//                r8 = r17
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x00f7:
//                r0 = r8
//                java.lang.String r4 = r22.getRegionSID()
//                r7 = 100
//                r8 = 100
//                java.lang.String r5 = "DOWNLOADING"
//                java.lang.String r6 = "Completing..."
//                r3 = r21
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                java.lang.String r4 = r22.getRegionSID()
//                r7 = 0
//                r8 = 0
//                java.lang.String r5 = "DOWNLOADED"
//                java.lang.String r6 = "downloaded"
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x011d:
//                r0 = r8
//                if (r0 == 0) goto L_0x0169
//                java.lang.String r3 = java.lang.String.valueOf(r0)
//                int r3 = java.lang.Integer.parseInt(r3)
//                if (r3 <= 0) goto L_0x0169
//                java.lang.String r4 = r22.getRegionSID()
//                java.lang.StringBuilder r3 = new java.lang.StringBuilder
//                r3.<init>()
//                r3.append(r0)
//                r5 = 47
//                r3.append(r5)
//                r3.append(r15)
//                java.lang.String r5 = "KB • "
//                r3.append(r5)
//                r3.append(r14)
//                r5 = 37
//                r3.append(r5)
//                java.lang.String r6 = r3.toString()
//                java.lang.String r3 = java.lang.String.valueOf(r15)
//                int r7 = java.lang.Integer.parseInt(r3)
//                java.lang.String r3 = java.lang.String.valueOf(r0)
//                int r8 = java.lang.Integer.parseInt(r3)
//                java.lang.String r5 = "DOWNLOADING"
//                r3 = r21
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x0169:
//                if (r0 == 0) goto L_0x018c
//                java.lang.String r3 = java.lang.String.valueOf(r0)
//                int r3 = java.lang.Integer.parseInt(r3)
//                r4 = 100
//                if (r3 != r4) goto L_0x018c
//                java.lang.String r4 = r22.getRegionSID()
//                r7 = 100
//                r8 = 100
//                java.lang.String r5 = "DOWNLOADING"
//                java.lang.String r6 = "Completing..."
//                r3 = r21
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x018c:
//                java.lang.String r4 = r22.getRegionSID()
//                r7 = 0
//                r8 = 0
//                java.lang.String r5 = "WAITING"
//                java.lang.String r6 = "Initialing data..."
//                r3 = r21
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x019f:
//                r0 = r8
//                java.lang.String r4 = r22.getRegionSID()
//                r7 = 0
//                r8 = 0
//                java.lang.String r5 = "QUEUE"
//                java.lang.String r6 = "Waiting in Queue..."
//                r3 = r21
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x01b2:
//                r0 = r8
//                java.lang.String r4 = r22.getRegionSID()
//                r7 = 0
//                r8 = 0
//                java.lang.String r5 = "QUEUE"
//                java.lang.String r6 = "Waiting in Queue..."
//                r3 = r21
//                r3.updateDownloadStatus(r4, r5, r6, r7, r8)
//                goto L_0x01c7
//            L_0x01c5:
//                r2 = r23
//            L_0x01c7:
//                return
//            */
//                throw UnsupportedOperationException("Method not decompiled: p000co.p001id.cpn.bdmgafi.p003ui.downloader.DownloadCustomerAdapter.ListItemViewHolder.m643processDownload\$lambda5(co.id.cpn.bdmgafi.databinding.ItemDownloadCustomerBinding, java.lang.String, co.id.cpn.bdmgafi.ui.main.MainViewModel, co.id.cpn.entity.Region, java.util.List):void")
//            }
//
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