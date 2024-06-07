package com.veon.demoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.prebid.mobile.AdSize
import org.prebid.mobile.api.exceptions.AdException
import org.prebid.mobile.api.rendering.BannerView
import org.prebid.mobile.api.rendering.listeners.BannerViewListener
import org.prebid.mobile.eventhandlers.AuctionBannerEventHandler
import org.prebid.mobile.eventhandlers.AuctionListener


class SampleRecyclerAdapter() : RecyclerView.Adapter<SampleViewHolder>() {
    var itemsCount = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.ad_item_320_50, parent, false)

        return SampleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(position)
    }

}

class SampleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(position: Int) {
        if (position % 20 == 0) {
            val applicationContext = view.context.applicationContext
            // listener for wrapping GAM rendering
            val eventHandler = AuctionBannerEventHandler(
                view.context,
                "/23081467975,23148104821/izi_kz_android/izi_kz_android_universal_320x50",
                0.1F,
                AdSize(320, 50)
            )

            // lister for understand where from demand
            eventHandler.setAuctionEventListener(object : AuctionListener {
                override fun onPRBWin(price: Float) {
                    Toast.makeText(view.context, "onPRBWin", Toast.LENGTH_LONG).show()
                }

                override fun onGAMWin(view: View?) {
                    Toast.makeText(applicationContext, "onGAMWin", Toast.LENGTH_LONG).show()
                }
            })

            // configure banner placement
            val adUnit = BannerView(view.context, "izi_kz_android_universal_320x50", eventHandler)

            // lister for custom tracking or custom display creative
            adUnit.setBannerListener(object : BannerViewListener {
                override fun onAdUrlClicked(url: String?) {
                    Toast.makeText(applicationContext, url, Toast.LENGTH_LONG).show()
                }

                override fun onAdLoaded(bannerView: BannerView?) {
                    Toast.makeText(applicationContext, "onAdLoaded", Toast.LENGTH_LONG).show()
                }

                override fun onAdDisplayed(bannerView: BannerView?) {
                    Toast.makeText(applicationContext, "onAdDisplayed", Toast.LENGTH_LONG)
                        .show()
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

            val AdSlot = view.findViewById<FrameLayout>(R.id.banner_320_50)
            AdSlot.addView(adUnit)
            adUnit.loadAd()
            AdSlot.setBackgroundColor(view.context.resources.getColor(android.R.color.holo_green_dark))
        } else {
            view.findViewById<FrameLayout>(R.id.banner_320_50).removeAllViews()
            view.findViewById<FrameLayout>(R.id.banner_320_50)
                .setBackgroundColor(view.context.resources.getColor(android.R.color.transparent))
        }
    }


}