package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
    lateinit var btnShow: Button
    lateinit var btnReset: Button
    lateinit var btnResetInvalid: Button

    var isLoaded = false
    var isShown = false
    var rewardedAd: AdropRewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewarded_ad_example)

        btnShow = findViewById(R.id.show)
        btnReset = findViewById(R.id.reset)
        btnResetInvalid = findViewById(R.id.reset_invalid)
        tvErrorCode = findViewById(R.id.rewarded_error_code)
        tvErrorDesc = findViewById(R.id.rewarded_error_code_desc)

        findViewById<View>(R.id.load).setOnClickListener { rewardedAd?.load() }
        findViewById<View>(R.id.show).setOnClickListener {
            // Show the rewarded ad with a callback to handle the reward
            rewardedAd?.show(this) { type: Int, amount: Int ->
                Log.d("adrop", "RewardedAd earn rewards type: $type, amount: $amount")
            }
        }
        btnReset.setOnClickListener { reset(PUBLIC_TEST_UNIT_ID_REWARDED) }
        btnResetInvalid.setOnClickListener { reset(INVALID_UNIT_ID) }
        reset(PUBLIC_TEST_UNIT_ID_REWARDED)
    }

    /**
     * Resets the rewarded ad with a new unit ID.
     * Destroys the previous ad instance and creates a new one with the listener.
     */
    private fun reset(unitId: String) {
        rewardedAd?.destroy()
        rewardedAd = AdropRewardedAd(this, unitId)
        rewardedAd?.rewardedAdListener = object : AdropRewardedAdListener {
            // Called when the ad fails to show in fullscreen
            override fun onAdFailedToShowFullScreen(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                setError(errorCode)
            }

            // Called when the fullscreen ad is dismissed
            override fun onAdDidDismissFullScreen(ad: AdropRewardedAd) {
                Log.d("adrop", "rewarded ad dismiss screen $ad")
            }

            // Called right before the fullscreen ad is dismissed
            override fun onAdWillDismissFullScreen(ad: AdropRewardedAd) {}

            // Called when the fullscreen ad is presented
            override fun onAdDidPresentFullScreen(ad: AdropRewardedAd) {
                isShown = true
                btnReset.isEnabled = true
                btnResetInvalid.isEnabled = true
            }

            // Called right before the fullscreen ad is presented
            override fun onAdWillPresentFullScreen(ad: AdropRewardedAd) {}

            // Called when the user clicks on the ad
            override fun onAdClicked(ad: AdropRewardedAd) {
                Log.d("adrop", "rewarded ad click $ad")
            }

            // Called when an ad impression is recorded
            override fun onAdImpression(ad: AdropRewardedAd) {
                Log.d("adrop", "rewarded ad impression $ad")
            }

            // Called when an ad is successfully loaded and ready to be shown
            override fun onAdReceived(ad: AdropRewardedAd) {
                Log.d("adrop", "rewarded ad received $ad")
                isLoaded = true
                btnShow.isEnabled = true
            }

            // Called when an ad fails to load
            override fun onAdFailedToReceive(ad: AdropRewardedAd, errorCode: AdropErrorCode) {
                setError(errorCode)
            }
        }
        btnShow.isEnabled = false
        btnReset.isEnabled = false
        btnResetInvalid.isEnabled = false
        tvErrorDesc.text = null
        tvErrorCode.text = null
    }

    private fun setError(code: AdropErrorCode?) {
        if (code != null) {
            tvErrorCode.text = code.name
            tvErrorDesc.text = ErrorUtils.descriptionOf(code)
            btnReset.isEnabled = true
            btnResetInvalid.isEnabled = true
        } else {
            tvErrorCode.text = null
            tvErrorDesc.text = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up the rewarded ad to prevent memory leaks
        rewardedAd?.destroy()
        rewardedAd = null
    }
}
