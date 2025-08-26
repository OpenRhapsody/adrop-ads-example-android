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

class BannerVideoExampleActivity : AppCompatActivity() {
    private val PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_16_9 = "PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_16_9"
    private val PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_9_16 = "PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_9_16"

    private val CONTEXT_ID = ""

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var tvAdInfo: TextView
    lateinit var bannerContainer: FrameLayout

    private val bannerAds = mutableMapOf<String, AdropBanner?>()
    private var currentBanner: AdropBanner? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_video_example)
        
        tvErrorCode = findViewById(R.id.banner_error_code)
        tvErrorDesc = findViewById(R.id.banner_error_code_desc)
        tvAdInfo = findViewById(R.id.ad_info)
        bannerContainer = findViewById(R.id.banner_container)
        
        setButtons()
        preloadVideoAds()
    }

    private fun setButtons() {
        findViewById<View>(R.id.load_video_16_9).setOnClickListener { displayPreloadedAd(PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_16_9) }
        findViewById<View>(R.id.load_video_9_16).setOnClickListener { displayPreloadedAd(PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_9_16) }
    }

    private fun preloadVideoAds() {
        tvAdInfo.text = "동영상 광고 미리 로드 중..."
        
        preloadVideoAd(PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_16_9, "16:9 비율")
        preloadVideoAd(PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_9_16, "9:16 비율")
    }

    private fun preloadVideoAd(unitId: String, aspectRatio: String) {
        val banner = AdropBanner(this, unitId, CONTEXT_ID).apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(receivedBanner: AdropBanner) {
                    Log.d("adrop", "Video banner preloaded $aspectRatio (${receivedBanner.getUnitId()}) size: ${receivedBanner.creativeSize.width}x${receivedBanner.creativeSize.height}")
                    bannerAds[unitId] = receivedBanner
                    updateAdInfo()
                }

                override fun onAdImpression(banner: AdropBanner) {
                    Log.d("adrop", "Video banner impressed $aspectRatio (${banner.getUnitId()})")
                }

                override fun onAdClicked(clickedBanner: AdropBanner) {
                    Log.d("adrop", "Video banner clicked $aspectRatio (${clickedBanner.getUnitId()})")
                }

                override fun onAdFailedToReceive(failedBanner: AdropBanner, errorCode: AdropErrorCode) {
                    Log.d("adrop", "Video banner failed to preload $aspectRatio (${failedBanner.getUnitId()}), $errorCode")
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
            tvErrorDesc.text = "미리 로드된 동영상 광고가 없습니다. 잠시 후 다시 시도해주세요."
        }
    }

    private fun updateAdInfo() {
        val loadedCount = bannerAds.values.count { it != null }
        val totalCount = bannerAds.size
        val currentInfo = currentBanner?.let { 
            val aspectRatio = if (it.creativeSize.width > it.creativeSize.height) "16:9" else "9:16"
            "현재: ${it.creativeSize.width}x${it.creativeSize.height} ($aspectRatio 동영상)" 
        } ?: "현재 표시된 동영상 광고 없음"
        
        tvAdInfo.text = "미리 로드된 동영상 광고: $loadedCount/$totalCount\n$currentInfo"
    }
    
    private fun clearCurrentAd() {
        if (currentBanner != null && bannerContainer.childCount > 0) {
            bannerContainer.removeAllViews()
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