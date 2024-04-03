package io.adrop.ads.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.Adrop

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Adrop.initialize(application, false, arrayOf())

        setButtons()
    }

    private fun setButtons() {
        findViewById<View>(R.id.banner_example).setOnClickListener { start(BannerExampleActivity::class.java) }
        findViewById<View>(R.id.interstitial_example).setOnClickListener { start(InterstitialExampleActivity::class.java) }
        findViewById<View>(R.id.rewarded_example).setOnClickListener { start(RewardedAdExampleActivity::class.java) }
        findViewById<View>(R.id.native_example).setOnClickListener {start(NativeExampleActivity::class.java) }
        findViewById<View>(R.id.property_example).setOnClickListener {start(PropertyActivity::class.java) }
    }

    private fun start(cls: Class<*>) {
        val intent = Intent(this, cls)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
