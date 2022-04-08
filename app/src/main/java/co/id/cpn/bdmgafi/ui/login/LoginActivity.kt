package co.id.cpn.bdmgafi.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import co.id.cpn.bdmgafi.R
import co.id.cpn.bdmgafi.databinding.ActivityLoginBinding
import co.id.cpn.bdmgafi.ui.base.BaseActivity
import co.id.cpn.bdmgafi.ui.main.MainActivity
import co.id.cpn.bdmgafi.util.AppUtils
import co.id.cpn.data.local.spref.SharedPref
import co.id.cpn.entity.DataBody
import co.id.cpn.entity.Resource
import co.id.cpn.entity.login.LoginRequest
import co.id.cpn.entity.login.LoginResponse
import co.id.cpn.entity.util.Constants
import co.id.cpn.entity.util.Utils
import co.id.cpn.entity.util.Utils.getDeviceID
import com.google.gson.JsonObject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*
import java.nio.charset.Charset
import kotlin.jvm.internal.Intrinsics
import kotlin.text.Charsets.UTF_8

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var loginRequest: LoginRequest

    private lateinit var deviceID: String
    private lateinit var serverKey: String

    override fun initViewBinding() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createDir()

        if (SharedPref(this@LoginActivity).loggedIn) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }


        binding.textVersion.text = "App Version ${Utils.appVersion(this@LoginActivity)}"

