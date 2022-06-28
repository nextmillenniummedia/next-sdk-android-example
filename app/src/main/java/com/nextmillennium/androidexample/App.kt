package com.nextmillennium.androidexample

import android.app.Application
import android.provider.Settings
import android.util.Log
import androidx.preference.PreferenceManager
import com.nextmillennium.inappsdk.core.InAppSdk
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initPreferences()
        val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val deviceId = md5(androidId).uppercase()
        val hashedScreens = arrayListOf(deviceId)
        InAppSdk.initialize(this, hashedScreens) {}
    }

    fun initPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        val unitsMap = mapOf(
            Pair("banner", "751"),
            Pair("banner_mrec", "752"),
            Pair("banner_anchored", "753"),
            Pair("interstitial", "754"),
            Pair("rewarded", "755"),
            Pair("native_small", "756"),
            Pair("native_medium", "757"),
        )
        unitsMap.forEach {
            if (!preferences.contains(it.key)) {
                editor.putString(it.key, it.value)
            }
        }
        editor.apply()
    }

    fun md5(androidId: String): String {
        try {
            val digest = MessageDigest.getInstance("MD5")
            digest.update(androidId.toByteArray())
            val messageDigest = digest.digest()
            val hexString = StringBuilder()
            for (part in messageDigest) {
                var h = Integer.toHexString(0xFF and part.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            Log.e("error", e.toString())
        }
        return ""
    }
}