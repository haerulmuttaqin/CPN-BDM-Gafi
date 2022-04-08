package co.id.cpn.bdmgafi.ui.distribution

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import co.id.cpn.bdmgafi.databinding.ActivityCustDistBinding
import co.id.cpn.bdmgafi.ui.adapter.ViewPagerCustAdapter
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.bdmgafi.ui.distribution.fragment.CustomerListFragment
import co.id.cpn.bdmgafi.ui.distribution.fragment.CustomerMapFragment
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener

class DistributionActivity : BaseActivity() {
    private lateinit var binding: ActivityCustDistBinding
    private val viewModel: DistributionViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val adapter: ViewPagerCustAdapter? = null
    private var dialogEmpty = false
    private var inspectionCriteriaFilter = ""
    private var inspectionCriteriaFilterName = ""
    private val tabPos = 0
    private var totalPage = 2
    private var visitCriteriaFilter = ""
    private var visitCriteriaFilterName = ""

    override fun observeViewModel() {}
    override fun initViewBinding() {
        binding = ActivityCustDistBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.getStringExtra("dist_type") != null) {
            val distType = intent.getStringExtra("dist_type")
            when(distType) {
                "cust" -> {
                    title = "Customer Distribution"
                }
                "asset" -> {
                    title = "Asset Distribution"
                }
            }
            mainViewModel.distributions.observe(this, { dist ->
                binding.distributionDropdown.setupData(dist)
                binding.distributionDropdown.setOnItemClickListener { _, _, position, _ ->
                    mainViewModel.submitSelectedDistribution(dist[position].distributionSID)
                    setupViewPager(binding.viewPager, distType!!)
                }
            })
        }
    }


    fun setupViewPager(viewPager: ViewPager, type: String) {
        Handler().postDelayed({
            val viewPagerAdapter = ViewPagerCustAdapter(supportFragmentManager)
            if (type == "cust") {
                viewPagerAdapter.addFragment(
                    CustomerMapFragment.newInstance(
                        mainViewModel.distributionSelected.value!!,
                        ""
                    ), "CUSTOMER"
                )
                viewPagerAdapter.addFragment(
                    CustomerListFragment.newInstance(
                        mainViewModel.distributionSelected.value!!,
                        ""
                    ), "CUSTOMER"
                )
            } else {
                binding.searchEdittext.hint = "Search Asset"
//                val activityCustDistBinding4: ActivityCustDistBinding = binding
//                if (activityCustDistBinding4 != null) {
//                    activityCustDistBinding4.searchEdittext.setHint("Search Asset")
//                    val viewPagerCustAdapter3: ViewPagerCustAdapter = adapter
//                    if (viewPagerCustAdapter3 != null) {
//                        val companion3: AssetDistMapFragment.Companion =
//                            AssetDistMapFragment.Companion
//                        val activityCustDistBinding5: ActivityCustDistBinding = binding
//                        if (activityCustDistBinding5 != null) {
//                            viewPagerCustAdapter3.addFragment(
//                                companion3.newInstance(
//                                    activityCustDistBinding5.distributionDropdown.getSelectedID(),
//                                    ""
//                                ), "MAP"
//                            )
//                            val viewPagerCustAdapter4: ViewPagerCustAdapter = adapter
//                            if (viewPagerCustAdapter4 != null) {
//                                val companion4: AssetDistListFragment.Companion =
//                                    AssetDistListFragment.Companion
//                                val activityCustDistBinding6: ActivityCustDistBinding =
//                                    binding
//                                if (activityCustDistBinding6 != null) {
//                                    viewPagerCustAdapter4.addFragment(
//                                        companion4.newInstance(
//                                            activityCustDistBinding6.distributionDropdown.getSelectedID(),
//                                            ""
//                                        ), "ASSET"
//                                    )
//                                } else {
//                                    Intrinsics.throwUninitializedPropertyAccessException("binding")
//                                    throw null
//                                }
//                            } else {
//                                Intrinsics.throwUninitializedPropertyAccessException("adapter")
//                                throw null
//                            }
//                        } else {
//                            Intrinsics.throwUninitializedPropertyAccessException("binding")
//                            throw null
//                        }
//                    } else {
//                        Intrinsics.throwUninitializedPropertyAccessException("adapter")
//                        throw null
//                    }
//                } else {
//                    Intrinsics.throwUninitializedPropertyAccessException("binding")
//                    throw null
//                }
            }
            totalPage = 2
            val viewPagerCustAdapter5: ViewPagerCustAdapter = viewPagerAdapter
            if (viewPagerCustAdapter5 != null) {
                binding.viewPager.adapter = viewPagerCustAdapter5
                val activityCustDistBinding7: ActivityCustDistBinding = binding
                if (activityCustDistBinding7 != null) {
                    val customViewPager = activityCustDistBinding7.viewPager
                    val activityCustDistBinding8: ActivityCustDistBinding = binding
                    if (activityCustDistBinding8 != null) {
                        customViewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(activityCustDistBinding8.tabLayout))
                        val activityCustDistBinding9: ActivityCustDistBinding = binding
                        if (activityCustDistBinding9 != null) {
                            activityCustDistBinding9.viewPager.currentItem = 0
                            val activityCustDistBinding10: ActivityCustDistBinding =
                                binding
                            if (activityCustDistBinding10 != null) {
                                activityCustDistBinding10.viewPager.pageMargin = 20
                                val activityCustDistBinding11: ActivityCustDistBinding =
                                    binding
                                if (activityCustDistBinding11 != null) {
                                    activityCustDistBinding11.viewPager.offscreenPageLimit =
                                        totalPage
                                    val activityCustDistBinding12: ActivityCustDistBinding =
                                        binding
                                    if (activityCustDistBinding12 != null) {
                                        val tabLayout = activityCustDistBinding12.tabLayout
                                        val activityCustDistBinding13: ActivityCustDistBinding =
                                            binding
                                        if (activityCustDistBinding13 != null) {
                                            tabLayout.setupWithViewPager(activityCustDistBinding13.viewPager)
                                            val activityCustDistBinding14: ActivityCustDistBinding =
                                                binding
                                            if (activityCustDistBinding14 != null) {
                                                activityCustDistBinding14.tabLayout.setTabTextColors(
                                                    Color.parseColor("#0971ba"),
                                                    Color.parseColor("#ffffff")
                                                )
                                                val activityCustDistBinding15: ActivityCustDistBinding =
                                                    binding
                                                if (activityCustDistBinding15 != null) {
                                                    activityCustDistBinding15.tabLayout.setSelectedTabIndicatorHeight(
                                                        0
                                                    )
                                                    val activityCustDistBinding16: ActivityCustDistBinding =
                                                        binding
                                                    if (activityCustDistBinding16 != null) {
                                                        activityCustDistBinding16.tabLayout.tabRippleColor =
                                                            null as ColorStateList?
                                                        val activityCustDistBinding17: ActivityCustDistBinding =
                                                            binding
                                                        if (activityCustDistBinding17 != null) {
                                                            binding.viewPager.isPagingEnabled = false
                                                            val viewPagerCustAdapter6: ViewPagerCustAdapter = adapter!!
                                                            viewPagerCustAdapter6.notifyDataSetChanged()
                                                            if (viewPagerCustAdapter6 != null) {
//                                                                getDataList()
//                                                                getViewModel().getFilter()
//                                                                    .observe(`this$0`,
//                                                                        Observer<Any?> { obj ->
//                                                                            CustDistActivity.`m573setupViewPager$lambda19$lambda16`(
//                                                                                this@CustDistActivity,
//                                                                                obj as String
//                                                                            )
//                                                                        })
//                                                                getViewModel()
//                                                                    .getFilterCustomer()
//                                                                    .observe(`this$0`,
//                                                                        Observer<Any?> { obj ->
//                                                                            CustDistActivity.`m574setupViewPager$lambda19$lambda17`(
//                                                                                this@CustDistActivity,
//                                                                                obj as FilterCustomer
//                                                                            )
//                                                                        })
//                                                                getViewModel()
//                                                                    .getFilterAsset()
//                                                                    .observe(`this$0`,
//                                                                        Observer<Any?> { obj ->
//                                                                            CustDistActivity.`m575setupViewPager$lambda19$lambda18`(
//                                                                                this@CustDistActivity,
//                                                                                obj as FilterAsset
//                                                                            )
//                                                                        })
                                                                hideProgress()
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }, 500)

//        val activityCustDistBinding = binding
//        if (activityCustDistBinding != null) {
//            activityCustDistBinding.tabLayout.setOnTabSelectedListener()
//        } else {
//            Intrinsics.throwUninitializedPropertyAccessException("binding")
//            throw null
//        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}