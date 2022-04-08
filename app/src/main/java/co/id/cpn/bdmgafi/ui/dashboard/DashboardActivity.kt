package co.id.cpn.bdmgafi.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityDashboardBinding
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.entity.Distribution
import co.id.cpn.entity.Resource
import co.id.cpn.entity.util.Utils
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity: BaseActivity() {
    
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()
    
    override fun observeViewModel() {}

    override fun initViewBinding() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.submitSelectedDistribution(viewModel.distributionSelected.value.toString())
            binding.distributionDropdown.setText("All Distributor")
            initProductivity()
            initSoVsDoChart()
            initReasonOrderLost()
            initTagging()
            initTotalFreezer()
            initRegisterOutlet()
            initVisitPerformance()
            Handler().postDelayed({
                binding.swipeRefresh.isRefreshing = false
            }, 3000)
        }

        viewModel.distributions.observe(this, { dist ->
            val allDistribution = StringBuilder()
            dist.forEach { distSid ->
                allDistribution.append(distSid.distributionSID)
                allDistribution.append(",")
            }
            val distData = arrayListOf<Distribution>()
            distData.add(Distribution(distributionSID = allDistribution.toString().dropLast(1), distributionName = "All Distributor"))
            distData.addAll(dist)

            // run first
            viewModel.submitSelectedDistribution(allDistribution.toString().dropLast(1))
            binding.distributionDropdown.setText("All Distributor")
            initProductivity()
            initSoVsDoChart()
            initReasonOrderLost()
            initTagging()
            initTotalFreezer()
            initRegisterOutlet()
            initVisitPerformance()

            // dropdown select
            binding.distributionDropdown.setupData(distData)
            binding.distributionDropdown.setOnItemClickListener { _, _, position, _ ->
                viewModel.submitSelectedDistribution(distData[position].distributionSID)
                initProductivity()
                initSoVsDoChart()
                initReasonOrderLost()
                initTagging()
                initTotalFreezer()
                initRegisterOutlet()
                initVisitPerformance()
            }
        })
    }

    private fun initProductivity() {
        viewModel.productivity()
        viewModel.productivity.observe(this, { data ->
            when(data) {
                is Resource.Loading -> {
                    binding.productivity.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> data.let {
                    binding.productivity.progress.visibility = View.GONE
                    val caption = data.data.data[0].asJsonObject.get("caption").asString
                    val value = data.data.data[0].asJsonObject.get("value").asString
                    val label = caption.split("|")[0]
                    val desc = caption.split("|")[1]
                    binding.productivity.productivityLabel.text = label
                    binding.productivity.productivityInfo.text = desc
                    binding.productivity.productivityValue.text = value
                }
                else -> {
                    binding.productivity.progress.visibility = View.GONE
                    binding.productivity.productivityValue.text = "0"
                    Toast.makeText(this, "Productivity - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initSoVsDoChart() {
        viewModel.soVsDo()
        viewModel.soVsDo.observe(this, { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.soVsDo.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> response.let {
                    binding.soVsDo.progress.visibility = View.GONE

                    val data = response.data.data
                    val summary = data[0]
                    val soValue = summary.asJsonObject.get("SO").asInt
                    val doValue = summary.asJsonObject.get("DO").asInt

                    val pieEntries = arrayListOf<PieEntry>()
                    val label = "type"

                    //initializing data
                    val typeAmountMap: MutableMap<String, Int> = HashMap()
                    typeAmountMap["SO"] = soValue
                    typeAmountMap["DO"] = doValue


                    binding.soVsDo.soVsDoPercentText.text = (doValue * soValue / 100).toString() + "%"
                    binding.soVsDo.soVsDoFromUntilText.text = "$soValue/$doValue"

                    //initializing colors for the entries
                    val colors = arrayListOf<Int>()
                    colors.add(Color.parseColor("#D50000"))
                    colors.add(Color.parseColor("#B1B1B1"))

                    //input data and fit data into pie chart entry
                    for (type in typeAmountMap.keys) {
                        pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
                    }

                    //collecting the entries with label name
                    val pieDataSet = PieDataSet(pieEntries, label)
                    pieDataSet.valueTextSize = 12f
                    pieDataSet.colors = colors

                    val pieData = PieData(pieDataSet)
                    pieData.setDrawValues(false)
                    binding.soVsDo.soVsDoChart.setDrawCenterText(false)
                    binding.soVsDo.soVsDoChart.setDrawEntryLabels(false)
                    binding.soVsDo.soVsDoChart.setDrawMarkers(false)
                    binding.soVsDo.soVsDoChart.holeRadius = 80f
                    binding.soVsDo.soVsDoChart.legend.isEnabled = false
                    binding.soVsDo.soVsDoChart.description.isEnabled = false
                    binding.soVsDo.soVsDoChart.data = pieData
                    binding.soVsDo.soVsDoChart.invalidate()

                    binding.soVsDo.childrenChart.removeAllViews()

                    data.forEach { child ->
                        if (child.asJsonObject.get("CAPTION").asString != "SUMMARY") {
                            val view: View = layoutInflater.inflate(R.layout.item_so_do_children, null)

                            val pieChartChild = view.findViewById<PieChart>(R.id.soVsDoChartChildren)
                            val percentTextChild = view.findViewById<TextView>(R.id.soVsDoPercentText)
                            val fromUntilPercentText = view.findViewById<TextView>(R.id.soVsDoFromUntilText)
                            val captionText = view.findViewById<TextView>(R.id.soVsDoBrand)

                            val soValueChild = child.asJsonObject.get("SO").asInt
                            val doValueChild = child.asJsonObject.get("DO").asInt
                            val captionChild = child.asJsonObject.get("CAPTION").asString

                            val pieEntriesChild = arrayListOf<PieEntry>()
                            val labelChild = "type"

                            //initializing data
                            val typeAmountMapChild: MutableMap<String, Int> = HashMap()
                            typeAmountMapChild["SO"] = soValueChild
                            typeAmountMapChild["DO"] = doValueChild

                            percentTextChild.text = (doValueChild * soValueChild / 100).toString() + "%"
                            fromUntilPercentText.text = "$soValueChild/$doValueChild"
                            captionText.text = captionChild.toString()

                            //initializing colors for the entries
                            val colorsChild = arrayListOf<Int>()
                            colorsChild.add(Color.parseColor("#fcba03"))
                            colorsChild.add(Color.parseColor("#B1B1B1"))

                            //input data and fit data into pie chart entry
                            for (type in typeAmountMapChild.keys) {
                                pieEntriesChild.add(PieEntry(typeAmountMapChild[type]!!.toFloat(), type))
                            }

                            //collecting the entries with label name
                            val pieDataSetChild = PieDataSet(pieEntriesChild, labelChild)
                            pieDataSetChild.colors = colorsChild

                            val pieDataChild = PieData(pieDataSetChild)
                            pieDataChild.setDrawValues(false)
                            pieChartChild.setDrawCenterText(false)
                            pieChartChild.setDrawEntryLabels(false)
                            pieChartChild.setDrawMarkers(false)
                            pieChartChild.holeRadius = 80f
                            pieChartChild.maxAngle = 180.0f
                            pieChartChild.rotationAngle = 180.0f
                            pieChartChild.legend.isEnabled = false
                            pieChartChild.description.isEnabled = false
                            pieChartChild.data = pieData
                            pieChartChild.invalidate()

                            binding.soVsDo.childrenChart.addView(view)
                        }
                    }
                }
                else -> {
                    binding.soVsDo.progress.visibility = View.GONE
                    Toast.makeText(this, "SO vs DO - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initReasonOrderLost() {

        viewModel.reasonOrderLost()
        viewModel.reasonOrderLost.observe(this, { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.reasonOrderlist.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> response.let {
                    binding.reasonOrderlist.progress.visibility = View.GONE

                    //initializing data
                    val typeAmountMap: MutableMap<String, Int> = HashMap()
                    response.data.data.forEach { data ->
                        typeAmountMap["${data.asJsonObject.get("caption").asString}"] = data.asJsonObject.get("value").asInt
                    }

                    val pieEntries = arrayListOf<PieEntry>()
                    for (type in typeAmountMap.keys) {
                        pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
                    }

                    val pieDataSet = PieDataSet(pieEntries, "")

                    val colors = arrayListOf<Int>()
                    colors.add(Color.parseColor("#4f00cf"))
                    colors.add(Color.parseColor("#006da3"))
                    colors.add(Color.parseColor("#05b583"))
                    colors.add(Color.parseColor("#80b505"))
                    colors.add(Color.parseColor("#b5a305"))
                    colors.add(Color.parseColor("#f07911"))
                    colors.add(Color.parseColor("#cf1060"))
                    colors.add(Color.parseColor("#a30101"))
                    colors.add(Color.parseColor("#05afbb"))
                    colors.add(Color.parseColor("#8805c5"))
                    colors.add(Color.parseColor("#1f7414"))
                    colors.add(Color.parseColor("#7eabff"))

                    pieDataSet.colors = colors
                    pieDataSet.valueTextSize = 12.0f
                    pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    val pieData = PieData(pieDataSet as IPieDataSet)
                    pieData.setDrawValues(true)
                    binding.reasonOrderlist.reasonChart.setDrawCenterText(true)
                    binding.reasonOrderlist.reasonChart.setDrawEntryLabels(false)
                    binding.reasonOrderlist.reasonChart.setEntryLabelColor(-16777216)
                    binding.reasonOrderlist.reasonChart.setDrawMarkers(true)
                    binding.reasonOrderlist.reasonChart.setDrawSlicesUnderHole(true)
                    binding.reasonOrderlist.reasonChart.extraTopOffset = 10.0f
                    binding.reasonOrderlist.reasonChart.extraBottomOffset = 20.0f
                    binding.reasonOrderlist.reasonChart.extraLeftOffset = 10.0f
                    binding.reasonOrderlist.reasonChart.extraRightOffset = 10.0f
                    binding.reasonOrderlist.reasonChart.holeRadius = 60.0f
                    val legend = binding.reasonOrderlist.reasonChart.legend
                    legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    legend.orientation = Legend.LegendOrientation.VERTICAL
                    legend.setDrawInside(false)
                    legend.xEntrySpace = 7.0f
                    legend.yEntrySpace = 0.0f
                    legend.yOffset = 40.0f
                    legend.isWordWrapEnabled = true
                    legend.setDrawInside(false)
                    legend.calculatedLineSizes
                    binding.reasonOrderlist.reasonChart.description.isEnabled = false
                    binding.reasonOrderlist.reasonChart.minAngleForSlices = 12.0f
                    binding.reasonOrderlist.reasonChart.data = pieData
                    binding.reasonOrderlist.reasonChart.invalidate()

                }
                else -> {
                    binding.reasonOrderlist.progress.visibility = View.GONE
                    Toast.makeText(this, "Reason of Order Lost - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun initTagging() {
        viewModel.tagging()
        viewModel.tagging.observe(this, { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.tagging.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> response.let {
                    binding.tagging.progress.visibility = View.GONE
                    val data = response.data.data

                    if (data.has("BLUE")) {
                        val value = data.asJsonObject.get("BLUE").asJsonArray[0].asJsonObject
                        binding.tagging.blue1.text = value.get("value").asString
                        binding.tagging.blue2.text = "[${value.get("comparation_percentage").asString}]%"
                        binding.tagging.blue3.text = value.get("total_percentage").asString
                        if (value.get("arrow").asString == "UP") {
                            binding.tagging.blue4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up))
                        } else {
                            binding.tagging.blue4.setImageDrawable(resources.getDrawable(R.drawable.arrow_down))
                        }
                        binding.tagging.blue4.setColorFilter(Color.parseColor(value.get("arrow_color").asString))
                    } else {
                        binding.tagging.blue1.text = "0"
                        binding.tagging.blue2.text = "[0]%"
                        binding.tagging.blue3.text = "0"
                        binding.tagging.blue4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up_down))
                        binding.tagging.blue4.setColorFilter(Color.parseColor("WHITE"))
                    }

                    if (data.has("YELLOW")) {
                        val value = data.asJsonObject.get("YELLOW").asJsonArray[0].asJsonObject
                        binding.tagging.yellow1.text = value.get("value").asString
                        binding.tagging.yellow2.text = "[${value.get("comparation_percentage").asString}]%"
                        binding.tagging.yellow3.text = value.get("total_percentage").asString
                        if (value.get("arrow").asString == "UP") {
                            binding.tagging.yellow4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up))
                        } else {
                            binding.tagging.yellow4.setImageDrawable(resources.getDrawable(R.drawable.arrow_down))
                        }
                        binding.tagging.yellow4.setColorFilter(Color.parseColor(value.get("arrow_color").asString))
                    } else {
                        binding.tagging.yellow1.text = "0"
                        binding.tagging.yellow2.text = "[0]%"
                        binding.tagging.yellow3.text = "0"
                        binding.tagging.yellow4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up_down))
                        binding.tagging.yellow4.setColorFilter(Color.parseColor("WHITE"))
                    }


                    if (data.has("RED")) {
                        val value = data.asJsonObject.get("RED").asJsonArray[0].asJsonObject
                        binding.tagging.red1.text = value.get("value").asString
                        binding.tagging.red2.text = "[${value.get("comparation_percentage").asString}]%"
                        binding.tagging.red3.text = value.get("total_percentage").asString
                        if (value.get("arrow").asString == "UP") {
                            binding.tagging.red4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up))
                        } else {
                            binding.tagging.red4.setImageDrawable(resources.getDrawable(R.drawable.arrow_down))
                        }
                        binding.tagging.red4.setColorFilter(Color.parseColor(value.get("arrow_color").asString))
                    } else {
                        binding.tagging.red1.text = "0"
                        binding.tagging.red2.text = "[0]%"
                        binding.tagging.red3.text = "0"
                        binding.tagging.red4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up_down))
                        binding.tagging.red4.setColorFilter(Color.parseColor("WHITE"))
                    }

                    if (data.has("GREY")) {
                        val value = data.asJsonObject.get("GREY").asJsonArray[0].asJsonObject
                        binding.tagging.gray1.text = value.get("value").asString
                        binding.tagging.gray2.text = "[${value.get("comparation_percentage").asString}]%"
                        binding.tagging.gray3.text = value.get("total_percentage").asString
                        if (value.get("arrow").asString == "UP") {
                            binding.tagging.gray4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up))
                        } else {
                            binding.tagging.gray4.setImageDrawable(resources.getDrawable(R.drawable.arrow_down))
                        }
                        binding.tagging.gray4.setColorFilter(Color.parseColor(value.get("arrow_color").asString))
                    } else {
                        binding.tagging.gray1.text = "0"
                        binding.tagging.gray2.text = "[0]%"
                        binding.tagging.gray3.text = "0"
                        binding.tagging.gray4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up_down))
                        binding.tagging.gray4.setColorFilter(Color.parseColor("WHITE"))
                    }

                    if (data.has("ORANGE")) {
                        val value = data.asJsonObject.get("ORANGE").asJsonArray[0].asJsonObject
                        binding.tagging.orange1.text = value.get("value").asString
                        binding.tagging.orange2.text = "[${value.get("comparation_percentage").asString}]%"
                        binding.tagging.orange3.text = value.get("total_percentage").asString
                        if (value.get("arrow").asString == "UP") {
                            binding.tagging.orange4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up))
                        } else {
                            binding.tagging.orange4.setImageDrawable(resources.getDrawable(R.drawable.arrow_down))
                        }
                        binding.tagging.orange4.setColorFilter(Color.parseColor(value.get("arrow_color").asString))
                    } else {
                        binding.tagging.orange1.text = "0"
                        binding.tagging.orange2.text = "[0]%"
                        binding.tagging.orange3.text = "0"
                        binding.tagging.orange4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up_down))
                        binding.tagging.orange4.setColorFilter(Color.parseColor("WHITE"))
                    }

                    if (data.has("BLACK")) {
                        val value = data.asJsonObject.get("BLACK").asJsonArray[0].asJsonObject
                        binding.tagging.black1.text = value.get("value").asString
                        binding.tagging.black2.text = "[${value.get("comparation_percentage").asString}]%"
                        binding.tagging.black3.text = value.get("total_percentage").asString
                        if (value.get("arrow").asString == "UP") {
                            binding.tagging.black4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up))
                        } else {
                            binding.tagging.black4.setImageDrawable(resources.getDrawable(R.drawable.arrow_down))
                        }
                        binding.tagging.black4.setColorFilter(Color.parseColor(value.get("arrow_color").asString))
                    } else {
                        binding.tagging.black1.text = "0"
                        binding.tagging.black2.text = "[0]%"
                        binding.tagging.black3.text = "0"
                        binding.tagging.black4.setImageDrawable(resources.getDrawable(R.drawable.arrow_up_down))
                        binding.tagging.black4.setColorFilter(Color.parseColor("WHITE"))
                    }

                }
                else -> {
                    binding.tagging.progress.visibility = View.GONE
                    Toast.makeText(this, "Tagging - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initTotalFreezer() {
        viewModel.totalFreezer()
        viewModel.totalFreezer.observe(this, { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.freezer.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> response.let {
                    binding.freezer.progress.visibility = View.GONE

                    val data = response.data.data[0].asJsonObject

                    binding.freezer.inUseText.text = data.get("in_use").asString
                    binding.freezer.inStockText.text = data.get("in_stock").asString
                    binding.freezer.stockGood.text = data.get("stock_good").asString
                    binding.freezer.stockBroken.text = data.get("stock_broken").asString
                    binding.freezer.distributionText.text = data.get("st_distribution").asString
                    binding.freezer.relocationText.text = data.get("relocation").asString
                    binding.freezer.bb.text = data.get("bb").asString
                    binding.freezer.fc.text = data.get("fc").asString
                    binding.freezer.gt.text = data.get("gt").asString
                    binding.freezer.zg.text = data.get("zg").asString
                }
                else -> {
                    binding.freezer.progress.visibility = View.GONE
                    Toast.makeText(this, "Total Freezer - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initRegisterOutlet() {

        viewModel.registerOutlet()
        viewModel.registerOutlet.observe(this, { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.registerOutlet.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> response.let {
                    binding.registerOutlet.progress.visibility = View.GONE

                    val caption = response.data.data.get("caption").asJsonArray
                    val value = response.data.data.get("value").asJsonArray

                    val typeAmountMap: MutableMap<String, Int> = HashMap()

                    value.forEachIndexed { index, data ->
                        typeAmountMap[caption[index].asString] = data.asInt
                    }

                    val pieEntries = arrayListOf<PieEntry>()
                    for (type in typeAmountMap.keys) {
                        pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
                    }

                    val pieDataSet = PieDataSet(pieEntries, "")

                    val colors = arrayListOf<Int>()
                    colors.add(Color.parseColor("#4f00cf"))
                    colors.add(Color.parseColor("#006da3"))
                    colors.add(Color.parseColor("#05b583"))
                    colors.add(Color.parseColor("#80b505"))
                    colors.add(Color.parseColor("#b5a305"))
                    colors.add(Color.parseColor("#f07911"))
                    colors.add(Color.parseColor("#cf1060"))
                    colors.add(Color.parseColor("#a30101"))
                    colors.add(Color.parseColor("#05afbb"))
                    colors.add(Color.parseColor("#8805c5"))
                    colors.add(Color.parseColor("#1f7414"))
                    colors.add(Color.parseColor("#7eabff"))

                    pieDataSet.colors = colors
                    pieDataSet.valueTextSize = 12.0f
                    pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                    val pieData = PieData(pieDataSet as IPieDataSet)
                    pieData.setDrawValues(true)
                    binding.registerOutlet.registerChart.setDrawCenterText(true)
                    binding.registerOutlet.registerChart.setDrawEntryLabels(false)
                    binding.registerOutlet.registerChart.setEntryLabelColor(-16777216)
                    binding.registerOutlet.registerChart.setDrawMarkers(true)
                    binding.registerOutlet.registerChart.setDrawSlicesUnderHole(true)
                    binding.registerOutlet.registerChart.extraTopOffset = 10.0f
                    binding.registerOutlet.registerChart.extraBottomOffset = 20.0f
                    binding.registerOutlet.registerChart.extraLeftOffset = 10.0f
                    binding.registerOutlet.registerChart.extraRightOffset = 10.0f
                    binding.registerOutlet.registerChart.holeRadius = 60.0f
                    val legend = binding.registerOutlet.registerChart.legend
                    legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                    legend.orientation = Legend.LegendOrientation.VERTICAL
                    legend.setDrawInside(false)
                    legend.xEntrySpace = 7.0f
                    legend.yEntrySpace = 0.0f
                    legend.yOffset = 40.0f
                    legend.isWordWrapEnabled = true
                    legend.setDrawInside(false)
                    legend.calculatedLineSizes
                    binding.registerOutlet.registerChart.description.isEnabled = false
                    binding.registerOutlet.registerChart.minAngleForSlices = 12.0f
                    binding.registerOutlet.registerChart.data = pieData
                    binding.registerOutlet.registerChart.invalidate()
                }
                else -> {
                    binding.soVsDo.progress.visibility = View.GONE
                    Toast.makeText(this, "Register Outlet - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initVisitPerformance() {
        viewModel.visitPerformance()
        viewModel.visitPerformance.observe(this, { response ->
            when(response) {
                is Resource.Loading -> {
                    binding.visitPerformance.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> response.let {
                    binding.visitPerformance.progress.visibility = View.GONE

                    val caption = response.data.data.get("caption").asJsonArray
                    val value = response.data.data.get("value").asJsonArray

                    val entries = arrayListOf<BarEntry>()
                    val labels = arrayListOf<String>()

                    caption.forEachIndexed { index, data ->
                        labels.add(data.asString)
                        entries.add(BarEntry(index.toFloat(), value[index].asFloat))
                    }

                    val barDataSet = BarDataSet(entries, "")
                    barDataSet.setGradientColor(resources.getColor(R.color.blue_500), resources.getColor(R.color.blue_100))

                    val data = BarData(barDataSet)
                    binding.visitPerformance.visitPerformanceChart.data = data
                    binding.visitPerformance.visitPerformanceChart.data.setValueTextSize(10f)
                    binding.visitPerformance.visitPerformanceChart.data.barWidth = 0.45f
                    binding.visitPerformance.visitPerformanceChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    binding.visitPerformance.visitPerformanceChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                    binding.visitPerformance.visitPerformanceChart.xAxis.granularity = 1f
                    binding.visitPerformance.visitPerformanceChart.xAxis.setCenterAxisLabels(false)
                    binding.visitPerformance.visitPerformanceChart.setGridBackgroundColor(Color.parseColor("#EDEDED"))
                    binding.visitPerformance.visitPerformanceChart.setDrawGridBackground(true)
                    binding.visitPerformance.visitPerformanceChart.setBorderColor(Color.WHITE)
                    binding.visitPerformance.visitPerformanceChart.setDrawBorders(true)
                    binding.visitPerformance.visitPerformanceChart.legend.isEnabled = false
                    binding.visitPerformance.visitPerformanceChart.axisRight.setDrawLabels(false)
                    binding.visitPerformance.visitPerformanceChart.description.isEnabled = false
                    binding.visitPerformance.visitPerformanceChart.isDoubleTapToZoomEnabled = false
                    binding.visitPerformance.visitPerformanceChart.setTouchEnabled(false)
                    binding.visitPerformance.visitPerformanceChart.setPinchZoom(false)
                    binding.visitPerformance.visitPerformanceChart.setScaleEnabled(false)
                    binding.visitPerformance.visitPerformanceChart.invalidate()
                }
                else -> {
                    binding.soVsDo.progress.visibility = View.GONE
                    Toast.makeText(this, "Visit Performance - Network failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.lastUpatedAt.text = "Last updated at ${Utils.currentDateTime()}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}