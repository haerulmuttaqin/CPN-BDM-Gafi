package co.id.cpn.bdmgafi.ui.customer

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.FragmentCustomerDetailBinding
import co.id.cpn.bdmgafi.databinding.FragmentCustomerVisitBinding
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.entity.CustomerItem
import com.downloader.utils.Utils
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomerVisitFragment : Fragment() {

    private var _binding: FragmentCustomerVisitBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: CustomerDetailFragmentArgs by navArgs()
    lateinit var customer: CustomerItem

    private val viewModel: CustomerViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerVisitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.itemSid
        Log.i("DetailFragment", "customer SID: $id")
        viewModel.customerBy(id).observe(viewLifecycleOwner, { seletedItem ->
            customer = seletedItem
            bind(customer)
        })
    }
    
    private fun bind(customer: CustomerItem) {
        binding.apply {
            binding.customerNameText.text = "Customer : ${customer.customerName}"
            binding.inspectionButton.setOnClickListener {
                findNavController().navigate(
                    CustomerVisitFragmentDirections.actionCustomerVisitFragmentToCustomerInspectionListFragment(customer.customerSID)
                )
            }
        }
    }

    private fun showCheckinDialog() {
        AppUtils.confirmDialog(
            requireContext(),
            "Please confirm",
            "Anda yakin akan berkunjung?",
            0,
            "Cancel",
            "OK",
            object : AppUtils.LeftRightConfirmListener {
                override fun onLeftClick() {}
                override fun onRightRight() {
                    
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}