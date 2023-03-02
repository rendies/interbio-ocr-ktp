package com.interbioocrktp

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import android.content.Intent
import android.app.Activity
import com.enuba.ocrktpv2.*
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import android.util.Base64
import org.json.JSONObject
import android.util.Log

class InterbioOcrKtpModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  var loading = false;
  var result: String = "";
  var ktpImageBase64: String = "";
  var signatureBase64: String = "";
  var isPhotocopy: Boolean = false;
  var isPhotocopyScore: Float = 0f;
  override fun getName(): String {
    return NAME
  }

  @ReactMethod
  fun getKtpImage(): String {
    return ktpImageBase64
  }
  @ReactMethod
  fun getSignature(): String {
    return signatureBase64
  }
  @ReactMethod
  fun getIsPhotocopyScore(): Float {
    return isPhotocopyScore
  }
  @ReactMethod
  fun getIsPhotocopy(): Boolean {
    return isPhotocopy
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
        this.result = Gson().toJson(it.demographicData)
        this.isPhotocopy = it.isPhotocopy
        this.isPhotocopyScore = it.isPhotocopyScore
        this.signatureBase64 = encodeImage(it.signature)!!
        this.ktpImageBase64 = encodeImage(it.ktpImage)!!
        this.loading= false

        val rootObject= JSONObject()
        rootObject.put("demographicData", this.result)
        rootObject.put("isPhotocopy", this.isPhotocopy)
        rootObject.put("ktpImage", this.ktpImageBase64)
        rootObject.put("signature", this.signatureBase64)
        rootObject.put("isPhotocopyScore", this.isPhotocopyScore)
        promise.resolve(rootObject.toString())
      }
    }
  }

  private fun encodeImage(bm: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
  }

  companion object {
    const val NAME = "InterbioOcrKtp"
  }
}
