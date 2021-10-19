package co.id.cpn.entity.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by AhmedEltaher
 */

class Network constructor(val context: Context) : NetworkConnectivity {
    override fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }

    override fun isConnected(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }
}

interface NetworkConnectivity {
    fun getNetworkInfo(): NetworkInfo?
    fun isConnected(): Boolean
}