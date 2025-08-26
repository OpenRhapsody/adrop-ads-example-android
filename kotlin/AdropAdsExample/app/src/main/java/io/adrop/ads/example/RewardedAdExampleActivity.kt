package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.adrop.ads.example.helper.ErrorUtils
import io.adrop.ads.model.AdropErrorCode
import io.adrop.ads.rewardedAd.AdropRewardedAd
import io.adrop.ads.rewardedAd.AdropRewardedAdListener

class RewardedAdExampleActivity : AppCompatActivity() {
    private val PUBLIC_TEST_UNIT_ID_REWARDED = "PUBLIC_TEST_UNIT_ID_REWARDED"
    private val INVALID_UNIT_ID = "INVALID_UNIT_ID"

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView
    lateinit var tvAdInfo: TextView
    lateinit var tvRewardInfo: TextView
    
    private var rewardedAd: AdropRewardedAd? = null
    private var totalCoins = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_ad_example)

        tvErrorCode = findViewById(R.id.rewarded_error_code)
        tvErrorDesc = findViewById(R.id.rewarded_error_code_desc)
        tvAdInfo = findViewById(R.id.ad_info)
        tvRewardInfo = findViewById(R.id.reward_info)

        setButtons()
        preloadRewardedAd()
        updateRewardInfo()
    }

    private fun setButtons() {
        findViewById<View>(R.id.show_rewarded).setOnClickListener { showRewardedAd() }
        findViewById<View>(R.id.show_invalid).setOnClickListener { loadAndShowInvalidAd() }
    }

    private fun preloadRewardedAd() {
        tvAdInfo.text = "보상형 광고 미리 로드 중..."
        
        rewardedAd = AdropRewardedAd(this, PUBLIC_TEST_UNIT_ID_REWARDED).apply {
            rewardedAdListener = object : AdropRewardedAdListener {
                override fun onAdReceived(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 미리 로드 완료: ${ad.unitId}")
                    updateAdInfo("보상형 광고 미리 로드 완료 - 즉시 표시 가능")
                }

                override fun onAdFailedToReceive(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                    Log.e("adrop", "보상형 광고 미리 로드 실패: ${ad.unitId}, $errorCode")
                    updateAdInfo("보상형 광고 로드 실패")
                    setError(errorCode)
                }

                override fun onAdImpression(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 노출: ${ad.unitId}")
                }

                override fun onAdClicked(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 클릭: ${ad.unitId}")
                }

                override fun onAdWillPresentFullScreen(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 표시 예정")
                }

                override fun onAdDidPresentFullScreen(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 표시 완료")
                    clearError()
                }

                override fun onAdWillDismissFullScreen(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 닫기 예정")
                }

                override fun onAdDidDismissFullScreen(ad: AdropRewardedAd) {
                    Log.d("adrop", "보상형 광고 닫힘")
                    // 새로운 광고 미리 로드
                    preloadNewRewardedAd()
                }

                override fun onAdFailedToShowFullScreen(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                    Log.e("adrop", "보상형 광고 표시 실패: ${ad.unitId}, $errorCode")
                    setError(errorCode)
                }
            }
            load()
        }
    }

    private fun showRewardedAd() {
        if (rewardedAd != null) {
            rewardedAd?.show(this) { type: Int, amount: Int ->
                Log.d("adrop", "보상 지급 - 타입: $type, 수량: $amount")
                giveReward(type, amount)
            }
        } else {
            tvErrorCode.text = "NO_AD_AVAILABLE"
            tvErrorDesc.text = "미리 로드된 보상형 광고가 없습니다."
        }
    }

    private fun giveReward(type: Int, amount: Int) {
        when (type) {
            0 -> { // 코인
                totalCoins += amount
                updateRewardInfo()
                updateAdInfo("보상 지급 완료: +$amount 코인")
            }
            1 -> { // 포인트 또는 다른 타입의 보상
                // 다른 보상 처리 로직
                updateAdInfo("보상 지급 완료: +$amount (타입: $type)")
            }
            else -> {
                updateAdInfo("보상 지급 완료: +$amount (타입: $type)")
            }
        }
    }

    private fun preloadNewRewardedAd() {
        rewardedAd = AdropRewardedAd(this, PUBLIC_TEST_UNIT_ID_REWARDED).apply {
            rewardedAdListener = rewardedAd?.rewardedAdListener
            load()
        }
        updateAdInfo("새로운 보상형 광고 로드 중...")
    }

    private fun loadAndShowInvalidAd() {
        val invalidAd = AdropRewardedAd(this, INVALID_UNIT_ID).apply {
            rewardedAdListener = object : AdropRewardedAdListener {
                override fun onAdReceived(ad: AdropRewardedAd) {
                    Log.d("adrop", "잘못된 보상형 광고 로드됨 (발생하지 않아야 함): ${ad.unitId}")
                    ad.show(this@RewardedAdExampleActivity) { type, amount ->
                        giveReward(type, amount)
                    }
                }

                override fun onAdFailedToReceive(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                    Log.d("adrop", "잘못된 보상형 광고 로드 실패 (예상된 결과): ${ad.unitId}, $errorCode")
                    setError(errorCode)
                }

                override fun onAdImpression(ad: AdropRewardedAd) {}
                override fun onAdClicked(ad: AdropRewardedAd) {}
                override fun onAdWillPresentFullScreen(ad: AdropRewardedAd) {}
                override fun onAdDidPresentFullScreen(ad: AdropRewardedAd) {}
                override fun onAdWillDismissFullScreen(ad: AdropRewardedAd) {}
                override fun onAdDidDismissFullScreen(ad: AdropRewardedAd) {}
                override fun onAdFailedToShowFullScreen(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                    setError(errorCode)
                }
            }
        }
        invalidAd.load()
    }

    private fun updateAdInfo(message: String) {
        tvAdInfo.text = message
    }

    private fun updateRewardInfo() {
        tvRewardInfo.text = "현재 보유 코인: $totalCoins"
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
        rewardedAd?.destroy()
        rewardedAd = null
    }
}