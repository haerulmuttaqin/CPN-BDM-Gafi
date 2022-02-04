package co.id.cpn.bdmgafi.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityMainBaseBinding
import co.id.cpn.bdmgafi.ui.adapter.ViewPagerAdapter
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.bdmgafi.ui.downloader.DownloadCustomerActivity
import co.id.cpn.bdmgafi.ui.login.LoginActivity
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.data.local.spref.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBaseBinding
    private val viewModel: MainViewModel by viewModel()

    override fun initViewBinding() {
        binding = ActivityMainBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {}

    /*viewModel.outputStatus("TAG_FINISH").observe(this, { listOfInfo ->
            if ("$listOfInfo" != "[]") {
                val info = listOfInfo[0]
                when (info.state) {
                    WorkInfo.State.ENQUEUED -> {


                    }
                    WorkInfo.State.SUCCEEDED -> {
                        startActivity(Intent(this@MainActivity, CustomerActivity::class.java))
                    }
                    WorkInfo.State.FAILED -> {
                        
                    }
                }
            }
        })*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBottomNavMenu()

//        binding.dashboardPerformButton.setOnClickListener {
//            startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
//        }
//
//        binding.bdmActivityButton.setOnClickListener {
//            startActivity(Intent(this@MainActivity, DownloadCustomerDistribution::class.java))
//        }

        binding.fab.setOnClickListener {

        }

        viewModel.getNews().observe(this, { news ->
            Log.w(TAG, "onCreate: $news")
            val headerAdapter = ViewPagerAdapter(news, this)
            binding.viewPagerHeader.adapter = headerAdapter
            binding.viewPagerHeader.setPadding(21, 0, 110, 0)
            headerAdapter.notifyDataSetChanged()
        })

        binding.workingArea.setOnClickListener {
            startActivity(Intent(this, DownloadCustomerActivity::class.java))
        }
    }

    private fun setupBottomNavMenu() {
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener)
    }

    private val navigationListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    AppUtils.confirmDialog(
                        this,
                        "Logout",
                        "Are you sure?",
                        R.drawable.ic_undraw_login_re_4vu2,
                        "Cancel",
                        "Yes, Logout",
                        object : AppUtils.LeftRightConfirmListener {
                            override fun onLeftClick() {
                                dialog?.dismiss()
                            }

                            override fun onRightRight() {
                                SharedPref(this@MainActivity).loggedIn = false
                                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                            }
                        }
                    )
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

}