package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.banner.AdropBanner
import io.adrop.ads.banner.AdropBannerListener
import io.adrop.ads.example.helper.ErrorUtils
import io.adrop.ads.model.AdropErrorCode

class BannerExampleActivity : AppCompatActivity() {
    private val PUBLIC_TEST_UNIT_ID_320_50 = "PUBLIC_TEST_UNIT_ID_320_50"
    private val PUBLIC_TEST_UNIT_ID_375_80 = "PUBLIC_TEST_UNIT_ID_375_80"
    private val PUBLIC_TEST_UNIT_ID_320_100 = "PUBLIC_TEST_UNIT_ID_320_100"
    private val PUBLIC_TEST_UNIT_ID_CAROUSEL = "PUBLIC_TEST_UNIT_ID_CAROUSEL"

    private val INVALID_UNIT_ID = "INVALID_UNIT_ID"

    private val CONTEXT_ID = ""

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var tvAdInfo: TextView
    lateinit var bannerContainer: FrameLayout

    private val bannerAds = mutableMapOf<String, AdropBanner?>()
    private var currentBanner: AdropBanner? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_example)
        
        tvErrorCode = findViewById(R.id.banner_error_code)
        tvErrorDesc = findViewById(R.id.banner_error_code_desc)
        tvAdInfo = findViewById(R.id.ad_info)
        bannerContainer = findViewById(R.id.banner_container)
        
        setButtons()
        preloadAds()
    }

    private fun setButtons() {
        findViewById<View>(R.id.load_320x50).setOnClickListener { displayPreloadedAd(PUBLIC_TEST_UNIT_ID_320_50) }
        findViewById<View>(R.id.load_375x80).setOnClickListener { displayPreloadedAd(PUBLIC_TEST_UNIT_ID_375_80) }
        findViewById<View>(R.id.load_320x100).setOnClickListener { displayPreloadedAd(PUBLIC_TEST_UNIT_ID_320_100) }
        findViewById<View>(R.id.load_carousel).setOnClickListener { load(PUBLIC_TEST_UNIT_ID_CAROUSEL) }
        findViewById<View>(R.id.load_invalid).setOnClickListener { load(INVALID_UNIT_ID) }
    }

    private fun preloadAds() {
        tvAdInfo.text = "광고 미리 로드 중..."
        
        preloadAd(PUBLIC_TEST_UNIT_ID_320_50, "320x50")
        preloadAd(PUBLIC_TEST_UNIT_ID_375_80, "375x80")
        preloadAd(PUBLIC_TEST_UNIT_ID_320_100, "320x100")
    }

    private fun preloadAd(unitId: String, sizeName: String) {
        val banner = AdropBanner(this, unitId, CONTEXT_ID).apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(receivedBanner: AdropBanner) {
                    Log.d("adrop", "Banner preloaded $sizeName (${receivedBanner.getUnitId()}) size: ${receivedBanner.creativeSize.width}x${receivedBanner.creativeSize.height}")
                    bannerAds[unitId] = receivedBanner
                    updateAdInfo()
                }

                override fun onAdImpression(banner: AdropBanner) {
                    Log.d("adrop", "Banner impressed $sizeName (${banner.getUnitId()})")
                }

                override fun onAdClicked(clickedBanner: AdropBanner) {
                    Log.d("adrop", "Banner clicked $sizeName (${clickedBanner.getUnitId()})")
                }

                override fun onAdFailedToReceive(failedBanner: AdropBanner, errorCode: AdropErrorCode) {
                    Log.d("adrop", "Banner failed to preload $sizeName (${failedBanner.getUnitId()}), $errorCode")
                    updateAdInfo()
                }
            }
        }
        banner.load()
    }
    
    private fun displayPreloadedAd(unitId: String) {
        val preloadedBanner = bannerAds[unitId]
        if (preloadedBanner != null) {
            clearCurrentAd()
            currentBanner = preloadedBanner
            bannerContainer.removeAllViews()
            bannerContainer.addView(preloadedBanner)
            tvErrorCode.text = null
            tvErrorDesc.text = null
            updateAdInfo()
        } else {
            tvErrorCode.text = "NO_AD_AVAILABLE"
            tvErrorDesc.text = "미리 로드된 광고가 없습니다. 잠시 후 다시 시도해주세요."
        }
    }

    private fun updateAdInfo() {
        val loadedCount = bannerAds.values.count { it != null }
        val totalCount = bannerAds.size
        val currentInfo = currentBanner?.let { 
            "현재: ${it.creativeSize.width}x${it.creativeSize.height}" 
        } ?: "현재 표시된 광고 없음"
        
        tvAdInfo.text = "미리 로드된 광고: $loadedCount/$totalCount\n$currentInfo"
    }
    
    private fun clearCurrentAd() {
        if (currentBanner != null && bannerContainer.childCount > 0) {
            bannerContainer.removeAllViews()
        }
    }

    private fun load(unitId: String) {
        clearCurrentAd()
        currentBanner = AdropBanner(this, unitId, CONTEXT_ID).apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(receivedBanner: AdropBanner) {
                    Log.d("adrop", "banner received " + receivedBanner.getUnitId() + " size: " + receivedBanner.creativeSize.width + "x" + receivedBanner.creativeSize.height)
                    bannerContainer.removeAllViews()
                    bannerContainer.addView(receivedBanner)
                    tvErrorDesc.text = null
                    tvErrorCode.text = null
                    currentBanner = receivedBanner
                    updateAdInfo()
                }

                override fun onAdImpression(banner: AdropBanner) {
                    Log.d("adrop", "banner impressed ${banner.getUnitId()}")
                }

                override fun onAdClicked(clickedBanner: AdropBanner) {
                    Log.d("adrop", "banner clicked ${clickedBanner.getUnitId()}")
                }

                override fun onAdFailedToReceive(failedBanner: AdropBanner, errorCode: AdropErrorCode) {
                    Log.d("adrop", "banner failed to receive ${failedBanner.getUnitId()}, $errorCode")
                    tvErrorCode.text = errorCode.name
                    tvErrorDesc.text = ErrorUtils.descriptionOf(errorCode)
                    updateAdInfo()
                }
            }
            load()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAds.values.forEach { it?.destroy() }
        bannerAds.clear()
        currentBanner?.destroy()
        currentBanner = null
    }
}