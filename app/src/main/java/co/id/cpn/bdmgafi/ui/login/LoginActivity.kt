package co.id.cpn.bdmgafi.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import co.id.cpn.bdmgafi.databinding.ActivityLoginBinding
import co.id.cpn.bdmgafi.ui.main.MainActivity
import co.id.cpn.bdmgafi.ui.base.BaseActivity
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
import java.lang.NullPointerException
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.nio.file.NoSuchFileException
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.Ref.ObjectRef
import kotlin.text.Charsets.UTF_8

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var loginRequest: LoginRequest

    private lateinit var deviceID: String
    private lateinit var handSetID: String

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


        binding.textVersion.text = Utils.appVersion(this@LoginActivity)

        val serverKey = SharedPref(this@LoginActivity).userKey.keyS
        if (serverKey != null) {
            binding.serverKeyLayout.visibility = View.GONE
            binding.serverKey.setText(serverKey)
        }

//        binding.rememberMe.setOnCheckedChangeListener { _, isChecked ->
//            loginRequest.isRemember = isChecked
//        }

        binding.loginButton.setOnClickListener {
            doLogin()
        }

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
//                    "352014090748281",
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
        val myExternalFile = File(
            Intrinsics.stringPlus(
                Environment.getExternalStorageDirectory().toString(),
                Constants.PATH
            ), fileName
        )
        if (!myExternalFile.exists()) {
            createFile(fileName, getDeviceID(this).toString())
        }
        try {
            val fileInputStream = FileInputStream(myExternalFile)
            val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
            val stringBuilder = StringBuilder()
            var text = ""
            while ((bufferedReader.readLine().also { text = it }) != null) {
                stringBuilder.append(text)
            }
            deviceID = stringBuilder.toString()
            val activityLoginBinding = binding
            if (activityLoginBinding != null) {
                activityLoginBinding.textHandsetKey.text = Intrinsics.stringPlus(
                    "Handset Key: ",
                    deviceID
                )
                fileInputStream.close()
                return
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

//    private fun readFileHandSet(fileName: String) {
//        val myExternalFile = File(
//            Intrinsics.stringPlus(
//                Environment.getExternalStorageDirectory().toString(),
//                Constants.PATH
//            ), fileName
//        )
//        if (myExternalFile.exists() && fileName != null) {
//            if (fileName == null) {
//                throw NullPointerException("null cannot be cast to non-null type kotlin.CharSequence")
//            } else if (!Intrinsics.areEqual(
//                    fileName as CharSequence?. trim ().toString() as Any,
//                    "" as Any
//                )
//            ) {
//                try {
//                    val fileInputStream = FileInputStream(myExternalFile)
//                    val bufferedReader =
//                        BufferedReader(InputStreamReader(fileInputStream))
//                    val stringBuilder = StringBuilder()
//                    val text: ObjectRef<*> = ObjectRef<Any?>()
//                    while (`LoginActivity$readFileHandSet$1`(
//                            text,
//                            bufferedReader
//                        ).invoke() != null
//                    ) {
//                        stringBuilder.append(text.element as String)
//                    }
//                    this.handSetID = stringBuilder.toString()
//                    Log.w(TAG, Intrinsics.stringPlus("handsetid: ", this.handSetID))
//                    if (!Intrinsics.areEqual(this.handSetID as Any?, "" as Any)) {
//                        binding.serverKeyLayout.visibility = View.VISIBLE
//                        binding.serverKey.setText(this.handSetID)
//                    }
//                    fileInputStream.close()
//                } catch (e: NoSuchFileException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }

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
//        readFileHandSet(".data.1.0.skey.txt")
    }

}