package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.Adrop
import io.adrop.ads.banner.AdropBanner
import io.adrop.ads.banner.AdropBannerListener
import io.adrop.ads.model.AdropErrorCode

class MainActivity : AppCompatActivity() {

    private lateinit var banner: AdropBanner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Adrop.initialize(application, false)

        banner = findViewById(R.id.adrop_banner_view)
        banner.listener = object : AdropBannerListener {
            override fun onAdReceived(banner: AdropBanner) {
                Log.d("Adrop", "${banner.getUnitId()}, onAdReceived")
            }

            override fun onAdClicked(banner: AdropBanner) {
                Log.d("Adrop", "${banner.getUnitId()}, onAdClicked")
            }

            override fun onAdFailedToReceive(banner: AdropBanner, error: AdropErrorCode) {
                Log.d("Adrop", "${banner.getUnitId()}, onAdFailedToReceive $error")
            }
        }

        val button = findViewById<Button>(R.id.display_button)
        button.setOnClickListener { banner.load() }
    }

    override fun onDestroy() {
        super.onDestroy()
        banner.destroy()
    }
}
