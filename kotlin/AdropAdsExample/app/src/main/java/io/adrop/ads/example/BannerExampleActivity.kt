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
    private val PUBLIC_TEST_UNIT_ID_320_80 = "PUBLIC_TEST_UNIT_ID_375_80"
    private val PUBLIC_TEST_UNIT_ID_CAROUSEL = "PUBLIC_TEST_UNIT_ID_CAROUSEL"

    private val INVALID_UNIT_ID = "INVALID_UNIT_ID"

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var bannerContainer: FrameLayout

    private var banner: AdropBanner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_example)
        tvErrorCode = findViewById(R.id.banner_error_code)
        tvErrorDesc = findViewById(R.id.banner_error_code_desc)
        bannerContainer = findViewById(R.id.banner_container)
        findViewById<View>(R.id.load).setOnClickListener { _ -> load(PUBLIC_TEST_UNIT_ID_320_80) }
        findViewById<View>(R.id.load_carousel).setOnClickListener { _ -> load(PUBLIC_TEST_UNIT_ID_CAROUSEL)}
        findViewById<View>(R.id.load_invalid).setOnClickListener { _ -> load(INVALID_UNIT_ID) }
    }

    private fun load(unitId: String) {
        if (banner != null) {
            banner?.destroy()
        }
        banner = AdropBanner(this, unitId).apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(receivedBanner: AdropBanner) {
                    Log.d("adrop", "banner received " + receivedBanner.getUnitId())
                    bannerContainer.removeAllViews()
                    bannerContainer.addView(receivedBanner)
                    tvErrorDesc.text = null
                    tvErrorCode.text = null
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
                }
            }
            load()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        banner?.destroy()
        banner = null
    }
}
