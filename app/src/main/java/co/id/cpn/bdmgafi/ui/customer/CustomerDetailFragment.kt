package co.id.cpn.bdmgafi.ui.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.FragmentCustomerDetailBinding
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.entity.CustomerItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CustomerDetailFragment : Fragment() {

    private var _binding: FragmentCustomerDetailBinding? = null
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
        _binding = FragmentCustomerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.itemSid
        Log.i("DetailFragment", "customer SID: $id")
        viewModel.customerBy(id).observe(viewLifecycleOwner, { seletedItem ->
            if (seletedItem != null) {
                customer = seletedItem
                bind(customer)
            }
        })
    }

    private fun bind(customer: CustomerItem) {
        binding.apply {
            binding.customerNameText.text = "Customer : ${customer.customerName}"
            binding.checkInButton.setOnClickListener {
                showCheckinDialog()
            }
        }
    }

    private fun showCheckinDialog() {
        AppUtils.confirmDialog(
            requireContext(),
            "Please confirm",
            "Anda yakin akan berkunjung?",
            R.drawable.ic_undraw_confirmation,
            "Cancel",
            "OK",
            object : AppUtils.LeftRightConfirmListener {
                override fun onLeftClick() {}
                override fun onRightRight() {
                    findNavController().navigate(
                        CustomerDetailFragmentDirections.actionCustomerDetailFragmentToCustomerVisitFragment(
                            customer.customerSID
                        )
                    )
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}