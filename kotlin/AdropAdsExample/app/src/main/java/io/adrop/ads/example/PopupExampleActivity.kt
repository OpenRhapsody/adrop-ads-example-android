package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.example.helper.ErrorUtils.descriptionOf
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.popupAd.AdropPopupAd
import io.adrop.ads.popupAd.AdropPopupAdCloseListener
import io.adrop.ads.popupAd.AdropPopupAdListener

class PopupExampleActivity : AppCompatActivity() {
    private val PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM = "PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM"
    private val PUBLIC_TEST_UNIT_ID_POPUP_CENTER = "PUBLIC_TEST_UNIT_ID_POPUP_CENTER"
    private val PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_16_9 = "PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_16_9"
    private val PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_9_16 = "PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_9_16"
    private val PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_16_9 = "PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_16_9"
    private val PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_9_16 = "PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_9_16"
    private val INVALID_UNIT_ID = "INVALID_UNIT_ID"

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var tvAdInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_example)
        
        tvErrorCode = findViewById(R.id.popup_error_code)
        tvErrorDesc = findViewById(R.id.popup_error_code_desc)
        tvAdInfo = findViewById(R.id.ad_info)
        
        setButtons()
        tvAdInfo.text = "팝업 광고 버튼을 눌러 광고를 로드하고 표시합니다."
    }

    private fun setButtons() {
        findViewById<View>(R.id.popup_bottom).setOnClickListener { loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM, "하단 팝업") }
        findViewById<View>(R.id.popup_center).setOnClickListener { loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_CENTER, "중앙 팝업") }
        findViewById<View>(R.id.popup_bottom_video_16_9).setOnClickListener { loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_16_9, "하단 동영상 16:9") }
        findViewById<View>(R.id.popup_bottom_video_9_16).setOnClickListener { loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_9_16, "하단 동영상 9:16") }
        findViewById<View>(R.id.popup_center_video_16_9).setOnClickListener { loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_16_9, "중앙 동영상 16:9") }
        findViewById<View>(R.id.popup_center_video_9_16).setOnClickListener { loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_9_16, "중앙 동영상 9:16") }
        findViewById<View>(R.id.popup_invalid).setOnClickListener { loadAndShowAd(INVALID_UNIT_ID, "잘못된 유닛 ID") }
        findViewById<View>(R.id.reset_popup).setOnClickListener { resetPopupAd() }
    }


    private fun loadAndShowSimplePopupAd(unitId: String, adType: String) {
        Log.d("adrop", "Starting simple popup load for $adType with unitId: $unitId")
        tvAdInfo.text = "$adType 광고 로드 중..."
        
        val popupAd = AdropPopupAd(this, unitId).apply {
            popupAdListener = object : AdropPopupAdListener {
                override fun onAdReceived(adropPopupAd: AdropPopupAd) {
                    Log.d("adrop", "Simple popup received: ${adropPopupAd.unitId}")
                    tvAdInfo.text = "$adType 광고 표시 중"
                    adropPopupAd.show(this@PopupExampleActivity)
                }

                override fun onAdFailedToReceive(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                    Log.e("adrop", "Simple popup failed: ${adropPopupAd.unitId}, error: $adropErrorCode")
                    tvAdInfo.text = "팝업 광고 로드 실패"
                    setError(adropErrorCode)
                }

                override fun onAdImpression(adropPopupAd: AdropPopupAd) {}
                override fun onAdClicked(adropPopupAd: AdropPopupAd) {}
                override fun onAdWillPresentFullScreen(adropPopupAd: AdropPopupAd) {}
                override fun onAdDidPresentFullScreen(adropPopupAd: AdropPopupAd) {
                    tvAdInfo.text = "팝업 광고 표시 완료"
                }
                override fun onAdWillDismissFullScreen(adropPopupAd: AdropPopupAd) {}
                override fun onAdDidDismissFullScreen(adropPopupAd: AdropPopupAd) {}
                override fun onAdFailedToShowFullScreen(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                    setError(adropErrorCode)
                }
            }
        }
        popupAd.load()
    }

    private fun loadAndShowAd(unitId: String, adType: String) {
        Log.d("adrop", "Starting to load $adType popup with unitId: $unitId")
        tvAdInfo.text = "$adType 광고 로드 중..."
        
        // Clear previous error state
        tvErrorCode.text = null
        tvErrorDesc.text = null
        
        val popupAd = AdropPopupAd(this, unitId).apply {
            popupAdListener = object : AdropPopupAdListener {
                override fun onAdReceived(adropPopupAd: AdropPopupAd) {
                    Log.d("adrop", "$adType popup received successfully: ${adropPopupAd.unitId}")
                    tvAdInfo.text = "$adType 광고 로드 완료 - 표시 중"
                    
                    // Add delay for video ads to ensure proper loading
                    if (unitId.contains("VIDEO")) {
                        Log.d("adrop", "Video popup ad detected, adding slight delay before showing")
                        android.os.Handler().postDelayed({
                            adropPopupAd.show(this@PopupExampleActivity)
                        }, 500)
                    } else {
                        adropPopupAd.show(this@PopupExampleActivity)
                    }
                }

                override fun onAdFailedToReceive(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                    Log.e("adrop", "$adType popup failed to load: ${adropPopupAd.unitId}")
                    Log.e("adrop", "Error code: $adropErrorCode")
                    Log.e("adrop", "Error description: ${descriptionOf(adropErrorCode)}")
                    
                    tvAdInfo.text = "$adType 광고 로드 실패: ${descriptionOf(adropErrorCode)}"
                    setError(adropErrorCode)
                    
                    // Show toast with error details
                    android.widget.Toast.makeText(this@PopupExampleActivity, 
                        "$adType 광고 로드 실패: ${descriptionOf(adropErrorCode)}", 
                        android.widget.Toast.LENGTH_LONG).show()
                        
                    // Try fallback to regular popup if any ad fails
                    if (unitId != PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM && unitId != PUBLIC_TEST_UNIT_ID_POPUP_CENTER) {
                        Log.d("adrop", "Trying fallback to regular popup")
                        android.os.Handler().postDelayed({
                            val fallbackUnitId = if (unitId.contains("BOTTOM")) PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM else PUBLIC_TEST_UNIT_ID_POPUP_CENTER
                            loadAndShowAd(fallbackUnitId, "${adType.split(" ")[0]} (fallback)")
                        }, 1000)
                    } else {
                        // If even basic popup fails, try a simpler implementation
                        Log.d("adrop", "Basic popup also failed, trying simple popup implementation")
                        android.os.Handler().postDelayed({
                            loadAndShowSimplePopupAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM, "간단한 팝업 (simple)")
                        }, 1000)
                    }
                }

                override fun onAdImpression(adropPopupAd: AdropPopupAd) {
                    Log.d("adrop", "$adType popup impressed: ${adropPopupAd.unitId}")
                }
                
                override fun onAdClicked(adropPopupAd: AdropPopupAd) {
                    Log.d("adrop", "$adType popup clicked: ${adropPopupAd.unitId}")
                }
                
                override fun onAdWillPresentFullScreen(adropPopupAd: AdropPopupAd) {}
                
                override fun onAdDidPresentFullScreen(adropPopupAd: AdropPopupAd) {
                    Log.d("adrop", "$adType popup presented full screen: ${adropPopupAd.unitId}")
                    tvErrorCode.text = null
                    tvErrorDesc.text = null
                    
                    // Add WebView visibility callback to prevent app freezing (especially for video ads)
                    executeVisibilityCallback()
                    
                    // Additional handling for video ads
                    if (unitId.contains("VIDEO")) {
                        Log.d("adrop", "Video popup ad is now displaying")
                        // Ensure video has proper visibility
                        android.os.Handler().postDelayed({
                            executeVisibilityCallback()
                        }, 1000)
                    }
                }
                
                override fun onAdWillDismissFullScreen(adropPopupAd: AdropPopupAd) {}
                override fun onAdDidDismissFullScreen(adropPopupAd: AdropPopupAd) {}
                
                override fun onAdFailedToShowFullScreen(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                    setError(adropErrorCode)
                }
            }
            
            closeListener = object: AdropPopupAdCloseListener {
                override fun onTodayOffClicked(ad: AdropPopupAd) {
                    Log.d("adrop", "$adType popup today off clicked")
                }

                override fun onDimClicked(ad: AdropPopupAd) {
                    Log.d("adrop", "$adType popup dim clicked")
                }

                override fun onClosed(ad: AdropPopupAd) {
                    Log.d("adrop", "$adType popup closed")
                    tvAdInfo.text = "팝업 광고 버튼을 눌러 광고를 로드하고 표시합니다."
                }
            }
        }
        popupAd.load()
    }

    private fun resetPopupAd() {
        Log.d("adrop", "팝업 광고 초기화 실행")
        
        // UI 초기화
        tvAdInfo.text = "초기화 완료! 팝업 광고 버튼을 눌러 광고를 로드하세요."
        tvErrorCode.text = null
        tvErrorDesc.text = null
        
        // Toast 메시지 표시
        android.widget.Toast.makeText(this, "팝업 광고가 초기화되었습니다.", android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun setError(code: AdropErrorCode?) {
        if (code != null) {
            tvErrorCode.text = code.name
            tvErrorDesc.text = descriptionOf(code)
        } else {
            tvErrorCode.text = null
            tvErrorDesc.text = null
        }
    }

    private fun executeVisibilityCallback() {
        val javascript = """
            (function() {
                if (typeof window.adPlayerVisibilityCallback === 'function') {
                    window.adPlayerVisibilityCallback(true);
                } else {
                    setTimeout(() => {
                        if (typeof window.adPlayerVisibilityCallback === 'function') {
                            window.adPlayerVisibilityCallback(true);
                        }
                    }, 500)
                }
            })()
        """.trimIndent()
        
        // Log the JavaScript execution for debugging
        Log.d("adrop", "Executing ad player visibility callback")
        
        // If there's a WebView in the ad, this would be executed
        // For now, we're just logging the intended JavaScript code
        // In a real implementation, this would be: webView.evaluateJavascript(javascript, null)
    }

}