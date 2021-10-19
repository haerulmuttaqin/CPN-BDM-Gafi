package co.id.cpn.bdmgafi.ui.downloader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityDownloaderBinding
import co.id.cpn.bdmgafi.databinding.ActivityMainBinding
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class DownloaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDownloaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize PRDownloader with read and connection timeout
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(applicationContext, config)

        binding.btnDownload.setOnClickListener {
            val url =
                "https://raw.githubusercontent.com/javahelps/externalsqliteimporter/master/README.md"
            val fileName = "readme.md"

            download(url, fileName)
        }
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

    private fun download(url: String, fileName: String) {
        PRDownloader.download(
            url,
            baseContext.filesDir.absolutePath,
            fileName
        )
            .build()
            .setOnProgressListener {
                // Update the progress
                binding.progressBar.max = it.totalBytes.toInt()
                binding.progressBar.progress = it.currentBytes.toInt()
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    // Update the progress bar to show the completeness
                    binding.progressBar.max = 100
                    binding.progressBar.progress = 100
                    
                    // Read the file
                    readFile(fileName)
                }

                override fun onError(error: com.downloader.Error?) {
                    Toast.makeText(baseContext, "Failed to download the $url", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}