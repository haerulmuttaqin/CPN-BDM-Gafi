package co.id.cpn.bdmgafi.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.id.cpn.bdmgafi.R

abstract class BaseActivity : AppCompatActivity() {

    val TAG = "-->"

    var progressBuilder: AlertDialog.Builder? = null
    private var progressDialog: ProgressDialog? = null
    var dialog: Dialog? = null

    abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initProgressBar()
        observeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    
    private fun initProgressBar() {
        progressBuilder = AlertDialog.Builder(this, R.style.ProgressDialogStyle2)
        val inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("InflateParams") val view: View = inflater.inflate(R.layout.dialog_progress, null)
        progressBuilder!!.setView(view)
        dialog = progressBuilder!!.create()
        (dialog as AlertDialog).setCancelable(false)
        progressDialog = ProgressDialog(this, R.style.ProgressDialogStyle)
        progressDialog!!.setMessage("Please wait... ")
        progressDialog!!.setCancelable(false)
    }

    open fun showProgress() {
        try {
            hideKeyboard()
            if (dialog != null);
                dialog!!.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    open fun hideProgress() {
        try {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    open fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}