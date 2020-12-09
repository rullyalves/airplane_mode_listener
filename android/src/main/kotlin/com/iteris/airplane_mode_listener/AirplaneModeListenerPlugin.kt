package com.iteris.airplane_mode_listener

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar


class AirplaneModeListenerPlugin: FlutterPlugin,EventChannel.StreamHandler {

  private var eventChannel: EventChannel? = null
  private var applicationContext: Context? = null
  private var airplaneModeReceiver: AirplaneModeReceiver? = null

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    eventChannel = EventChannel(flutterPluginBinding.binaryMessenger,"airplane_mode_changed")
    eventChannel?.setStreamHandler(this)
    applicationContext = flutterPluginBinding.applicationContext;
  }


  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    eventChannel?.setStreamHandler(null);
    eventChannel = null;
    applicationContext = null;
  }

  override fun onListen(arguments: Any?, eventSink: EventChannel.EventSink?) {
    var isAirplaneMode: Boolean? =
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
      Settings.System.getInt(applicationContext?.contentResolver,
              Settings.System.AIRPLANE_MODE_ON, 0) == 1;
    }else{
      Settings.Global.getInt(applicationContext?.contentResolver,
              Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
    }

    eventSink?.success(isAirplaneMode ?: false)


   airplaneModeReceiver = AirplaneModeReceiver(eventSink)
    applicationContext?.registerReceiver(airplaneModeReceiver,
              IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
  }

  override fun onCancel(arguments: Any?) {
   applicationContext?.unregisterReceiver(airplaneModeReceiver)
    airplaneModeReceiver = null;
  }

}
