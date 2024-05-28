package com.veon.demoapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.veon.demoapp.databinding.ActivityMainBinding
import org.prebid.mobile.AdSize
import org.prebid.mobile.BannerAdUnit
import org.prebid.mobile.BannerParameters
import org.prebid.mobile.Host
import org.prebid.mobile.PrebidMobile
import org.prebid.mobile.Signals
import org.prebid.mobile.addendum.AdViewUtils
import org.prebid.mobile.addendum.PbFindSizeError
import org.prebid.mobile.api.data.InitializationStatus
import org.prebid.mobile.api.exceptions.AdException
import org.prebid.mobile.api.rendering.BannerView
import org.prebid.mobile.api.rendering.listeners.BannerViewListener
import org.prebid.mobile.eventhandlers.AuctionBannerEventHandler
import org.prebid.mobile.eventhandlers.AuctionListener

//import org.prebid.mobile.eventhandlers.AuctionBannerEventHandler
//import org.prebid.mobile.eventhandlers.AuctionListener


class MainActivity : AppCompatActivity() {

    var adButton: Button? = null
    var SdkTAG = "veon"
    var SdkOK = false

    protected val adWrapperView: ViewGroup
        get() = binding.banner32050

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adButton = findViewById(R.id.button)
        adButton!!.setOnClickListener {
            if (SdkOK) {
                Log.d(SdkTAG, "load ad")
                showAds320x50()
            } else {
                Log.d(SdkTAG, "sdk not loaded")
            }
        }

        initSDK()
    }

    // load placement mybl_android_universal_banner_320x50
    private fun showAds320x50() {
        // listener for wrapping GAM rendering
        val eventHandler = AuctionBannerEventHandler(
            this,
            "/",
            0.1F,
            AdSize(320, 50)
        )

        // lister for understand where from demand
        eventHandler.setAuctionEventListener(object : AuctionListener {
            override fun onPRBWin(price: Float) {
                Toast.makeText(applicationContext, "onPRBWin", Toast.LENGTH_LONG).show()
            }
            override fun onGAMWin(view: View?) {
                Toast.makeText(applicationContext, "onGAMWin", Toast.LENGTH_LONG).show()
            }
        })

        // configure banner placement
        val adUnit = BannerView(this, "izi_kz_android_universal_320x50", eventHandler)

        // lister for custom tracking or custom display creative
        adUnit.setBannerListener(object : BannerViewListener {
            override fun onAdUrlClicked(url: String?) {
                Toast.makeText(applicationContext, url, Toast.LENGTH_LONG).show()
            }
            override fun onAdLoaded(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdLoaded", Toast.LENGTH_LONG).show()
            }
            override fun onAdDisplayed(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdDisplayed", Toast.LENGTH_LONG).show()
            }

            override fun onAdFailed(bannerView: BannerView?, exception: AdException?) {
                Toast.makeText(applicationContext, "onAdFailed", Toast.LENGTH_LONG).show()
            }

            override fun onAdClicked(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdClicked", Toast.LENGTH_LONG).show()
            }

            override fun onAdClosed(bannerView: BannerView?) {
                Toast.makeText(applicationContext, "onAdClosed", Toast.LENGTH_LONG).show()
            }
        })

        val AdSlot = binding.banner32050
        AdSlot.addView(adUnit)
        adUnit.loadAd()
    }
