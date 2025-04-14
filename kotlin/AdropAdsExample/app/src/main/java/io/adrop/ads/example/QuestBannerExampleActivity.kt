package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.banner.AdropBanner
import io.adrop.ads.banner.AdropBannerListener
import io.adrop.ads.banner.AdropQuestBanner
import io.adrop.ads.example.helper.ErrorUtils
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.model.AdropQuestBannerFormat

class QuestBannerExampleActivity : AppCompatActivity() {

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var feedContainer: FrameLayout
    lateinit var coverContainer: FrameLayout

    private val TEST_CHANNEL = "timestamp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_banner_example)
        tvErrorCode = findViewById(R.id.quest_banner_error_code)
        tvErrorDesc = findViewById(R.id.quest_banner_error_code_desc)
        feedContainer = findViewById(R.id.quest_banner_container_feed)
        coverContainer = findViewById(R.id.quest_banner_container_cover)

        loadQuestBanners()

        setInvalidQuestBanner()
    }

    private fun loadQuestBanners() {
        AdropQuestBanner(this, TEST_CHANNEL, AdropQuestBannerFormat.FEED).apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(banner: AdropBanner) {
                    Log.d("adrop", "feed quest received")
                    feedContainer.removeAllViews()
                    feedContainer.addView(banner)
                }

                override fun onAdImpression(banner: AdropBanner) {
                    Log.d("adrop", "feed quest impressed ${banner.getUnitId()}")
                }

                override fun onAdClicked(banner: AdropBanner) {
                    Log.d("adrop", "feed quest clicked ${banner.getUnitId()}")
                }

                override fun onAdFailedToReceive(banner: AdropBanner, errorCode: AdropErrorCode) {
                    Log.d("adrop", "feed quest failed to receive, $errorCode")
                }
            }
            load()
        }

        AdropQuestBanner(this, TEST_CHANNEL, AdropQuestBannerFormat.COVER).apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(banner: AdropBanner) {
                    Log.d("adrop", "cover quest received")
                    coverContainer.removeAllViews()
                    coverContainer.addView(banner)
                }

                override fun onAdImpression(banner: AdropBanner) {
                    Log.d("adrop", "cover quest impressed ${banner.getUnitId()}")
                }

                override fun onAdClicked(banner: AdropBanner) {
                    Log.d("adrop", "cover quest clicked ${banner.getUnitId()}")
                }

                override fun onAdFailedToReceive(banner: AdropBanner, errorCode: AdropErrorCode) {
                    Log.d("adrop", "cover quest failed to receive, $errorCode")
                }
            }
            load()
        }
    }

    private fun setInvalidQuestBanner() {
        findViewById<View>(R.id.load_invalid).setOnClickListener {
            tvErrorCode.text = null
            tvErrorDesc.text = null

            AdropQuestBanner(this, "invalid_channel", AdropQuestBannerFormat.COVER).apply {
                listener = object : AdropBannerListener {
                    override fun onAdReceived(banner: AdropBanner) {
                        Log.d("adrop", "invalid quest received")
                    }

                    override fun onAdClicked(banner: AdropBanner) {
                        Log.d("adrop", "invalid quest clicked ${banner.getUnitId()}")
                    }

                    override fun onAdFailedToReceive(banner: AdropBanner, errorCode: AdropErrorCode) {
                        Log.d("adrop", "invalid quest failed to receive, $errorCode")
                        tvErrorCode.text = errorCode.name
                        tvErrorDesc.text = ErrorUtils.descriptionOf(errorCode)
                    }
                }
                load()
            }
        }
    }
}
