package co.id.cpn.bdmgafi.ui.customer

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import co.id.cpn.bdmgafi.databinding.ActivityDownloadCustomerBinding
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadCustomerDistribution : BaseActivity() {

    private lateinit var binding: ActivityDownloadCustomerBinding
    private val viewModel: MainViewModel by viewModel()

    override fun initViewBinding() {
        binding = ActivityDownloadCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun observeViewModel() {
        viewModel.distributions.observe(this, { dist ->
            binding.distributionDropdown.setupData(dist)
            binding.distributionDropdown.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val distributionSID = dist[position].distributionSID
                    viewModel.submitSelectedDistribution(distributionSID)
                }
        })
        viewModel.distributionSelected.observe(this, { dist ->
            getRegions(dist)
            binding.swipe.setOnRefreshListener {
                getRegions(dist)
            }
        })
    }

    private fun getRegions(distributionSID: String) {
        viewModel.regions(distributionSID).observe(this, { items ->
            items.let {
                adapter.submitList(it)
            }
        })
        binding.apply {
            recyclerView.adapter = adapter
            swipe.isRefreshing = false
        }
    }

    private val adapter: DownloadCustomerAdapter by lazy {
        DownloadCustomerAdapter(
            context = this,
            viewModel = viewModel, 
            onItemClicked = {
                Log.i("ListFragment", " $it")
            }
        )
    }

}