package io.adrop.ads.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.Adrop.initialize

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.initialize).setOnClickListener { initialize(application, false) }
        findViewById<View>(R.id.banner_example).setOnClickListener { start(BannerExampleActivity::class.java) }
        findViewById<View>(R.id.interstitial_example).setOnClickListener { start(InterstitialExampleActivity::class.java) }
        findViewById<View>(R.id.rewarded_example).setOnClickListener { start(RewardedAdExampleActivity::class.java) }
    }

    private fun start(cls: Class<*>) {
        val intent = Intent(this, cls)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
