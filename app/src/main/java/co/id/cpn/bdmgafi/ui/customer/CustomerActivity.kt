package co.id.cpn.bdmgafi.ui.customer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityCustomerBinding
import co.id.cpn.bdmgafi.ui.base.BaseActivity


class CustomerActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCustomerBinding

    override fun initViewBinding() {
        binding = ActivityCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment_customer)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private val adapter: CustomerAdapter by lazy {
        CustomerAdapter(
            onItemClicked = { item ->
                Log.i("Customer", " $item")
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}