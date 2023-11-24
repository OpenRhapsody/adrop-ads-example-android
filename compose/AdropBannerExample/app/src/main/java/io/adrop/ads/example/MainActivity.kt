package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.adrop.ads.Adrop
import io.adrop.ads.banner.AdropBanner
import io.adrop.ads.banner.AdropBannerListener
import io.adrop.ads.example.ui.theme.AdropBannerExampleTheme
import io.adrop.ads.model.AdropErrorCode

class MainActivity : ComponentActivity() {
    private lateinit var banner: AdropBanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Adrop.initialize(application, false)

        banner = AdropBanner(this, "PUBLIC_TEST_UNIT_ID_320_50")
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

        setContent {
            AdropBannerExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { banner.load() }) {
                            Text(text = "request ad")
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        AndroidView(factory = {
                            banner
                        }, modifier = Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}
