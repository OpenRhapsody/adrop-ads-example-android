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

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var adFrame: FrameLayout
    private lateinit var splashAd: AdropSplashAdView
    private val displayDuration = 3_000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Adrop.initialize(application, false)

        adFrame = findViewById(R.id.ad_area)

        splashAd = AdropSplashAdView(this, "PUBLIC_TEST_UNIT_ID_SPLASH")
        adFrame.addView(splashAd)
        splashAd.displayDuration = displayDuration

        splashAd.listener = object : AdropSplashAdViewListener {
            override fun onAdReceived(ad: AdropSplashAdView) {
                Log.d("splashAdView", "SplashActivity onAdReceived ${ad.unitId} ${ad.creativeId}")
            }

            override fun onAdFailedToReceive(ad: AdropSplashAdView, errorCode: AdropErrorCode) {
                Log.d("splashAdView", "SplashActivity onAdFailedToReceive ${ad.unitId}, $errorCode")
            }

            override fun onAdImpression(ad: AdropSplashAdView) {
                Log.d("splashAdView", "SplashActivity onAdImpression ${ad.unitId}")
            }

            override fun onAdClose(ad: AdropSplashAdView, impressed: Boolean) {
                Log.d("splashAdView", "SplashActivity onAdClose ${ad.unitId} $impressed")
                startMainActivity(impressed)
            }
        }
        splashAd.load()
    }

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