package com.example.myapplication

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import java.security.AccessControlContext

class PingRequest {
    companion object {
        @Throws(UnknownHostException::class, IOException::class)
        fun sendPingRequest(ipAddress: String): Boolean {
            val geek = InetAddress.getByName(ipAddress)
            Log.i("sendPingRequest", "Sending Ping Request to $ipAddress")
            if (geek.isReachable(500)) {

                return true

            }
            return false
        }
    }
}


