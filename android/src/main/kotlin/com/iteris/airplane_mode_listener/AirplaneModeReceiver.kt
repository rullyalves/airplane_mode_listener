package com.iteris.airplane_mode_listener

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

class AirplaneModeReceiver(val eventSink: EventChannel.EventSink?) : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val airplaneMode = it.getBooleanExtra("state",false);
            eventSink?.success(airplaneMode);

        }
    }

}