package com.streaming.aisoccer.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.applovin.mediation.*
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdkUtils
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.huawei.hms.ads.*
import com.huawei.hms.ads.banner.BannerView
import com.streaming.aisoccer.R

object AdManager {
    @JvmField
    var adCounter = 1
    @JvmField
    var isloadFbAd = false
    fun initAd(context: Context?) {
        MobileAds.initialize(context!!) { initializationStatus: InitializationStatus? -> }
        HwAds.init(context)
    }

    var bannerView: BannerView? = null
    const val REFRESH_TIME = 60
    @JvmStatic
    fun loadBannerAd(context: Context, adContainer: LinearLayout) {
        bannerView = BannerView(context)
        // Set an ad slot ID.
        bannerView!!.adId = context.getString(R.string.banner_ad_id)
        bannerView!!.setBannerRefresh(REFRESH_TIME.toLong())
        // Set the background color and size based on user selection.
        bannerView!!.bannerAdSize = BannerAdSize.BANNER_SIZE_320_50
        bannerView!!.setBackgroundColor(Color.WHITE)
        adContainer.addView(bannerView)
        bannerView!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Called when an ad is loaded successfully.
            }

            override fun onAdFailed(errorCode: Int) {
                // Called when an ad fails to be loaded.
                bannerView!!.destroy()
                maxBanner(context as Activity, adContainer)
            }

            override fun onAdOpened() {
                // Called when an ad is opened.
            }

            override fun onAdClicked() {
                // Called when a user taps an ad.
            }

            override fun onAdLeave() {
                // Called when a user has left the app.
                bannerView!!.destroy()
            }

            override fun onAdClosed() {
                // Called when an ad is closed.
                bannerView!!.destroy()
                maxBanner(context as Activity, adContainer)
            }
        }
        bannerView!!.loadAd(AdParam.Builder().build())
    }

    var interstitialAd: InterstitialAd? = null
    @JvmStatic
    fun loadInterAd(context: Context) {
        interstitialAd = InterstitialAd(context)
        interstitialAd!!.adId = context.resources.getString(R.string.image_ad_id)
        val adParam = AdParam.Builder().build()
        interstitialAd!!.loadAd(adParam)
    }

    @JvmStatic
    fun showInterAd(context: Activity, intent: Intent?) {
        if (interstitialAd!!.isLoaded) {
            interstitialAd!!.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    super.onAdLoaded()
                }

                override fun onAdFailed(errorCode: Int) {
                    maxInterstital(context)
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    startActivity(context, intent)
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                }
            }
            interstitialAd!!.show(context)
        } else {
            showMaxInterstitial(context, intent)
        }
    }

    fun startActivity(context: Context, intent: Intent?) {
        if (intent != null) {
            context.startActivity(intent)
        }
    }

    @SuppressLint("StaticFieldLeak")
    var maxAdView: MaxAdView? = null
    @JvmStatic
    fun maxBanner(activity: Activity, linearLayout: LinearLayout) {
        maxAdView = MaxAdView(activity.resources.getString(R.string.max_banner), activity)
        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        // Banner height on phones and tablets is 50 and 90, respectively
        val heightPx = activity.resources.getDimensionPixelSize(R.dimen.banner_height)
        maxAdView!!.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        // Set background or background color for banners to be fully functional
        maxAdView!!.setBackgroundColor(activity.resources.getColor(R.color.main_bg_home_color))
        maxAdView!!.setListener(object : MaxAdViewAdListener {
            override fun onAdExpanded(ad: MaxAd) {}
            override fun onAdCollapsed(ad: MaxAd) {}
            override fun onAdLoaded(ad: MaxAd) {}
            override fun onAdDisplayed(ad: MaxAd) {}
            override fun onAdHidden(ad: MaxAd) {}
            override fun onAdClicked(ad: MaxAd) {}
            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {}
            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {}
        })
//        if (Utils.isNetworkAvailable(activity)) {
        linearLayout.addView(maxAdView)
        // Load the banner
        if (maxAdView != null) {
            maxAdView!!.loadAd()
        }
    }

    @SuppressLint("StaticFieldLeak")
    var maxAdAdaptive: MaxAdView? = null
    fun maxBannerAdaptive(activity: Activity, linearLayout: LinearLayout) {
        maxAdAdaptive = MaxAdView(activity.resources.getString(R.string.max_banner), activity)
        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        // Get the adaptive banner height.
        val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).height
        val heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp)
        maxAdAdaptive!!.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        maxAdAdaptive!!.setExtraParameter("adaptive_banner", "true")
        // Set background or background color for banners to be fully functional
        maxAdAdaptive!!.setBackgroundColor(activity.resources.getColor(R.color.main_bg_home_color))
        linearLayout.addView(maxAdAdaptive)
        // Load the adaptive
        if (maxAdAdaptive != null) {
            maxAdAdaptive!!.loadAd()
        }
    }

    var maxIntent: Intent? = null
    var maxInterstitialAd: MaxInterstitialAd? = null
    @JvmStatic
    fun maxInterstital(activity: Activity) {
        maxInterstitialAd = MaxInterstitialAd(activity.resources.getString(R.string.max_interstitial), activity)
        maxInterstitialAd!!.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd) {}
            override fun onAdDisplayed(ad: MaxAd) {}
            override fun onAdHidden(ad: MaxAd) {
                maxInterstitialAd!!.loadAd()
                startActivity(activity, maxIntent)
            }

            override fun onAdClicked(ad: MaxAd) {}
            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                maxInterstitialAd!!.loadAd()
                loadInterAd(activity as Context)
                startActivity(activity, maxIntent)
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                maxInterstitialAd!!.loadAd()
                startActivity(activity, maxIntent)
            }
        })
        maxInterstitialAd!!.loadAd()
    }

    @JvmStatic
    fun showMaxInterstitial(context: Activity, intent: Intent?) {
        maxIntent = intent
        if (maxInterstitialAd != null && maxInterstitialAd!!.isReady) {
            maxInterstitialAd!!.showAd()
        } else {
            startActivity(context, intent)
        }
    }
}