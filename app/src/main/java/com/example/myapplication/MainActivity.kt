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
import org.reactivestreams.Subscriber


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ipaddr:String
        var host:Boolean=false
        pingBtn.setOnClickListener{
            ipaddr=getIP()
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

            Observable.fromCallable(object : Callable<Boolean> {

                @Throws(Exception::class)
                override fun call(): Boolean {

                    host=PingRequest.sendPingRequest(ipaddr)

                    return host
                }

            }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    onNext->printToast(onNext,ipaddr)
                }



            Log.i("onCreate","IP Address : $ipaddr")

          //  Toast.makeText(this,"IP Address is $ipaddr",Toast.LENGTH_SHORT).show()
        }

    }


    fun getIP():String{
        var ipad=iptext.text
        Log.i("getIP","text entered : $ipad")
     //   Toast.makeText(this,"IP Address is $ipad",Toast.LENGTH_SHORT).show()

        return ipad.toString()
    }

    @Throws(UnknownHostException::class, IOException::class)
    fun sendPingRequest(ipAddress: String):Unit {
        val geek = InetAddress.getByName(ipAddress)
        Log.i("sendPingRequest","Sending Ping Request to $ipAddress")
        if (geek.isReachable(500)) {
            runOnUiThread {
                Toast.makeText(this, "Host $ipAddress is reachable", Toast.LENGTH_SHORT).show()
            }
        }
        else
            runOnUiThread {
                Toast.makeText(this, "Host $ipAddress is reachable", Toast.LENGTH_SHORT).show()
            }

    }

    fun printToast(host:Boolean,ipAddress: String):Unit {
        if (host) {
            Toast.makeText(this, "Host $ipAddress is reachable", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Host $ipAddress is reachable", Toast.LENGTH_SHORT).show()
        }

    }

}
