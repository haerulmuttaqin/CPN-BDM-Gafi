package co.id.cpn.bdmgafi.ui.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.databinding.ItemCustomerBinding
import co.id.cpn.bdmgafi.databinding.ItemCustomerInspectionBinding
import co.id.cpn.entity.Asset
import co.id.cpn.entity.Customer
import co.id.cpn.entity.CustomerItem

class CustomerInspectionAdapter(private val onItemClicked: (Asset) -> Unit) :
    ListAdapter<Asset, CustomerInspectionAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemCustomerInspectionBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.itemView.setOnClickListener { onItemClicked(currentPosition) }
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemCustomerInspectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asset) {
            binding.apply {
                customerAssetIdText.text = item.assetID
                customerAssetTypeText.text = item.assetType
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Asset>() {
            override fun areItemsTheSame(
                oldItem: Asset,
                newItem: Asset
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Asset,
                newItem: Asset
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}