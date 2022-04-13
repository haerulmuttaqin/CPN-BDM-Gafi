package co.id.cpn.bdmgafi.ui.customer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.id.cpn.bdmgafi.databinding.FragmentCustomerListBinding
import co.id.cpn.bdmgafi.ui.customer.adapter.CustomerAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomerListFragment : Fragment() {
    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerViewModel by sharedViewModel()
    
    private val adapter: CustomerAdapter by lazy {
        CustomerAdapter(
            onItemClicked = {
                Log.i("ListFragment", " ${it.customerName}")
                findNavController().navigate(
                    CustomerListFragmentDirections.actionListFragmentToDetailFragment(it.customerSID)
                )
            }
        )
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.customers.observe(viewLifecycleOwner, { items ->
            items.let {
                adapter.submitList(it)
            }
            binding.progress.visibility = View.GONE
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