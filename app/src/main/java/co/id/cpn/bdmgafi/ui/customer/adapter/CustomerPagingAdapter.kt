package co.id.cpn.bdmgafi.ui.customer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.id.cpn.bdmgafi.databinding.ItemCustomerBinding
import co.id.cpn.entity.CustomerItem
import java.util.*

class CustomerPagingAdapter (private val onItemClicked: (CustomerItem) -> Unit):
    PagingDataAdapter<CustomerItem, CustomerPagingAdapter.ListItemViewHolder>(diffCallback) {
    private val list: ArrayList<CustomerItem> = arrayListOf()

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CustomerItem>() {
            override fun areItemsTheSame(
                oldItem: CustomerItem,
                newItem: CustomerItem
            ): Boolean {
                return oldItem.customerSID == newItem.customerSID
            }

            override fun areContentsTheSame(
                oldItem: CustomerItem,
                newItem: CustomerItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class ListItemViewHolder(val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customerItem: CustomerItem?) {
            binding.customerNameText.text = customerItem?.customerName
            binding.customerAddressText.text = customerItem?.customerAddress
            binding.customerAssetText.text = customerItem?.customerAssetID
            if (customerItem?.customerInspection != "") {
                binding.lastInspectionText.text = "Last Inspection: \n${customerItem?.customerInspection}"
            } else {
                binding.lastInspectionText.visibility = View.GONE
            }
            if (customerItem?.customerOrder != "") {
                binding.lastOrderText.text = "Last Order: \n${customerItem?.customerOrder}"
            } else {
                binding.lastOrderText.visibility = View.GONE
            }
            if (customerItem?.productOrder != "") {
                binding.productOrderText.text = "Product Order: \n${customerItem?.productOrder}"
            } else {
                binding.productOrderText.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val currentPosition = getItem(position)
        holder.bind(currentPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context))
        return ListItemViewHolder(binding)
    }
}