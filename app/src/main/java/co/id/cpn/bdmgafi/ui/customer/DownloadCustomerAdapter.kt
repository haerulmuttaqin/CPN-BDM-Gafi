package co.id.cpn.bdmgafi.ui.customer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ItemDownloadCustomerBinding
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.bdmgafi.ui.worker.DownloadOperations
import co.id.cpn.bdmgafi.ui.worker.DownloadOperations2
import co.id.cpn.entity.Region

class DownloadCustomerAdapter(private val context: Context, private val viewModel: MainViewModel, private val onItemClicked: (Region) -> Unit) :
    ListAdapter<Region, DownloadCustomerAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(context, viewModel, ItemDownloadCustomerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.itemView.setOnClickListener { onItemClicked(currentPosition) }
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var context: Context, private var viewModel: MainViewModel, private var binding: ItemDownloadCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Region) {
            binding.apply {
                textCustomer.text = item.regionName
//                customerAssetTypeText.text = item.assetType
            }
            binding.download.setOnClickListener {
                val animZoomOut = AnimationUtils.loadAnimation(binding.icon.context, R.anim.zoom_out)
                binding.icon.startAnimation(animZoomOut)
                binding.progress.visibility = View.VISIBLE
                binding.progressBarInit.visibility = View.VISIBLE
                binding.download.visibility = View.INVISIBLE
                val work = DownloadOperations2.Builder(context).build()
                viewModel.apply2(work)
            }
        }
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