//        binding.rememberMe.setOnCheckedChangeListener { _, isChecked ->
//            loginRequest.isRemember = isChecked
//        }

        binding.loginButton.setOnClickListener {
            doLogin()
        }

        binding.resetServerKey.setOnClickListener {
            resetServerKey()
        }

        viewModel.generateModules()

    }

    private fun resetServerKey() {
        AppUtils.confirmDialog(this,
            "Reset Server Key", "Are you sure?",
            R.drawable.ic_undraw_login_re_4vu2, "Cancel", "Yes",
            object: AppUtils.LeftRightConfirmListener {
                override fun onLeftClick() {
                    dialog?.dismiss()
                }
                override fun onRightRight() {
                    removeConfigFile()
                    startActivity(Intent(this@LoginActivity, LoginActivity::class.java))
                    finish()
                }
            }
        )
    }

    override fun observeViewModel() {
        viewModel.loginLiveData.observe(this, ::handleLoginResult)
        viewModel.token.observe(this, ::handleTokenResult)
    }

    private fun doLogin() {
        when {
            binding.username.text.toString().isEmpty() -> {
                binding.username.error = "This field can't be empty"
                binding.username.requestFocus()
            }
            binding.password.text.toString().isEmpty() -> {
                binding.password.error = "This field can't be empty"
                binding.password.requestFocus()
            }
            binding.serverKey.text.toString().isEmpty() -> {
                binding.serverKey.error = "This field can't be empty"
                binding.serverKey.requestFocus()
            }
            else -> {
                loginRequest = LoginRequest(
                    binding.username.text.toString(),
                    binding.password.text.toString(),
                    binding.serverKey.text.toString(),
                    Utils.getIMEIDeviceId(this),
                    Utils.appVersion(this),
                    Utils.getOprName(this),
                    106.5231531,
                    4.5123145,
                    Utils.currentDateTime(),
                    binding.rememberMe.isChecked
                )
                viewModel.doLogin(
                    loginRequest
                )
            }
        }
    }

    private fun handleLoginResult(response: Resource<DataBody<LoginResponse>>) {
        when (response) {
            is Resource.Loading -> {
                disableView()
            }
            is Resource.Success -> response.data.let {

                createFile(".data.1.0.skey.txt", loginRequest.keyS)

                for (dist in response.data.data.listDistribution) {
                    viewModel.storeDistribution(dist)
                }

                SharedPref(this@LoginActivity).apply {
                    user = response.data.data
                    loggedIn = true
                    userKey = loginRequest
                }

                viewModel.getToken(response.data.data.authorization)

            }
            is Resource.Failed -> {
                enableView()
                if (response.code == Constants.CODE_UNAUTHORIZED) {
                    AppUtils.errorDialog(
                        this,
                        response.message
                    )
                } else {
                    AppUtils.errorDialog(
                        this,
                        response.message,
                    )
                }
            }
            is Resource.Error -> {
                enableView()
                AppUtils.errorDialog(
                    this,
                    response.throwable.localizedMessage
                )
            }
            else -> {
                enableView()
                AppUtils.errorDialog(
                    this,
                    "Empty response from server!",
                )
            }
        }
    }

    private fun handleTokenResult(response: Resource<DataBody<JsonObject>>) {
        when (response) {
            is Resource.Loading -> {
                disableView()
            }
            is Resource.Success -> response.data.let {
                SharedPref(this).token = response.data.data.get("TokenAuth").asString
                navigateToMainScreen()
            }
            is Resource.Failed -> {
                enableView()
                if (response.code == Constants.CODE_UNAUTHORIZED) {
                    AppUtils.errorDialog(
                        this,
                        response.message
                    )
                } else {
                    AppUtils.errorDialog(
                        this,
                        response.message,
                    )
                }
            }
            is Resource.Error -> {
                enableView()
                AppUtils.errorDialog(
                    this,
                    response.throwable.localizedMessage
                )
            }
            else -> {
                enableView()
                AppUtils.errorDialog(
                    this,
                    "Empty response from server!",
                )
            }
        }
    }

    private fun disableView() {
        binding.progress.isEnabled = true
        binding.loginButton.text = ""
        binding.loginButton.isEnabled = false
        binding.username.isEnabled = false
        binding.password.isEnabled = false
        binding.serverKey.isEnabled = false
        binding.rememberMe.isEnabled = false
    }

    private fun enableView() {
        binding.progress.isEnabled = false
        binding.loginButton.text = "Login"
        binding.loginButton.isEnabled = true
        binding.username.isEnabled = true
        binding.password.isEnabled = true
        binding.serverKey.isEnabled = true
        binding.rememberMe.isEnabled = true
    }

    private fun navigateToMainScreen() {
        val nextScreenIntent = Intent(this, MainActivity::class.java)
        startActivity(nextScreenIntent)
        finish()
    }


    private fun createDir() {
        var file2 = File(Environment.getExternalStorageDirectory().toString() + Constants.PATH)
        if (!file2.exists()) {
            file2.mkdir()
            file2 = File(Environment.getExternalStorageDirectory().toString() + Constants.PATH_IMG)
            if (!file2.exists()) {
                file2.mkdir()
            }
        } else {
            file2 = File(Environment.getExternalStorageDirectory().toString() + Constants.PATH_IMG)
            if (!file2.exists()) {
                file2.mkdir()
            }
        }
        var file3 = File(Environment.getExternalStorageDirectory().toString() + Constants.PATH)
        if (!file3.exists()) {
            file3.mkdir()
            file3 =
                File(Environment.getExternalStorageDirectory().toString() + Constants.PATH_DOWNLOAD)
            if (!file3.exists()) {
                file3.mkdir()
            }
        } else {
            file3 =
                File(Environment.getExternalStorageDirectory().toString() + Constants.PATH_DOWNLOAD)
            if (!file3.exists()) {
                file3.mkdir()
            }
        }
    }


    private fun readFileDeviceID(fileName: String) {
        val myExternalFile = File(Environment.getExternalStorageDirectory().toString() + Constants.PATH, fileName)
        if (!myExternalFile.exists()) {
            createFile(fileName, getDeviceID(this).toString())
        }
        try {
            val text = myExternalFile.readText(UTF_8)
            deviceID = text
            binding.textHandsetKey.text = "Handset Key: $deviceID"
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun readFileServerKey(fileName: String) {
        val myExternalFile = File(
            Environment.getExternalStorageDirectory().toString() +
            Constants.PATH, fileName
        )
        if (myExternalFile.exists()) {
            try {
                val text = myExternalFile.readText(UTF_8)
                serverKey = text
                if (serverKey != "") {
                    binding.serverKeyLayout.visibility = View.GONE
                    binding.serverKey.setText(serverKey)
                } else {
                    binding.serverKeyLayout.visibility = View.VISIBLE
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun createFile(fileName: String, content: String?) {
        try {
            val fileOutPutStream = FileOutputStream(
                File(
                    Intrinsics.stringPlus(
                        Environment.getExternalStorageDirectory().toString(),
                        Constants.PATH
                    ), fileName
                )
            )
            val charset: Charset = UTF_8
            if (content != null) {
                val bytes = content.toByteArray(charset)
                fileOutPutStream.write(bytes)
                fileOutPutStream.close()
                return
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun removeConfigFile() {
        val file1 = File(
            Intrinsics.stringPlus(
                Environment.getExternalStorageDirectory().toString(),
                Constants.PATH
            ), ".data.1.0.device.txt"
        )
        val file2 = File(
            Intrinsics.stringPlus(
                Environment.getExternalStorageDirectory().toString(),
                Constants.PATH
            ), ".data.1.0.skey.txt"
        )
        if (file1.exists()) {
            file1.delete()
        }
        if (file2.exists()) {
            file2.delete()
        }
    }


    override fun onResume() {
        super.onResume()
        readFileDeviceID(".data.1.0.device.txt")
        readFileServerKey(".data.1.0.skey.txt")
    }

}