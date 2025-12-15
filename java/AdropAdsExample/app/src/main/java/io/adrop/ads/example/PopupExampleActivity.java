package io.adrop.ads.example;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.popupAd.AdropPopupAd;
import io.adrop.ads.popupAd.AdropPopupAdCloseListener;
import io.adrop.ads.popupAd.AdropPopupAdListener;

public class PopupExampleActivity extends AppCompatActivity {
    private final String PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM = "PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM";
    private final String PUBLIC_TEST_UNIT_ID_POPUP_CENTER = "PUBLIC_TEST_UNIT_ID_POPUP_CENTER";
    private final String INVALID_UNIT_ID = "INVALID_UNIT_ID";

    TextView tvErrorCode;
    TextView tvErrorDesc;

    Button btnShow;
    Button btnReset;
    Button btnResetCenter;
    Button btnResetInvalid;

    Boolean isLoaded = false;
    Boolean isShown = false;
    AdropPopupAd popupAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_example);

        btnShow = findViewById(R.id.show);
        btnReset = findViewById(R.id.reset);
        btnResetCenter = findViewById(R.id.reset_center);
        btnResetInvalid = findViewById(R.id.reset_invalid);
        tvErrorCode = findViewById(R.id.popup_error_code);
        tvErrorDesc = findViewById(R.id.popup_error_code_desc);

        findViewById(R.id.load).setOnClickListener(v -> load());
        findViewById(R.id.show).setOnClickListener(v -> show());
        btnReset.setOnClickListener(v -> reset(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM));
        btnResetCenter.setOnClickListener(v -> reset(PUBLIC_TEST_UNIT_ID_POPUP_CENTER));
        btnResetInvalid.setOnClickListener(v -> reset(INVALID_UNIT_ID));
        reset(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM);
    }

    /**
     * Resets the popup ad with a new unit ID.
     * Destroys the previous ad instance and creates a new one with listeners.
     */
    private void reset(String unitId) {
        if (popupAd != null) {
            popupAd.destroy();
        }

        popupAd = new AdropPopupAd(this, unitId);
        popupAd.setPopupAdListener(new AdropPopupAdListener() {
            // Called when an ad is successfully loaded and ready to be shown
            @Override
            public void onAdReceived(AdropPopupAd adropPopupAd) {
                isLoaded = true;
                btnShow.setEnabled(true);
            }

            // Called when an ad fails to load
            @Override
            public void onAdFailedToReceive(AdropPopupAd adropPopupAd, AdropErrorCode adropErrorCode) {
                setError(adropErrorCode);
            }

            // Called when an ad impression is recorded
            @Override
            public void onAdImpression(AdropPopupAd adropPopupAd) {

            }

            // Called when the user clicks on the ad
            @Override
            public void onAdClicked(AdropPopupAd adropPopupAd) {

            }

            // Called right before the fullscreen ad is presented
            @Override
            public void onAdWillPresentFullScreen(AdropPopupAd adropPopupAd) {

            }

            // Called when the fullscreen ad is presented
            @Override
            public void onAdDidPresentFullScreen(AdropPopupAd adropPopupAd) {
                isShown = true;
                btnReset.setEnabled(true);
                btnResetCenter.setEnabled(true);
                btnResetInvalid.setEnabled(true);
                tvErrorCode.setText(null);
                tvErrorDesc.setText(null);
            }

            // Called right before the fullscreen ad is dismissed
            @Override
            public void onAdWillDismissFullScreen(AdropPopupAd adropPopupAd) {

            }

            // Called when the fullscreen ad is dismissed
            @Override
            public void onAdDidDismissFullScreen(AdropPopupAd adropPopupAd) {

            }

            // Called when the ad fails to show in fullscreen
            @Override
            public void onAdFailedToShowFullScreen(AdropPopupAd adropPopupAd, AdropErrorCode adropErrorCode) {
                setError(adropErrorCode);
            }
        });
        // Close listener handles user interactions with popup close options
        popupAd.setCloseListener(new AdropPopupAdCloseListener() {
            // Called when the popup is closed
            @Override
            public void onClosed(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "popup ad closed");
            }

            // Called when user clicks the dimmed background area
            @Override
            public void onDimClicked(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "popup ad dim clicked");
            }

            // Called when user clicks "Don't show today" option
            @Override
            public void onTodayOffClicked(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "popup ad today off clicked");
            }
        });

        btnShow.setEnabled(false);
        btnReset.setEnabled(false);
        btnResetCenter.setEnabled(false);
        btnResetInvalid.setEnabled(false);
        tvErrorDesc.setText(null);
        tvErrorCode.setText(null);
    }

    private void load() {
        if (popupAd != null) popupAd.load();
    }

    private void show() {
        if (popupAd != null) popupAd.show(this);
    }

    private void setError(AdropErrorCode code) {
        if (code != null) {
            tvErrorCode.setText(code.name());
            tvErrorDesc.setText(ErrorUtils.descriptionOf(code));
            btnReset.setEnabled(true);
            btnResetCenter.setEnabled(true);
            btnResetInvalid.setEnabled(true);
        } else {
            tvErrorCode.setText(null);
            tvErrorDesc.setText(null);
        }
    }
}
