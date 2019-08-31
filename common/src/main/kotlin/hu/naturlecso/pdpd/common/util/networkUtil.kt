package hu.naturlecso.pdpd.common.util

import android.net.ConnectivityManager

// permissions declared in the app module
// have to use deprecated methods because of the low API level
@Suppress("MissingPermission", "deprecation")
fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
    val activeNetwork = connectivityManager.activeNetworkInfo

    return activeNetwork?.let {
        activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
        activeNetwork.type == ConnectivityManager.TYPE_MOBILE
    } ?: false
}
