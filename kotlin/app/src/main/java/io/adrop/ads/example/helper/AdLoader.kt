package io.adrop.ads.example.helper

import android.content.Context
import android.util.Log
import io.adrop.ads.banner.AdropBanner
import io.adrop.ads.banner.AdropBannerListener
import io.adrop.ads.interstitial.AdropInterstitialAd
import io.adrop.ads.interstitial.AdropInterstitialAdListener
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.nativeAd.AdropNativeAd
import io.adrop.ads.nativeAd.AdropNativeAdListener
import io.adrop.ads.rewardedAd.AdropRewardedAd
import io.adrop.ads.rewardedAd.AdropRewardedAdListener
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object AdLoader {
    private val BANNER_UNIT_ID = "PUBLIC_TEST_UNIT_ID_320_100"
    private val INTERSTITIAL_UNIT_ID = "PUBLIC_TEST_UNIT_ID_INTERSTITIAL"
    private val REWARDED_UNIT_ID = "PUBLIC_TEST_UNIT_ID_REWARDED"
    private val NATIVE_UNIT_ID = "PUBLIC_TEST_UNIT_ID_NATIVE"
    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)

    val banners = arrayListOf<AdropBanner>()
    val interstitialAds = arrayListOf<AdropInterstitialAd>()
    val rewardedAds = arrayListOf<AdropRewardedAd>()
    val nativeAds = arrayListOf<AdropNativeAd>()

    fun fetchBanner(context: Context) {
        val banner = AdropBanner(context, BANNER_UNIT_ID)
        banner.listener = object : AdropBannerListener {
            override fun onAdClicked(banner: AdropBanner) {
                Log.d("adrop", "banner clicked")
            }

            override fun onAdFailedToReceive(banner: AdropBanner, errorCode: AdropErrorCode) {
                Log.d("adrop", "banner failed to receive, $errorCode")
            }

            override fun onAdReceived(banner: AdropBanner) {
                Log.d("adrop", "banner received")
                banners.add(banner)
            }
        }
        banner.load()
    }

    fun fetchInterstitial(context: Context) {
        val interstitialAd = AdropInterstitialAd(context, INTERSTITIAL_UNIT_ID)
        interstitialAd.interstitialAdListener = object : AdropInterstitialAdListener {
            override fun onAdFailedToReceive(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
                Log.d("adrop", "interstitial ad failed to receive, $errorCode")
            }

            override fun onAdReceived(ad: AdropInterstitialAd) {
                Log.d("adrop", "interstitial ad failed to receive")
                interstitialAds.add(ad)
            }

        }
        interstitialAd.load()
    }

    fun fetchRewarded(context: Context) {
        val rewardedAd = AdropRewardedAd(context, REWARDED_UNIT_ID)
        rewardedAd.rewardedAdListener = object : AdropRewardedAdListener {
            override fun onAdFailedToReceive(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                Log.d("adrop", "rewarded ad failed to receive, $errorCode")
            }

            override fun onAdReceived(ad: AdropRewardedAd) {
                Log.d("adrop", "rewarded ad received")
                rewardedAds.add(ad)
            }

        }

    }

    fun fetchNativeAd(context: Context) {
        val nativeAd = AdropNativeAd(context, NATIVE_UNIT_ID)
        nativeAd.listener = object : AdropNativeAdListener {
            override fun onAdReceived(ad: AdropNativeAd) {
                Log.d("adrop", "native ad received")
                nativeAds.add(ad)
            }

            override fun onAdClick(ad: AdropNativeAd) {
                Log.d("adrop", "native ad clicked")
            }

            override fun onAdFailedToReceive(ad: AdropNativeAd, errorCode: AdropErrorCode) {
                Log.d("adrop", "native ad failed to receive, $errorCode")
            }
        }

        executorService.execute { nativeAd.load() }
    }

}
