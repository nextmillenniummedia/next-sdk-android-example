package com.nextmillennium.androidexample

import android.app.Application
import androidx.preference.PreferenceManager
import com.nextmillennium.inappsdk.core.InAppSdk

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initPreferences()
        InAppSdk.initialize(this, true)
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
}