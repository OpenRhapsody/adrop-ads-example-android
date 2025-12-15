package io.adrop.ads.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.interstitial.AdropInterstitialAd;
import io.adrop.ads.interstitial.AdropInterstitialAdListener;
import io.adrop.ads.model.AdropErrorCode;

import org.jetbrains.annotations.NotNull;

public class InterstitialExampleActivity extends AppCompatActivity {

    private final String PUBLIC_TEST_UNIT_ID_INTERSTITIAL = "PUBLIC_TEST_UNIT_ID_INTERSTITIAL";
    private final String INVALID_UNIT_ID = "INVALID_UNIT_ID";

    TextView tvErrorCode;
    TextView tvErrorDesc;

    Button btnShow;
    Button btnReset;
    Button btnResetInvalid;

    Boolean isLoaded = false;
    Boolean isShown = false;
    AdropInterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_example);

        btnShow = findViewById(R.id.show);
        btnReset = findViewById(R.id.reset);
        btnResetInvalid = findViewById(R.id.reset_invalid);
        tvErrorCode = findViewById(R.id.interstitial_error_code);
        tvErrorDesc = findViewById(R.id.interstitial_error_code_desc);

        findViewById(R.id.load).setOnClickListener(v -> {
            if (interstitialAd != null) {
                interstitialAd.load();
            }
        });
        findViewById(R.id.show).setOnClickListener(v -> {
            if (interstitialAd != null) {
                interstitialAd.show(this);
            }
        });
        btnReset.setOnClickListener(v -> reset(PUBLIC_TEST_UNIT_ID_INTERSTITIAL));
        btnResetInvalid.setOnClickListener(v -> reset(INVALID_UNIT_ID));
        reset(PUBLIC_TEST_UNIT_ID_INTERSTITIAL);
    }

    /**
     * Resets the interstitial ad with a new unit ID.
     * Destroys the previous ad instance and creates a new one with the listener.
     */
    private void reset(String unitId) {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        interstitialAd = new AdropInterstitialAd(this, unitId);
        interstitialAd.setInterstitialAdListener(new AdropInterstitialAdListener() {
            // Called when the ad fails to show in fullscreen
            @Override
            public void onAdFailedToShowFullScreen(@NotNull AdropInterstitialAd ad, @NotNull AdropErrorCode errorCode) {
                setError(errorCode);
            }

            // Called when the fullscreen ad is dismissed
            @Override
            public void onAdDidDismissFullScreen(@NotNull AdropInterstitialAd ad) {
            }

            // Called right before the fullscreen ad is dismissed
            @Override
            public void onAdWillDismissFullScreen(@NotNull AdropInterstitialAd ad) {
            }

            // Called when the fullscreen ad is presented
            @Override
            public void onAdDidPresentFullScreen(@NotNull AdropInterstitialAd ad) {
                isShown = true;
                btnReset.setEnabled(true);
                btnResetInvalid.setEnabled(true);
                tvErrorCode.setText(null);
                tvErrorDesc.setText(null);
            }

            // Called right before the fullscreen ad is presented
            @Override
            public void onAdWillPresentFullScreen(@NotNull AdropInterstitialAd ad) {
            }

            // Called when the user clicks on the ad
            @Override
            public void onAdClicked(@NotNull AdropInterstitialAd ad) {
                Log.d("adrop", String.format("InterstitialAd clicked: %s", ad.getUnitId()));
            }

            // Called when an ad impression is recorded
            @Override
            public void onAdImpression(@NotNull AdropInterstitialAd ad) {
                Log.d("adrop", String.format("InterstitialAd impression: %s", ad.getUnitId()));
            }

            // Called when an ad is successfully loaded and ready to be shown
            @Override
            public void onAdReceived(@NotNull AdropInterstitialAd ad) {
                Log.d("adrop", String.format("InterstitialAd received: %s", ad.getUnitId()));
                isLoaded = true;
                btnShow.setEnabled(true);
            }

            // Called when an ad fails to load
            @Override
            public void onAdFailedToReceive(@NotNull AdropInterstitialAd ad, @NotNull AdropErrorCode errorCode) {
                setError(errorCode);
            }
        });
        btnShow.setEnabled(false);
        btnReset.setEnabled(false);
        btnResetInvalid.setEnabled(false);
        tvErrorDesc.setText(null);
        tvErrorCode.setText(null);
    }

    private void setError(AdropErrorCode code) {
        if (code != null) {
            tvErrorCode.setText(code.name());
            tvErrorDesc.setText(ErrorUtils.descriptionOf(code));
            btnReset.setEnabled(true);
            btnResetInvalid.setEnabled(true);
        } else {
            tvErrorCode.setText(null);
            tvErrorDesc.setText(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up the interstitial ad to prevent memory leaks
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }
}
