package com.veon.demoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.veon.demoapp.databinding.ActivityMainBinding
import org.prebid.mobile.AdSize
import org.prebid.mobile.Host
import org.prebid.mobile.PrebidMobile
import org.prebid.mobile.api.data.InitializationStatus
import org.prebid.mobile.api.exceptions.AdException
import org.prebid.mobile.api.rendering.BannerView
import org.prebid.mobile.api.rendering.listeners.BannerViewListener
import org.prebid.mobile.eventhandlers.AuctionBannerEventHandler
import org.prebid.mobile.eventhandlers.AuctionListener


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
        findViewById<Button>(R.id.recyclerbutton).setOnClickListener {
            startActivity(Intent(this, RecyclerActivity::class.java))
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