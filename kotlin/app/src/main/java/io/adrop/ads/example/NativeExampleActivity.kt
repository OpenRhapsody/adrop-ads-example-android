package io.adrop.ads.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.adrop.ads.example.adapter.PostAdapter
import io.adrop.ads.example.helper.AdLoader
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.nativeAd.AdropNativeAd
import io.adrop.ads.nativeAd.AdropNativeAdListener

class NativeExampleActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val nativeAd: AdropNativeAd?
        get() {
            for (ad in AdLoader.nativeAds) {
                if (ad.isLoaded) {
                    return ad
                }
            }
            return null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_example)

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        recyclerView = findViewById(R.id.list)
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(this@NativeExampleActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(decoration)
            nativeAd?.let { adapter = PostAdapter(it) }
        }
    }

    override fun onDestroy() {
        nativeAd?.let {
            it.destroy()
            AdLoader.nativeAds.remove(it)
        }

        super.onDestroy()
    }
}
