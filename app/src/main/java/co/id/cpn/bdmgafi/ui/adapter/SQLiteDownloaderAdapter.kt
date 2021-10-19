package co.id.cpn.bdmgafi.ui.adapter

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.bdmgafi.util.DownloadDataSqlite
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.Region
import co.id.cpn.entity.Resource
import co.id.cpn.entity.util.Constants
import kotlinx.coroutines.runBlocking

internal class SQLiteDownloaderAdapter(
    private var dataList: List<Region>,
    private val viewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<SQLiteDownloaderAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var regionName: TextView = view.findViewById(R.id.textCustomer)
        var progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        var textProgress: TextView = view.findViewById(R.id.textProgressSize)
        var textProgressPercent: TextView = view.findViewById(R.id.textProgressPercent)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_download_sqlite, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.regionName.text = data.regionName

        viewModel.getCustomerSQLite(
            data.distributionSid,
            data.regionSID,
            SharedPref(holder.regionName.context).token
        )
        runBlocking {
            viewModel.sqLite.observe(lifecycleOwner, { response ->
                when (response) {
                    is Resource.Loading -> {
                        holder.progressBar.isIndeterminate = true
                    }
                    is Resource.Success -> response.data.let {
                        holder.progressBar.isIndeterminate = false
                        /*downloadDbMaster(
//                            response.data.data.get("dataZip").asString,
                            "http://103.100.27.46/sqlite.zip",
                            holder.progressBar,
                            holder.textProgress,
                            holder.textProgressPercent,
                            holder.progressBar.context,
                            viewModel
                        )*/
                    }
                    is Resource.Failed -> {
                        holder.progressBar.isIndeterminate = false
                        if (response.code == Constants.CODE_UNAUTHORIZED) {
                            AppUtils.errorDialog(
                                holder.progressBar.context,
                                response.message
                            )
                        } else {
                            AppUtils.errorDialog(
                                holder.progressBar.context,
                                response.message,
                            )
                        }
                    }
                    is Resource.Error -> {
                        holder.progressBar.isIndeterminate = false
                        AppUtils.errorDialog(
                            holder.progressBar.context,
                            response.throwable.localizedMessage
                        )
                    }
                    else -> {
                        holder.progressBar.isIndeterminate = false
                        AppUtils.errorDialog(
                            holder.progressBar.context,
                            "Empty response from server!",
                        )
                    }
                }
            })
        }
    }

    open fun downloadDbMaster(
        dbUrl: String,
        progressHorizontal: ProgressBar,
        textProgress: TextView,
        textProgressPercent: TextView,
        context: Context,
        viewModel: MainViewModel
    ) {
        object : DownloadDataSqlite(
            context,
            /*progressHorizontal,
            textProgress,
            textProgressPercent,*/
        ) {
            override fun finishDownload(isSuccess: Boolean, message: String?) {
                if (!isSuccess) {
                    textProgressPercent.text = "Failed"
                    progressHorizontal.progress = 0
                    textProgress.text = "0/0"
                } else {
                    textProgressPercent.text = "Success"
                    viewModel.assetsTemp.observe(lifecycleOwner, { Log.w("TAG", "--> " + it) /*viewModel.insertAsset(it)*/ })
                    viewModel.customersTemp.observe(lifecycleOwner, { Log.w("TAG", "--> " + it) /*viewModel.insertCustomer(it)*/ })
                    viewModel.customerTypesTemp.observe(lifecycleOwner, { Log.w("TAG", "--> " + it) /*viewModel.insertCustomerType(it)*/ })
                    viewModel.productsTemp.observe(lifecycleOwner, { Log.w("TAG", "--> " + it) /*viewModel.insertProduct(it)*/ })
                }
            }
        }.execute(dbUrl, Constants.MASTER_DB)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}