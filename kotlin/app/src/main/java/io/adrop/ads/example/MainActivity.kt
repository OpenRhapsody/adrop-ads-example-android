package io.adrop.ads.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.Adrop
import io.adrop.ads.example.helper.AdLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.initialize).setOnClickListener { Adrop.initialize(application, false) }
        findViewById<View>(R.id.banner_example).setOnClickListener { start(BannerExampleActivity::class.java) }
        findViewById<View>(R.id.interstitial_example).setOnClickListener { start(InterstitialExampleActivity::class.java) }
        findViewById<View>(R.id.rewarded_example).setOnClickListener { start(RewardedAdExampleActivity::class.java) }
        findViewById<View>(R.id.native_load).setOnClickListener { AdLoader.fetchNativeAd(this) }
        findViewById<View>(R.id.native_example).setOnClickListener {
            for (nativeAd in AdLoader.nativeAds) {
                if (nativeAd.isLoaded) {
                    start(NativeExampleActivity::class.java)
                    return@setOnClickListener
                }
            }

            Toast.makeText(this, "There is no native ads", Toast.LENGTH_SHORT).show()
        }
    }

    private fun start(cls: Class<*>) {
        val intent = Intent(this, cls)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
