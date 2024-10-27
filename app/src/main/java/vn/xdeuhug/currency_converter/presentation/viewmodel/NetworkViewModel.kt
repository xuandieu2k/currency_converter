package vn.xdeuhug.currency_converter.presentation.viewmodel

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.xdeuhug.currency_converter.receiver.NetworkReceiver
import javax.inject.Inject

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 26 / 10 / 2024
 */
@HiltViewModel
class NetworkViewModel @Inject constructor(
    application: Application,
    private val networkReceiver: NetworkReceiver
) : AndroidViewModel(application) {

    private val _isNetworkAvailable = MediatorLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> get() = _isNetworkAvailable

    init {
        @Suppress("DEPRECATION")
        getApplication<Application>().registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        _isNetworkAvailable.addSource(networkReceiver.getNetworkStatus()) { status ->
            _isNetworkAvailable.value = status
        }
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().unregisterReceiver(networkReceiver)
    }
}

