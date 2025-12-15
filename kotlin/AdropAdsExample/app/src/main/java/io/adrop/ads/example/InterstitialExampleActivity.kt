package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
    lateinit var btnShow: Button
    lateinit var btnReset: Button
    lateinit var btnResetInvalid: Button
    var isLoaded = false
    var isShown = false
    var interstitialAd: AdropInterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_example)

        btnShow = findViewById(R.id.show)
        btnReset = findViewById(R.id.reset)
        btnResetInvalid = findViewById(R.id.reset_invalid)
        tvErrorCode = findViewById(R.id.interstitial_error_code)
        tvErrorDesc = findViewById(R.id.interstitial_error_code_desc)

        findViewById<View>(R.id.load).setOnClickListener {
            interstitialAd?.load()
        }
        findViewById<View>(R.id.show).setOnClickListener {
            interstitialAd?.show(this)
        }
        btnReset.setOnClickListener { reset(PUBLIC_TEST_UNIT_ID_INTERSTITIAL) }
        btnResetInvalid.setOnClickListener { reset(INVALID_UNIT_ID) }
        reset(PUBLIC_TEST_UNIT_ID_INTERSTITIAL)
    }

    /**
     * Resets the interstitial ad with a new unit ID.
     * Destroys the previous ad instance and creates a new one with the listener.
     */
    private fun reset(unitId: String) {
        interstitialAd?.destroy()
        interstitialAd = AdropInterstitialAd(this, unitId)
        interstitialAd?.interstitialAdListener = object : AdropInterstitialAdListener {
            // Called when the ad fails to show in fullscreen
            override fun onAdFailedToShowFullScreen(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
                setError(errorCode)
            }

            // Called when the fullscreen ad is dismissed
            override fun onAdDidDismissFullScreen(ad: AdropInterstitialAd) {}

            // Called right before the fullscreen ad is dismissed
            override fun onAdWillDismissFullScreen(ad: AdropInterstitialAd) {}

            // Called when the fullscreen ad is presented
            override fun onAdDidPresentFullScreen(ad: AdropInterstitialAd) {
                isShown = true
                btnReset.isEnabled = true
                btnResetInvalid.isEnabled = true
            }

            // Called right before the fullscreen ad is presented
            override fun onAdWillPresentFullScreen(ad: AdropInterstitialAd) {}

            // Called when the user clicks on the ad
            override fun onAdClicked(ad: AdropInterstitialAd) {
                Log.d("adrop", "InterstitialAd clicked " + ad.unitId)
            }

            // Called when an ad impression is recorded
            override fun onAdImpression(ad: AdropInterstitialAd) {
                Log.d("adrop", "InterstitialAd impression " + ad.unitId)
            }

            // Called when an ad is successfully loaded and ready to be shown
            override fun onAdReceived(ad: AdropInterstitialAd) {
                Log.d("adrop", "InterstitialAd received " + ad.unitId)
                isLoaded = true
                btnShow.isEnabled = true
            }

            // Called when an ad fails to load
            override fun onAdFailedToReceive(ad: AdropInterstitialAd, errorCode: AdropErrorCode) {
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
        // Clean up the interstitial ad to prevent memory leaks
        interstitialAd?.destroy()
        interstitialAd = null
    }
}
