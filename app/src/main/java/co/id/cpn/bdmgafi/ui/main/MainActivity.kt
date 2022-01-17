package co.id.cpn.bdmgafi.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.work.WorkInfo
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityMainBaseBinding
import co.id.cpn.bdmgafi.ui.adapter.ViewPagerAdapter
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.bdmgafi.ui.customer.CustomerActivity
import co.id.cpn.bdmgafi.ui.customer.DownloadCustomerDistribution
import co.id.cpn.bdmgafi.ui.dashboard.DashboardActivity
import co.id.cpn.bdmgafi.ui.login.LoginActivity
import co.id.cpn.bdmgafi.ui.worker.DownloadOperations
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.bdmgafi.util_view.DistributionDropdown
import co.id.cpn.bdmgafi.util_view.RegionDropdown
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Region
import co.id.cpn.entity.Resource
import co.id.cpn.entity.util.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
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
            finish()
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


    fun showDialogLoadCustomer() {
        val li = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = li.inflate(R.layout.dialog_load_cutomer, null)
        val dialog = BottomSheetDialog(this@MainActivity, R.style.AppBottomSheetDialogTheme)
        val closeButton = view.findViewById<ImageView>(R.id.close_button)
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        val processButton = view.findViewById<Button>(R.id.process_button)
        val distDropdown = view.findViewById<DistributionDropdown>(R.id.distribution_dropdown)
        val regionDropdown = view.findViewById<RegionDropdown>(R.id.region_dropdown)
        processButton.isEnabled = false
        viewModel.distributions.observe(this, { dist ->
            distDropdown.setupData(dist)
            distDropdown.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val distributionSID = dist[position].distributionSID
                    viewModel.regions(distributionSID).observe(this, { region ->
                        regionDropdown.setupData(region)
                        processButton.isEnabled = true
                        processButton.setOnClickListener {
                            val regionsSelected = arrayListOf<Region>()
                            region.forEach {
                                if (it.isSelected) {
                                    regionsSelected.add(it)
                                }
                            }
                            viewModel.deleteAllTransactionData()
                            showDialogLoadSQLite(distributionSID, regionsSelected)
                            dialog.dismiss()
                        }
                    })
                }
        })

        cancelButton.setOnClickListener { dialog.dismiss() }
        closeButton.setOnClickListener { dialog.dismiss() }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }
    

    fun showDialogLoadSQLite(distribution: String, regions: List<Region>) {
        val li = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = li.inflate(R.layout.dialog_sqlite_cutomer, null)

        val close = view.findViewById<ImageView>(R.id.close_button)
        val downloadContainer = view.findViewById<LinearLayout>(R.id.downloadContainer)
        val downloadList = arrayListOf<LinearLayout>()

        val work = DownloadOperations.Builder(
            applicationContext,
            regions,
            downloadContainer,
            downloadList,
            viewModel,
            this
        ).build()
        
        viewModel.apply(work)

        val dialog = BottomSheetDialog(this@MainActivity, R.style.AppBottomSheetDialogTheme)
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        close.setOnClickListener { 
            viewModel.cancel()
            dialog.dismiss()   
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

}