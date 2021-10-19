package co.id.cpn.bdmgafi.ui.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.id.cpn.bdmgafi.databinding.FragmentCustomerInspectionListBinding
import co.id.cpn.bdmgafi.databinding.FragmentCustomerListBinding
import co.id.cpn.entity.CustomerItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomerListInspectionFragment : Fragment() {

    private var _binding: FragmentCustomerInspectionListBinding? = null
    private val binding get() = _binding!!
    
    private val safeArgs: CustomerDetailFragmentArgs by navArgs()
    lateinit var customer: CustomerItem
    
    private val viewModel: CustomerViewModel by sharedViewModel()
    
    private val adapter: CustomerInspectionAdapter by lazy {
        CustomerInspectionAdapter(
            onItemClicked = {
                Log.i("ListFragment", " $it")
//                findNavController().navigate(
//                    CustomerListFragmentDirections.actionListFragmentToDetailFragment(it.customerSID)
//                )
            }
        )
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerInspectionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = safeArgs.itemSid
        viewModel.customerAssetsBy(id).observe(viewLifecycleOwner, { items ->
            items.let {
                adapter.submitList(it)
            }
        })
        binding.apply { 
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}