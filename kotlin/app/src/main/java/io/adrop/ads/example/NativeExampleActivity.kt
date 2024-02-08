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
    private val nativeAds: ArrayList<AdropNativeAd>
        get() {
            val list = arrayListOf<AdropNativeAd>()

            for (ad in AdLoader.nativeAds) {
                if (ad.isLoaded) {
                    list.add(ad)
                }
            }
            return list
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_example)

        Log.d("adrop", "nativeAds $nativeAds")

        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        recyclerView = findViewById(R.id.list)
        recyclerView.run {
            layoutManager =
                LinearLayoutManager(this@NativeExampleActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(decoration)
            adapter = PostAdapter(nativeAds)
        }
    }

    override fun onDestroy() {
        for(ad in nativeAds) {
            ad.destroy()
            AdLoader.nativeAds.remove(ad)
        }

        nativeAds.clear()

        super.onDestroy()
    }
}
