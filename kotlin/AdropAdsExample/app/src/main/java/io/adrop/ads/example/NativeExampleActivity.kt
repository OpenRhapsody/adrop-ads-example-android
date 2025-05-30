package io.adrop.ads.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.adrop.ads.example.adapter.PostAdapter
import io.adrop.ads.example.helper.ErrorUtils
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.nativeAd.AdropNativeAd
import io.adrop.ads.nativeAd.AdropNativeAdListener

class NativeExampleActivity : AppCompatActivity() {
    private val NATIVE_UNIT_ID = "PUBLIC_TEST_UNIT_ID_NATIVE"
    private val CONTEXT_ID = "" // Optional context ID for the banner, can be left empty

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private var nativeAd: AdropNativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_example)

        progressBar = findViewById(R.id.progress_bar)
        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        recyclerView = findViewById(R.id.list)
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(this@NativeExampleActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(decoration)
        }

        load()
    }

    private fun load() {
        nativeAd = AdropNativeAd(this, NATIVE_UNIT_ID, CONTEXT_ID)
        nativeAd?.listener = object : AdropNativeAdListener {
            override fun onAdReceived(ad: AdropNativeAd) {
                Log.d("adrop", "native ad received")
                recyclerView.adapter = PostAdapter(ad)
                progressBar.visibility = View.GONE
            }

            override fun onAdClick(ad: AdropNativeAd) {
                Log.d("adrop", "native ad clicked")
            }

            override fun onAdFailedToReceive(ad: AdropNativeAd, errorCode: AdropErrorCode) {
                Log.d("adrop", "native ad failed to receive, $errorCode")
                Toast.makeText(this@NativeExampleActivity, ErrorUtils.descriptionOf(errorCode), Toast.LENGTH_SHORT).show()
            }

            override fun onAdImpression(ad: AdropNativeAd) {
                Log.d("adrop", "native ad impression")
            }
        }
        nativeAd?.load()
    }

    override fun onDestroy() {
        nativeAd?.destroy()
        super.onDestroy()
    }
}
