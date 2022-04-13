package co.id.cpn.bdmgafi.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityMainBaseBinding
import co.id.cpn.bdmgafi.ui.adapter.ViewPagerAdapter
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.bdmgafi.ui.customer.CustomerActivity
import co.id.cpn.bdmgafi.ui.dashboard.DashboardActivity
import co.id.cpn.bdmgafi.ui.distribution.DistributionActivity
import co.id.cpn.bdmgafi.ui.downloader.DownloadCustomerActivity
import co.id.cpn.bdmgafi.ui.login.LoginActivity
import co.id.cpn.bdmgafi.ui.webview.WebViewActivity
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.bdmgafi.util.SpacesItemDecoration
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.jvm.internal.Intrinsics

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBaseBinding
    private val viewModel: MainViewModel by viewModel()

    override fun initViewBinding() {
        binding = ActivityMainBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun observeViewModel() {}

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

        binding.workingArea.setOnClickListener {
            startActivity(Intent(this, DownloadCustomerActivity::class.java))
        }

        setupMain()

        val userName: CharSequence = SharedPref(this).user.userName
        if (!(userName == null || userName.isEmpty())) {
            binding.name.text = "Welcome, $userName"
        } else {
            binding.name.visibility = View.GONE
        }

    }

    private fun setupMain() {
        viewModel.getUserModule(SharedPref(this).user.userRole)
        viewModel.modules(viewModel.getRolesData()).observe(this, { modules ->
            val adapter: MainModuleAdapter by lazy {
                MainModuleAdapter(
                    onItemClicked2 = { module ->
                        when (module?.moduleID) {
                            0 -> {
                                val intent = Intent(this, DistributionActivity::class.java)
                                intent.putExtra("dist_type", "cust")
                                this.startActivity(intent)
                            }
                            1 -> {
//                                val intent2 = Intent(this, CustDistActivity::class.java)
//                                intent2.putExtra("dist_type", Constants.ASSET)
//                                this.startActivity(intent2)
                            }
                            2 -> {
//                                this.showChooseScanDialog()
                            }
                            3 -> {
                                this.startActivity(
                                    Intent(
                                        this,
                                        DashboardActivity::class.java
                                    )
                                )
                            }
                            4 -> {
                                this.startActivity(
                                    Intent(
                                        this,
                                        CustomerActivity::class.java
                                    )
                                )
                            }
                        }
                    }
                )
            }
            binding.rvModule.addItemDecoration(SpacesItemDecoration(3, 40, false))
            binding.rvModule.hasFixedSize()
            binding.rvModule.adapter = adapter
            adapter.submitData(modules)
        })
        viewModel.getNews().observe(this, { news ->
            Log.w(TAG, "onCreate: $news")
            val headerAdapter = ViewPagerAdapter(news, this)
            binding.viewPagerHeader.adapter = headerAdapter
            binding.viewPagerHeader.setPadding(21, 0, 110, 0)
            headerAdapter.notifyDataSetChanged()
            binding.shimmer.stopShimmer()

            headerAdapter.setOnItemClickListener { _, position ->
                Log.w(TAG, "setupMain: $position")
                val newsItem = news[position]
                val title: String = newsItem.title
                val url = Intrinsics.stringPlus("https://www.joyday.com/", newsItem.link)
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra(Constants.TITLE, title)
                intent.putExtra(Constants.URL, url)
                startActivity(intent)
            }
        })

        binding.seeAll.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(Constants.TITLE, "Information")
            intent.putExtra(Constants.URL, "https://www.joyday.com/id/blog")
            startActivity(intent)
        }

        Handler().postDelayed({
            binding.shimmer.visibility = View.GONE
            binding.content.visibility = View.VISIBLE
        }, 1000)
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