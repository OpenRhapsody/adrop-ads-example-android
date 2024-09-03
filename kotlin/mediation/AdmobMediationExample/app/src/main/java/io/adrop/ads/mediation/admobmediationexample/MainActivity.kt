package io.adrop.ads.mediation.admobmediationexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import io.adrop.ads.Adrop
import io.adrop.ads.mediation.admob.AdropAdRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()

        findViewById<Button>(R.id.banner_load_ad).setOnClickListener {
            val view = load()

            val container = findViewById<FrameLayout>(R.id.banner_container)
            container.removeAllViews()
            container.addView(view)
        }
    }

    private fun initialize() {
        Adrop.initialize(application, false)
        MobileAds.initialize(this) {
            Log.d("adrop", "mobile ads initialize $it")

            val statusMap = it.adapterStatusMap
            for (adapterClass in statusMap.keys) {
                val status = statusMap[adapterClass]
                Log.d(
                    "AdmobMediationExample", String.format(
                        "Adapter name: %s, Description: %s, Latency: %d",
                        adapterClass, status!!.description, status.latency
                    )
                )
            }
        }
    }

    private fun load(): AdView {
        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = "ca-app-pub-2926914265361170/2941298347"
        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d("adrop", "admob failed to load $p0")
            }

            override fun onAdLoaded() {
                Log.d("adrop", "admob received ad")
            }
        }

        val request = AdropAdRequest.Builder()
            .setUnitId("PUBLIC_TEST_UNIT_ID_375_80").build()

        adView.loadAd(request)
        return adView
    }
}