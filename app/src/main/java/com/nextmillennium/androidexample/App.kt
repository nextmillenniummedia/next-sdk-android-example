package com.nextmillennium.androidexample

import android.app.Application
import androidx.preference.PreferenceManager
import com.nextmillennium.inappsdk.core.InAppSdk

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initPreferences()
        InAppSdk.initialize(this)
    }

    fun initPreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        val unitsMap = mapOf(
            Pair("banner_anchored", "514"),
            Pair("banner_mrec", "515"),
            Pair("banner_large", "516"),
            Pair("banner", "517"),
            Pair("banner_full", "518"),
            Pair("interstitial", "415"),
            Pair("rewarded", "419"),
            Pair("rewarded_interstitial", "425"),
            Pair("in_content", "409"),
        )
        unitsMap.forEach {
            if (!preferences.contains(it.key)) {
                editor.putString(it.key, it.value)
            }
        }
        editor.apply()
    }
}