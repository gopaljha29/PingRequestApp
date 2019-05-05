package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import android.R.attr.duration
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import java.util.concurrent.Callable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.flowable.FlowableBlockingSubscribe.subscribe
import io.reactivex.internal.util.HalfSerializer.onNext
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    var ipaddr:String=""
    private val TAG="MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"onCreate called");

//          //  class SimpleThread: Thread() {
//            //    override fun run() {
//                   val host:Boolean= PingRequest.sendPingRequest(ipaddr)
//                    if(host)
//                       // runOnUiThread {
//                            Toast.makeText(this, "Host $ipaddr is reachable", Toast.LENGTH_SHORT).show()
//                      //  }
//                    else
//                     //   runOnUiThread {
//                            Toast.makeText(this, "Host $ipaddr is not reachable", Toast.LENGTH_SHORT).show()
//                      //  }
//                //}
//           // }
//           // val thread = SimpleThread()
//          //  thread.start()

//            Observable.fromCallable(object : Callable<Boolean> {
//
//                @Throws(Exception::class)
//                override fun call(): Boolean {
//                   return PingRequest.sendPingRequest(ipaddr)
//                }
//
//            }).subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe{
//                    onNext->printToast(onNext,ipaddr)
//                }




          //  Toast.makeText(this,"IP Address is $ipaddr",Toast.LENGTH_SHORT).show()
        }



    override fun onStart() {
        super.onStart()
        Log.i(TAG,"onStart called")
        pingBtn.setOnClickListener {
            ipaddr = getIP()
            GlobalScope.launch {
                try {
                    var flag = sendPingRequest(ipaddr)
                    runOnUiThread {
                        printToast(flag, ipaddr)
                    }
                } catch (e: UnknownHostException) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "UNKOWN HOST", Toast.LENGTH_SHORT).show()
                    }
                    Log.e(TAG, "onStart: UnkownHostException")

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            Log.i(TAG, "onCreate: IP Address : $ipaddr")
        }
    }
    private fun getIP():String{
        var ipad=iptext.text
        Log.i("","getIP: text entered : $ipad")
     //   Toast.makeText(this,"IP Address is $ipad",Toast.LENGTH_SHORT).show()

        return ipad.toString()
    }

    @Throws(UnknownHostException::class, IOException::class)
    suspend fun sendPingRequest(ipAddress: String):Boolean{
        var flag:Boolean
       // var job= GlobalScope.launch {
            val geek = InetAddress.getByName(ipAddress)
            Log.i(TAG,"sendPingRequest: Sending Ping Request to $ipAddress")
            flag = geek.isReachable(500)
            return flag
       // }
//        job.invokeOnCompletion {
//            runOnUiThread {
//                printToast(flag, ipAddress)
//            }
//        }
    }

    private fun printToast(host:Boolean,ipAddress: String) {
        if (host) {
            Toast.makeText(this, "Host $ipAddress is reachable", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Host $ipAddress is not reachable", Toast.LENGTH_SHORT).show()
        }
    }
    private fun printStringOnToast():(String)-> Unit={
        Toast.makeText(this,it,Toast.LENGTH_SHORT).show()

    }

}


