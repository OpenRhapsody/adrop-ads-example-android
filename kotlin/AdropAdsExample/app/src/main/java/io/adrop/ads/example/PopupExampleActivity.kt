package io.adrop.ads.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
    private val INVALID_UNIT_ID = "INVALID_UNIT_ID"

    lateinit var tvErrorCode: TextView
    lateinit var tvErrorDesc: TextView

    lateinit var btnShow: Button
    lateinit var btnReset: Button
    lateinit var btnResetCenter: Button
    lateinit var btnResetInvalid: Button

    var isLoaded = false
    var isShown = false
    var popupAd: AdropPopupAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_example)
        btnShow = findViewById(R.id.show)
        btnReset = findViewById(R.id.reset)
        btnResetCenter = findViewById(R.id.reset_center)
        btnResetInvalid = findViewById(R.id.reset_invalid)
        tvErrorCode = findViewById(R.id.popup_error_code)
        tvErrorDesc = findViewById(R.id.popup_error_code_desc)
        findViewById<View>(R.id.load).setOnClickListener { v: View? -> load() }
        findViewById<View>(R.id.show).setOnClickListener { v: View? -> show() }
        btnReset.setOnClickListener { _ -> reset(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM) }
        btnResetCenter.setOnClickListener { _ -> reset(PUBLIC_TEST_UNIT_ID_POPUP_CENTER) }
        btnResetInvalid.setOnClickListener { _ -> reset(INVALID_UNIT_ID) }
        reset(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM)
    }

    private fun reset(unitId: String) {
        popupAd?.destroy()
        popupAd = AdropPopupAd(this, unitId)
        popupAd?.popupAdListener = object : AdropPopupAdListener {
            override fun onAdReceived(adropPopupAd: AdropPopupAd) {
                isLoaded = true
                btnShow.setEnabled(true)
            }

            override fun onAdFailedToReceive(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                setError(adropErrorCode)
            }

            override fun onAdImpression(adropPopupAd: AdropPopupAd) {}
            override fun onAdClicked(adropPopupAd: AdropPopupAd) {}
            override fun onAdWillPresentFullScreen(adropPopupAd: AdropPopupAd) {}
            override fun onAdDidPresentFullScreen(adropPopupAd: AdropPopupAd) {
                isShown = true
                btnReset.setEnabled(true)
                btnResetCenter.setEnabled(true)
                btnResetInvalid.setEnabled(true)
                tvErrorCode.setText(null)
                tvErrorDesc.setText(null)
            }

            override fun onAdWillDismissFullScreen(adropPopupAd: AdropPopupAd) {}
            override fun onAdDidDismissFullScreen(adropPopupAd: AdropPopupAd) {}
            override fun onAdFailedToShowFullScreen(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                setError(adropErrorCode)
            }
        }
        popupAd?.closeListener = object: AdropPopupAdCloseListener {
            override fun onTodayOffClicked(ad: AdropPopupAd) {
                Log.d("adrop", "popup ad today off clicked")
            }

            override fun onDimClicked(ad: AdropPopupAd) {
                Log.d("adrop", "popup ad dim clicked")
            }

            override fun onClosed(ad: AdropPopupAd) {
                Log.d("adrop", "popup ad closed")
            }
        }
        btnShow.setEnabled(false)
        btnReset.setEnabled(false)
        btnResetCenter.setEnabled(false)
        btnResetInvalid.setEnabled(false)
        tvErrorDesc.setText(null)
        tvErrorCode.setText(null)
    }

    private fun load() {
        popupAd?.load()
    }

    private fun show() {
        popupAd?.show(this)
    }

    private fun setError(code: AdropErrorCode?) {
        if (code != null) {
            tvErrorCode.text = code.name
            tvErrorDesc.text = descriptionOf(code)
            btnReset.setEnabled(true)
            btnResetCenter.setEnabled(true)
            btnResetInvalid.setEnabled(true)
        } else {
            tvErrorCode.text = null
            tvErrorDesc.setText(null)
        }
    }
}