//
//    private fun createGAMListener(adView: AdManagerAdView): AdListener {
//        return object : AdListener() {
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//
//                // 6. Resize ad view if needed
//                AdViewUtils.findPrebidCreativeSize(adView, object : AdViewUtils.PbFindSizeListener {
//                    override fun success(width: Int, height: Int) {
//                        adView.setAdSizes(com.google.android.gms.ads.AdSize(width, height))
//                    }
//
//                    override fun failure(error: PbFindSizeError) {}
//                })
//            }
//        }
//    }
//    private fun OldVersionWithGamAds300x250() {
//
//        val adUnit = BannerAdUnit(
//            "izi_kz_android_universal_320x50",
//            320,
//            50,
//        )
//        adUnit.setAutoRefreshInterval(30)
//
//        val parameters = BannerParameters()
//        parameters.api = listOf(Signals.Api.MRAID_3, Signals.Api.OMID_1)
//        adUnit.bannerParameters = parameters
//
//        // GAM LOADER
//        val adView = AdManagerAdView(this)
//        adView.adUnitId = "/"
//        adView.setAdSizes(com.google.android.gms.ads.AdSize(320, 50))
//        adView.adListener = createGAMListener(adView)
//
//        val AdSlot = binding.banner32050
//        adWrapperView.addView(adView)
//
//        // PREBID LOADER
//        val request = AdManagerAdRequest.Builder().build()
//        adUnit.fetchDemand(request) {
//            adView.loadAd(request)
//        }
//
//        val AdSlot = binding.banner300250
//        val adUnit = BannerAdUnit("mybl_android_universal_banner_300x250", 300, 250)
//        val parameters = BannerParameters()
//        parameters.api = listOf(Signals.Api.MRAID_3, Signals.Api.OMID_1)
//        adUnit.bannerParameters = parameters
//
//
//
//        val adView = AdManagerAdView(this)
//        adView.adUnitId = "/"
//        adView.setAdSizes(com.google.android.gms.ads.AdSize(300, 250))
//        adView.adListener = object : AdListener() {
//            override fun onAdLoaded() {
//                super.onAdLoaded()
//                AdViewUtils.findPrebidCreativeSize(adView, object : AdViewUtils.PbFindSizeListener {
//                    override fun success(width: Int, height: Int) {
//                        adView.setAdSizes(com.google.android.gms.ads.AdSize(width, height))
//                    }
//                    override fun failure(error: PbFindSizeError) {
//                    }
//                })
//            }
//        }
//        AdSlot.addView(adView)
//
//        val request = AdManagerAdRequest.Builder().build()
//        adUnit.fetchDemand(request) {
//            adView.loadAd(request)
//        }

//    }

    // load placement mybl_android_universal_banner_300x250
//    private fun showAds300x250() {
//
//        // listener for wrapping GAM rendering
//        val eventHandler = AuctionBannerEventHandler(
//            this,
//            "/",
//            0.1F,
//            AdSize(300, 250)
//        )
//
//        // lister for understand where from demand
//        eventHandler.setAuctionEventListener(object : AuctionListener {
//            override fun onPRBWin(price: Float) {
//                Toast.makeText(applicationContext, "onPRBWin", Toast.LENGTH_LONG).show()
//            }
//            override fun onGAMWin(view: View?) {
//                Toast.makeText(applicationContext, "onGAMWin", Toast.LENGTH_LONG).show()
//            }
//        })
//
//        // configure banner placement
//        val adUnit = BannerView(this, "mybl_android_universal_banner_300x250", eventHandler)
//
//        // lister for custom tracking or custom display creative
//        adUnit.setBannerListener(object : BannerViewListener {
//            override fun onAdUrlClicked(url: String?) {
//                Toast.makeText(applicationContext, url, Toast.LENGTH_LONG).show()
//            }
//            override fun onAdLoaded(bannerView: BannerView?) {
//                Toast.makeText(applicationContext, "onAdLoaded", Toast.LENGTH_LONG).show()
//            }
//            override fun onAdDisplayed(bannerView: BannerView?) {
//                Toast.makeText(applicationContext, "onAdDisplayed", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onAdFailed(bannerView: BannerView?, exception: AdException?) {
//                Toast.makeText(applicationContext, "onAdFailed", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onAdClicked(bannerView: BannerView?) {
//                Toast.makeText(applicationContext, "onAdClicked", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onAdClosed(bannerView: BannerView?) {
//                Toast.makeText(applicationContext, "onAdClosed", Toast.LENGTH_LONG).show()
//            }
//        })
//
//        val AdSlot = binding.banner300250
//        AdSlot.addView(adUnit)
//        adUnit.loadAd()
//    }

    // Initialization VEON SDK
    private fun initSDK() {
        PrebidMobile.setPrebidServerAccountId("com.veon.izi")
        PrebidMobile.setPrebidServerHost(Host.createCustomHost("https://prebid.kazdsp.com/openrtb2/auction"))
        PrebidMobile.setCustomStatusEndpoint("https://prebid.kazdsp.com/status")
        PrebidMobile.setTimeoutMillis(100000)
        PrebidMobile.setShareGeoLocation(true)
        PrebidMobile.useExternalBrowser = true

        PrebidMobile.initializeSdk(applicationContext) { status ->
            if (status == InitializationStatus.SUCCEEDED) {
                SdkOK = true
                Log.d(SdkTAG, "initialized successfully!")
            } else {
                SdkOK = false
                Log.e(SdkTAG, "initialization error: $status\n${status.description}")
            }
        }
    }
}