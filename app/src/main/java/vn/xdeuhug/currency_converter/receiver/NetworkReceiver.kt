package vn.xdeuhug.currency_converter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 26 / 10 / 2024
 */
class NetworkReceiver @Inject constructor(
    @ApplicationContext private val context: Context
) : BroadcastReceiver() {

    private val networkStatus = MutableLiveData<Boolean>()

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent?) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        networkStatus.value = activeNetwork?.isConnectedOrConnecting == true
    }

    fun getNetworkStatus(): LiveData<Boolean> = networkStatus
}
