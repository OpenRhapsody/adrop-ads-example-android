package com.example.splashadviewexample

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import io.adrop.ads.Adrop
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.splash.AdropSplashAdView
import io.adrop.ads.splash.AdropSplashAdViewListener
import java.util.Timer
import java.util.TimerTask

/**
 * Example of implementing a custom splash screen with Adrop Splash Ad.
 * This approach gives you more control over the splash screen behavior
 * compared to declaring it in AndroidManifest.xml.
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var adFrame: FrameLayout
    private lateinit var splashAd: AdropSplashAdView
    private val displayDuration = 3_000L // Duration to display the splash ad in milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize Adrop SDK (set to true for production)
        Adrop.initialize(application, false)

        adFrame = findViewById(R.id.ad_area)

        // Create and configure the splash ad view
        splashAd = AdropSplashAdView(this, "PUBLIC_TEST_UNIT_ID_SPLASH")
        adFrame.addView(splashAd)
        splashAd.displayDuration = displayDuration

        splashAd.listener = object : AdropSplashAdViewListener {
            // Called when an ad is successfully loaded
            override fun onAdReceived(ad: AdropSplashAdView) {
                Log.d("splashAdView", "SplashActivity onAdReceived ${ad.unitId} ${ad.creativeId}")
            }

            // Called when an ad fails to load
            override fun onAdFailedToReceive(ad: AdropSplashAdView, errorCode: AdropErrorCode) {
                Log.d("splashAdView", "SplashActivity onAdFailedToReceive ${ad.unitId}, $errorCode")
            }

            // Called when an ad impression is recorded
            override fun onAdImpression(ad: AdropSplashAdView) {
                Log.d("splashAdView", "SplashActivity onAdImpression ${ad.unitId}")
            }

            // Called when the splash ad is closed (either by user or after duration)
            // impressed: true if the ad was displayed, false otherwise
            override fun onAdClose(ad: AdropSplashAdView, impressed: Boolean) {
                Log.d("splashAdView", "SplashActivity onAdClose ${ad.unitId} $impressed")
                startMainActivity(impressed)
            }
        }
        splashAd.load()
    }

    /**
     * Navigates to the main activity.
     * If the ad was impressed, navigates immediately.
     * Otherwise, waits for displayDuration before navigating.
     */
    private fun startMainActivity(impressed: Boolean) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, if (impressed) 0 else displayDuration)
    }
}
