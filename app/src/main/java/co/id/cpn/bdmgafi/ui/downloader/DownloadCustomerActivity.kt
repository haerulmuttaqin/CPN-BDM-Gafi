package co.id.cpn.bdmgafi.ui.downloader

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.id.cpn.bdmgafi.databinding.ActivityDownloaderBinding
import co.id.cpn.bdmgafi.ui.main.MainActivity
import co.id.cpn.bdmgafi.ui.main.MainViewModel
import co.id.cpn.entity.util.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class DownloadCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownloaderBinding
    private val viewModel: MainViewModel by viewModel()
    private val distName: String? = null
    private var distSID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = "Working Area"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.distributions.observe(this, { dist ->
            binding.distributionDropdown.setupData(dist)
            binding.distributionDropdown.setOnItemClickListener { _, _, position, _ ->
                binding.scroll.visibility = View.GONE
                binding.selectView.visibility = View.GONE
                binding.shimmer.visibility = View.VISIBLE
                binding.shimmer.startShimmer()
                viewModel.submitSelectedDistribution(dist[position].distributionSID)
                viewModel.regions(viewModel.distributionSelected.value!!).observe(this, { regions ->
                    binding.recyclerView.adapter = adapter
                    Handler().postDelayed({
                        adapter.addData(regions)
                        binding.scroll.visibility = View.VISIBLE
                        binding.shimmer.visibility = View.GONE
                        binding.shimmer.stopShimmer()
                        binding.emptyView.visibility = if (regions.isEmpty()) View.VISIBLE else View.GONE
                    },100)
                })
            }
        })
    }

    private val adapter: DownloadCustomerAdapter by lazy {
        DownloadCustomerAdapter(
            this,
            this,
            viewModel,
            viewModel.distributionSelected.value
        )
    }

    private fun readFile(fileName: String) {
        return try {
            val reader = BufferedReader(InputStreamReader(baseContext.openFileInput(fileName)))
            reader.use {
                val sb = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                val text = sb.toString()
                Toast.makeText(baseContext, text, Toast.LENGTH_LONG).show()
            }
        } catch (ex: FileNotFoundException) {
            Toast.makeText(baseContext, "Error in reading the file $fileName", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (intent?.getStringExtra("data_type") != null) {
            val dataType: String = intent?.getStringExtra("data_type") as String
            if (dataType == "single") {
                finish()
                return
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.KEY_DATA, "is_downloaded")
            intent.putExtra(Constants.DISTRIBUTION, distSID)
            intent.putExtra("dist_type", dataType);
            startActivity(intent);
        }
        finish();
    }
}