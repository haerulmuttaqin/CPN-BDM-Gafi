package co.id.cpn.bdmgafi.ui.splash

import android.Manifest
import android.Manifest.permission
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivitySplashBinding
import co.id.cpn.bdmgafi.ui.login.LoginActivity
import co.id.cpn.bdmgafi.util.AppUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val REQ_CODE_PERMISSIONS = 426
        private const val REQ_CODE_PERMISSIONS_SETTINGS = 427
    }

    private val permissionList = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel.getInfo(co.id.cpn.entity.util.Utils.getIMEIDeviceId(this), "1")
        
        if (!hasPermissions(this, permissionList)) {
            ActivityCompat.requestPermissions(this, permissionList, REQ_CODE_PERMISSIONS)
        } else {
            if (permissionChecker()) {
                showPermissionDialog()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }

    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!hasPermissions(this, permissionList)) {
            AlertDialog.Builder(this)
                .setTitle("We need some action!")
                .setMessage(
                    "Grant access - Change permission in your device app setting"
                )
                .setCancelable(false)
                .setPositiveButton("CLOSE") { _, _ ->
                    finish()
                }
                .setNegativeButton("SETTINGS") { _, _ ->
                    try {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_SETTINGS,
                            Uri.fromParts("package", packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivityForResult(intent, REQ_CODE_PERMISSIONS_SETTINGS)
                    } catch (e: Exception) {
                        Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${packageName}")).apply {
                            addCategory(Intent.CATEGORY_DEFAULT)
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(this)
                        }
                    }
                }
                .show()
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }

    private fun showPermissionDialog() {
        AppUtils.confirmDialog(
            this,
            "Access File Permission",
            "Please allow app for access storage",
            R.drawable.ic_permission,
            "",
            "OK",
            object : AppUtils.LeftRightConfirmListener {
                override fun onLeftClick() {}
                override fun onRightRight() {
                    if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        try {
                            val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                            intent.addCategory("android.intent.category.DEFAULT")
                            intent.data = Uri.parse(
                                java.lang.String.format(
                                    "package:%s",
                                    applicationContext.packageName
                                )
                            )
                            startActivity(intent)
                        } catch (e: java.lang.Exception) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                            startActivity(intent)
                        }
                    } else ActivityCompat.requestPermissions(
                        this@SplashActivity,
                        arrayOf(
                            permission.WRITE_EXTERNAL_STORAGE,
                            permission.READ_EXTERNAL_STORAGE
                        ),
                        333
                    )
                }
            }
        )
    }

    private fun permissionChecker(): Boolean {
        return if (VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            !Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(
                applicationContext,
                permission.WRITE_EXTERNAL_STORAGE
            )
            val read = ContextCompat.checkSelfPermission(
                applicationContext,
                permission.READ_EXTERNAL_STORAGE
            )
            write != PackageManager.PERMISSION_GRANTED ||
                    read != PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_PERMISSIONS_SETTINGS) {
            if (resultCode == 0) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionList,
                    REQ_CODE_PERMISSIONS
                )
            } 
        }
    }
}