package com.techm.techMEmployeeManagement.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.techm.techMEmployeeManagement.application.ApplicationContext.Companion.context

class NetworkConnection {
    companion object {
        /**
         * checking internet is available or not
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun isNetworkConnected(): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork

            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities != null &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        fun isNetworkConnectedKitkat(): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.isActiveNetworkMetered
        }
    }


}