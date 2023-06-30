package io.nextmillennium.nextandroidexample

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import io.nextmillennium.nextsdk.NextSdk

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initPreferences()
        if (BuildConfig.DEBUG) {
            NextSdk.disableCrashReports()
            NextSdk.enableLogging()
        }
        NextSdk.initialize(this, true) {
            it?.let { Log.d("APP", it.toString()) }
        }
    }

    private fun initPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        val unitsMap = mapOf(
            "banner" to "103",
            "banner_mrec" to "104",
            "banner_anchored" to "105",
            "interstitial" to "106",
            "rewarded" to "107",
            "native_small" to "108",
            "native_medium" to "109",
            "app_open" to "110",
            "inline" to "112"
        )
        unitsMap.forEach {
            if (!preferences.contains(it.key)) {
                editor.putString(it.key, it.value)
            }
        }
        editor.apply()
    }
}