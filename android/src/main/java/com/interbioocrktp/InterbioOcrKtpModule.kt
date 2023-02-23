package com.interbioocrktp

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import android.content.Intent
import android.app.Activity
import com.enuba.ocrktpv2.*
import com.google.gson.Gson

class InterbioOcrKtpModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  var loading = false;
  var result: String = "";
  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Double, b: Double, promise: Promise) {
    promise.resolve(a * b)
  }

  @ReactMethod
  fun getOcrKtpResult(): String {
    android.util.Log.d("OCRKTP", "getOcrKtpResult: " + result.toString())
    return this.result
  }

  @ReactMethod
  fun isLoading(): Boolean {
    return this.loading
  }

  @ReactMethod
  fun loadOcrKtpActivity(promise: Promise) {
    this.loading = true
    var intent = Intent(reactApplicationContext, KtpReaderActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    reactApplicationContext.startActivity(intent)
    PassedData.onKtpParsed = { ocrResult: OCRResult? ->
      ocrResult?.let {
        android.util.Log.d("OCRKTP", "loadOcrKtpActivity: " + Gson().toJson(it))
        this.result = Gson().toJson(it)
        this.loading= false
        promise.resolve(Gson().toJson(it))
      }
    }
  }

  companion object {
    const val NAME = "InterbioOcrKtp"
  }
}
