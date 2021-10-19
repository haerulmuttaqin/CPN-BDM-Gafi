package co.id.cpn.bdmgafi.ui.dashboard

import android.R
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import co.id.cpn.bdmgafi.databinding.ActivityDashboardBinding
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardActivity: BaseActivity() {
    
    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModel()
    
    override fun observeViewModel() {
        
    }

    override fun initViewBinding() {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                binding.swipeRefresh.isRefreshing = false
            }, 3000)
        }
        initSoVsDoChart()
        initSoVsDoChart1()
        initSoVsDoChart2()
        initSoVsDoChart3()
        initReasonOrderLost()
        initRegisterOutlet()
        initVisitPerformance()
    }

    private fun initSoVsDoChart() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["DO"] = 450
        typeAmountMap["SO"] = 550

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#B1B1B1"))
        colors.add(Color.parseColor("#D50000"))

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
    }
    
    private fun initSoVsDoChart1() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["In"] = 300
        typeAmountMap["From"] = 700

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
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
        binding.soVsDo.soVsDoChart1.setDrawCenterText(false)
        binding.soVsDo.soVsDoChart1.setDrawEntryLabels(false)
        binding.soVsDo.soVsDoChart1.setDrawMarkers(false)
        binding.soVsDo.soVsDoChart1.setTouchEnabled(false)
        binding.soVsDo.soVsDoChart1.maxAngle = 180f
        binding.soVsDo.soVsDoChart1.rotationAngle = 180f
        binding.soVsDo.soVsDoChart1.holeRadius = 80f
        binding.soVsDo.soVsDoChart1.legend.isEnabled = false
        binding.soVsDo.soVsDoChart1.description.isEnabled = false
        binding.soVsDo.soVsDoChart1.data = pieData
        binding.soVsDo.soVsDoChart1.invalidate()
    }
    
    private fun initSoVsDoChart2() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["In"] = 900
        typeAmountMap["From"] = 100

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#FFAB00"))
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
        binding.soVsDo.soVsDoChart2.setDrawCenterText(false)
        binding.soVsDo.soVsDoChart2.setDrawEntryLabels(false)
        binding.soVsDo.soVsDoChart2.setDrawMarkers(false)
        binding.soVsDo.soVsDoChart2.setTouchEnabled(false)
        binding.soVsDo.soVsDoChart2.maxAngle = 180f
        binding.soVsDo.soVsDoChart2.rotationAngle = 180f
        binding.soVsDo.soVsDoChart2.holeRadius = 80f
        binding.soVsDo.soVsDoChart2.legend.isEnabled = false
        binding.soVsDo.soVsDoChart2.description.isEnabled = false
        binding.soVsDo.soVsDoChart2.data = pieData
        binding.soVsDo.soVsDoChart2.invalidate()
    }
    
    private fun initSoVsDoChart3() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["In"] = 1000
        typeAmountMap["From"] = 0

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#049E44"))
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
        binding.soVsDo.soVsDoChart3.setDrawCenterText(false)
        binding.soVsDo.soVsDoChart3.setDrawEntryLabels(false)
        binding.soVsDo.soVsDoChart3.setDrawMarkers(false)
        binding.soVsDo.soVsDoChart3.setTouchEnabled(false)
        binding.soVsDo.soVsDoChart3.maxAngle = 180f
        binding.soVsDo.soVsDoChart3.rotationAngle = 180f
        binding.soVsDo.soVsDoChart3.holeRadius = 80f
        binding.soVsDo.soVsDoChart3.legend.isEnabled = false
        binding.soVsDo.soVsDoChart3.description.isEnabled = false
        binding.soVsDo.soVsDoChart3.data = pieData
        binding.soVsDo.soVsDoChart3.invalidate()
    }
    
    private fun initReasonOrderLost() {
        val pieEntries: ArrayList<PieEntry> = ArrayList()
        val label = "type"

        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()
        typeAmountMap["Price Issue"] = 60
        typeAmountMap["Operational Issue"] = 56
        typeAmountMap["Payment"] = 40
        typeAmountMap["Inventory"] = 30
        typeAmountMap["Quantity"] = 25
        typeAmountMap["Competitors"] = 20

        //initializing colors for the entries
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#4f00cf"))
        colors.add(Color.parseColor("#006da3"))
        colors.add(Color.parseColor("#05b583"))
        colors.add(Color.parseColor("#80b505"))
        colors.add(Color.parseColor("#b5a305"))
        colors.add(Color.parseColor("#f07911"))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        pieDataSet.valueTextSize = 12f
        pieDataSet.colors = colors
        pieDataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        pieDataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        binding.reasonOrderlist.reasonChart.setDrawCenterText(true)
        binding.reasonOrderlist.reasonChart.setDrawEntryLabels(true)
        binding.reasonOrderlist.reasonChart.setEntryLabelColor(Color.BLACK)
        binding.reasonOrderlist.reasonChart.setDrawMarkers(true)
        binding.reasonOrderlist.reasonChart.setDrawSlicesUnderHole(true)
        binding.reasonOrderlist.reasonChart.extraTopOffset = 10f
        binding.reasonOrderlist.reasonChart.extraBottomOffset = 10f
        binding.reasonOrderlist.reasonChart.holeRadius = 80f
        binding.reasonOrderlist.reasonChart.legend.isEnabled = false
        binding.reasonOrderlist.reasonChart.description.isEnabled = false
        binding.reasonOrderlist.reasonChart.data = pieData
        binding.reasonOrderlist.reasonChart.invalidate()
    }
    
    private fun initRegisterOutlet() {
        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 49f))
        entries.add(BarEntry(1f, 43f))
        entries.add(BarEntry(2f, 38f))
        entries.add(BarEntry(3f, 30f))
        entries.add(BarEntry(4f, 8f))
        
        val labels = ArrayList<String>()
        labels.add("Cov")
        labels.add("On Asset")
        labels.add("Visit")
        labels.add("Trading")
        labels.add("NOO")
        
        val barDataSet = BarDataSet(entries, "")
        barDataSet.setGradientColor(Color.BLUE, resources.getColor(R.color.holo_blue_dark))

        val data = BarData(barDataSet)
        binding.registerOutlet.registerChart.data = data
        binding.registerOutlet.registerChart.data.setValueTextSize(10f)
        binding.registerOutlet.registerChart.data.barWidth = 0.45f
        binding.registerOutlet.registerChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        binding.registerOutlet.registerChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.registerOutlet.registerChart.xAxis.granularity = 1f
        binding.registerOutlet.registerChart.xAxis.setCenterAxisLabels(false)
        binding.registerOutlet.registerChart.setGridBackgroundColor(Color.parseColor("#EDEDED"))
        binding.registerOutlet.registerChart.setDrawGridBackground(true)
        binding.registerOutlet.registerChart.setBorderColor(Color.WHITE)
        binding.registerOutlet.registerChart.setDrawBorders(true)
        binding.registerOutlet.registerChart.legend.isEnabled = false
        binding.registerOutlet.registerChart.axisRight.setDrawLabels(false)
        binding.registerOutlet.registerChart.description.isEnabled = false
        binding.registerOutlet.registerChart.invalidate()
    }


    private fun initVisitPerformance() {
        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 47f))
        entries.add(BarEntry(1f, 20f))
        entries.add(BarEntry(2f, 24f))
        entries.add(BarEntry(3f, 28f))
        entries.add(BarEntry(4f, 7f))
        entries.add(BarEntry(4f, 7f))
        entries.add(BarEntry(4f, 3f))

        val labels = ArrayList<String>()
        labels.add("Schedule")
        labels.add("EC")
        labels.add("Call")
        labels.add("EX")
        labels.add("IV")
        labels.add("DC")
        labels.add("Not Yet")

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setGradientColor(resources.getColor(R.color.holo_blue_dark), resources.getColor(R.color.holo_blue_bright))

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
        binding.visitPerformance.visitPerformanceChart.invalidate()
    }
}