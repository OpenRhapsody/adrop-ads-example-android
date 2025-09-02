package io.adrop.ads.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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
    private val NATIVE_VIDEO_16_9_UNIT_ID = "PUBLIC_TEST_UNIT_ID_NATIVE_VIDEO_16_9"
    private val NATIVE_VIDEO_9_16_UNIT_ID = "PUBLIC_TEST_UNIT_ID_NATIVE_VIDEO_9_16"
    private val CONTEXT_ID = ""

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvAdInfo: TextView
    
    private var currentNativeAd: AdropNativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_example)

        progressBar = findViewById(R.id.progress_bar)
        tvAdInfo = findViewById(R.id.ad_info)
        val decoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        recyclerView = findViewById(R.id.list)
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@NativeExampleActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(decoration)
        }

        setButtons()
        tvAdInfo.text = "네이티브 광고 버튼을 눌러 광고를 로드하고 표시합니다."
    }

    private fun setButtons() {
        findViewById<View>(R.id.load_native).setOnClickListener { loadAndDisplayAd(NATIVE_UNIT_ID, "일반 네이티브") }
        findViewById<View>(R.id.load_native_video_16_9).setOnClickListener { loadAndDisplayAd(NATIVE_VIDEO_16_9_UNIT_ID, "동영상 16:9") }
        findViewById<View>(R.id.load_native_video_9_16).setOnClickListener { loadAndDisplayAd(NATIVE_VIDEO_9_16_UNIT_ID, "동영상 9:16") }
        findViewById<View>(R.id.reset_native).setOnClickListener { resetNativeAd() }
    }

    private fun loadAndDisplayAd(unitId: String, adType: String) {
        Log.d("adrop", "Starting to load $adType with unitId: $unitId")
        tvAdInfo.text = "$adType 광고 로드 중..."
        progressBar.visibility = View.VISIBLE
        
        // Clear previous ad
        currentNativeAd?.destroy()
        currentNativeAd = null
        recyclerView.adapter = null
        
        val nativeAd = AdropNativeAd(this, unitId, CONTEXT_ID).apply {
            listener = object : AdropNativeAdListener {
                override fun onAdReceived(ad: AdropNativeAd) {
                    Log.d("adrop", "$adType native ad received successfully: ${ad.unitId}")
                    val isVideoAd = unitId.contains("VIDEO")
                    Log.d("adrop", "Ad type: $adType, isVideoAd: $isVideoAd")
                    
                    currentNativeAd = ad
                    val adapter = PostAdapter(ad)
                    recyclerView.adapter = adapter
                    progressBar.visibility = View.GONE
                    tvAdInfo.text = "$adType 네이티브 광고 로드 완료 - 표시 중${if (isVideoAd) " (동영상 포함)" else ""}"
                    
                    Toast.makeText(this@NativeExampleActivity, "$adType 광고가 성공적으로 로드되었습니다!", Toast.LENGTH_SHORT).show()
                }

                override fun onAdClick(ad: AdropNativeAd) {
                    Log.d("adrop", "$adType native ad clicked: ${ad.unitId}")
                }

                override fun onAdFailedToReceive(ad: AdropNativeAd, errorCode: AdropErrorCode) {
                    Log.e("adrop", "$adType native ad failed to load: ${ad.unitId}")
                    Log.e("adrop", "Error code: $errorCode")
                    Log.e("adrop", "Error description: ${ErrorUtils.descriptionOf(errorCode)}")
                    
                    progressBar.visibility = View.GONE
                    tvAdInfo.text = "$adType 네이티브 광고 로드 실패: ${ErrorUtils.descriptionOf(errorCode)}"
                    Toast.makeText(this@NativeExampleActivity, 
                        "$adType 광고 로드 실패: ${ErrorUtils.descriptionOf(errorCode)}", 
                        Toast.LENGTH_LONG).show()
                    
                    // Just log the error, don't do fallbacks for now
                    Log.e("adrop", "네이티브 광고 로드 실패 - 에러 확인 필요")
                }

                override fun onAdImpression(ad: AdropNativeAd) {
                    Log.d("adrop", "$adType native ad impression recorded: ${ad.unitId}")
                }
            }
        }
        
        Log.d("adrop", "Calling nativeAd.load() for $adType")
        nativeAd.load()
    }

    private fun loadAndDisplayAdWithEmptyContext(unitId: String, adType: String) {
        Log.d("adrop", "Starting to load $adType with empty context, unitId: $unitId")
        tvAdInfo.text = "$adType 광고 로드 중..."
        progressBar.visibility = View.VISIBLE
        
        // Clear previous ad
        currentNativeAd?.destroy()
        currentNativeAd = null
        recyclerView.adapter = null
        
        val nativeAd = AdropNativeAd(this, unitId, "").apply {
            listener = object : AdropNativeAdListener {
                override fun onAdReceived(ad: AdropNativeAd) {
                    Log.d("adrop", "$adType native ad received successfully: ${ad.unitId}")
                    
                    currentNativeAd = ad
                    val adapter = PostAdapter(ad)
                    recyclerView.adapter = adapter
                    progressBar.visibility = View.GONE
                    tvAdInfo.text = "$adType 네이티브 광고 로드 완료 - 표시 중"
                    
                    Toast.makeText(this@NativeExampleActivity, "$adType 광고가 성공적으로 로드되었습니다!", Toast.LENGTH_SHORT).show()
                }

                override fun onAdClick(ad: AdropNativeAd) {
                    Log.d("adrop", "$adType native ad clicked: ${ad.unitId}")
                }

                override fun onAdFailedToReceive(ad: AdropNativeAd, errorCode: AdropErrorCode) {
                    Log.e("adrop", "$adType native ad with empty context failed to load: ${ad.unitId}")
                    Log.e("adrop", "Error code: $errorCode")
                    Log.e("adrop", "Error description: ${ErrorUtils.descriptionOf(errorCode)}")
                    
                    progressBar.visibility = View.GONE
                    tvAdInfo.text = "$adType 네이티브 광고 로드 실패: ${ErrorUtils.descriptionOf(errorCode)}"
                    Toast.makeText(this@NativeExampleActivity, 
                        "모든 네이티브 광고 로드 실패: ${ErrorUtils.descriptionOf(errorCode)}", 
                        Toast.LENGTH_LONG).show()
                }

                override fun onAdImpression(ad: AdropNativeAd) {
                    Log.d("adrop", "$adType native ad impression recorded: ${ad.unitId}")
                }
            }
        }
        
        Log.d("adrop", "Calling nativeAd.load() for $adType with empty context")
        nativeAd.load()
    }

    private fun resetNativeAd() {
        Log.d("adrop", "네이티브 광고 초기화 실행")
        
        // 현재 광고 정리
        currentNativeAd?.destroy()
        currentNativeAd = null
        
        // RecyclerView 초기화
        recyclerView.adapter = null
        
        // UI 초기화
        progressBar.visibility = View.GONE
        tvAdInfo.text = "초기화 완료! 네이티브 광고 버튼을 눌러 광고를 로드하세요."
        
        Toast.makeText(this, "네이티브 광고가 초기화되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        currentNativeAd?.destroy()
        super.onDestroy()
    }
}