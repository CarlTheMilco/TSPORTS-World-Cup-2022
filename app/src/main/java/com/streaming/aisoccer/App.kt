package com.streaming.aisoccer

import android.app.Application
import com.applovin.sdk.AppLovinSdk
import com.streaming.aisoccer.utils.AdManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AdManager.initAd(this)
        AppLovinSdk.getInstance(this).mediationProvider = "max"
        AppLovinSdk.initializeSdk(this) { }
    }
}