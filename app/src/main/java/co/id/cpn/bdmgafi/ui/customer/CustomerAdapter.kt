package co.id.cpn.bdmgafi.ui.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.databinding.ItemCustomerBinding
import co.id.cpn.entity.Customer
import co.id.cpn.entity.CustomerItem

class CustomerAdapter(private val onItemClicked: (CustomerItem) -> Unit) :
    ListAdapter<CustomerItem, CustomerAdapter.ListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(ItemCustomerBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.itemView.setOnClickListener { onItemClicked(currentPosition) }
        holder.bind(currentPosition)
    }

    class ListItemViewHolder(private var binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cust: CustomerItem) {
            binding.apply {
                customerNameText.text = cust.customerName
                customerAddressText.text = cust.customerAddress
                customerAssetText.text = cust.customerAssetID
                lastInspectionText.text = "Last Inspection: ${cust.customerInspection}"
                lastOrderText.text = "Last Order: ${cust.customerOrder}"
                productOrderText.text = "Product Order:"
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CustomerItem>() {
            override fun areItemsTheSame(
                oldItem: CustomerItem,
                newItem: CustomerItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CustomerItem,
                newItem: CustomerItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}