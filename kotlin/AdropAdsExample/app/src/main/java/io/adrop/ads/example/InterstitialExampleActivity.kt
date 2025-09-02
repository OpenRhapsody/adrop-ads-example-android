package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.example.helper.ErrorUtils
import io.adrop.ads.interstitial.AdropInterstitialAd
import io.adrop.ads.interstitial.AdropInterstitialAdListener
import io.adrop.ads.model.AdropErrorCode

class InterstitialExampleActivity : AppCompatActivity() {
    private val PUBLIC_TEST_UNIT_ID_INTERSTITIAL = "PUBLIC_TEST_UNIT_ID_INTERSTITIAL"
    private val INVALID_UNIT_ID = "INVALID_UNIT_ID"

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var tvAdInfo: TextView
    
    private var interstitialAd: AdropInterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_example)

        tvErrorCode = findViewById(R.id.interstitial_error_code)
        tvErrorDesc = findViewById(R.id.interstitial_error_code_desc)
        tvAdInfo = findViewById(R.id.ad_info)

        setButtons()
    }

    private fun setButtons() {
        findViewById<View>(R.id.show_interstitial).setOnClickListener { loadAndShowInterstitialAd() }
        findViewById<View>(R.id.show_invalid).setOnClickListener { loadAndShowInvalidAd() }
    }


    private fun loadAndShowInterstitialAd() {
        updateAdInfo("전면 광고 로드 중...")
        clearError()
        
        interstitialAd = AdropInterstitialAd(this, PUBLIC_TEST_UNIT_ID_INTERSTITIAL).apply {
            interstitialAdListener = object : AdropInterstitialAdListener {
                override fun onAdReceived(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 로드 완료: ${ad.unitId}")
                    updateAdInfo("전면 광고 로드 완료 - 표시 중")
                    ad.show(this@InterstitialExampleActivity)
                }

                override fun onAdFailedToReceive(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
                    Log.e("adrop", "전면 광고 로드 실패: ${ad.unitId}, $errorCode")
                    updateAdInfo("전면 광고 로드 실패")
                    setError(errorCode)
                }

                override fun onAdImpression(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 노출: ${ad.unitId}")
                }

                override fun onAdClicked(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 클릭: ${ad.unitId}")
                }

                override fun onAdWillPresentFullScreen(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 표시 예정")
                }

                override fun onAdDidPresentFullScreen(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 표시 완료")
                    clearError()
                }

                override fun onAdWillDismissFullScreen(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 닫기 예정")
                }

                override fun onAdDidDismissFullScreen(ad: AdropInterstitialAd) {
                    Log.d("adrop", "전면 광고 닫힘")
                }

                override fun onAdFailedToShowFullScreen(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
                    Log.e("adrop", "전면 광고 표시 실패: ${ad.unitId}, $errorCode")
                    setError(errorCode)
                }
            }
            load()
        }
    }


    private fun loadAndShowInvalidAd() {
        val invalidAd = AdropInterstitialAd(this, INVALID_UNIT_ID).apply {
            interstitialAdListener = object : AdropInterstitialAdListener {
                override fun onAdReceived(ad: AdropInterstitialAd) {
                    Log.d("adrop", "잘못된 광고 로드됨 (발생하지 않아야 함): ${ad.unitId}")
                    ad.show(this@InterstitialExampleActivity)
                }

                override fun onAdFailedToReceive(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
                    Log.d("adrop", "잘못된 광고 로드 실패 (예상된 결과): ${ad.unitId}, $errorCode")
                    setError(errorCode)
                }

                override fun onAdImpression(ad: AdropInterstitialAd) {}
                override fun onAdClicked(ad: AdropInterstitialAd) {}
                override fun onAdWillPresentFullScreen(ad: AdropInterstitialAd) {}
                override fun onAdDidPresentFullScreen(ad: AdropInterstitialAd) {}
                override fun onAdWillDismissFullScreen(ad: AdropInterstitialAd) {}
                override fun onAdDidDismissFullScreen(ad: AdropInterstitialAd) {}
                override fun onAdFailedToShowFullScreen(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
                    setError(errorCode)
                }
            }
        }
        invalidAd.load()
    }

    private fun updateAdInfo(message: String) {
        tvAdInfo.text = message
    }

    private fun setError(code: AdropErrorCode?) {
        if (code != null) {
            tvErrorCode.text = code.name
            tvErrorDesc.text = ErrorUtils.descriptionOf(code)
        } else {
            clearError()
        }
    }

    private fun clearError() {
        tvErrorCode.text = null
        tvErrorDesc.text = null
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd?.destroy()
        interstitialAd = null
    }
}