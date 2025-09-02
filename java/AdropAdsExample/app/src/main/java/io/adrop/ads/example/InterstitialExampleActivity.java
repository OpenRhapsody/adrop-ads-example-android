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

        findViewById(R.id.load).setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_INTERSTITIAL));
        findViewById(R.id.show).setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_INTERSTITIAL));
        btnReset.setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_INTERSTITIAL));
        btnResetInvalid.setOnClickListener(v -> loadAndShowAd(INVALID_UNIT_ID));
    }

    private void loadAndShowAd(String unitId) {
        Log.d("adrop", "Starting to load interstitial ad with unitId: " + unitId);
        
        // Clear previous error state
        tvErrorCode.setText(null);
        tvErrorDesc.setText(null);
        
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        
        interstitialAd = new AdropInterstitialAd(this, unitId);
        interstitialAd.setInterstitialAdListener(new AdropInterstitialAdListener() {
            @Override
            public void onAdReceived(@NotNull AdropInterstitialAd ad) {
                Log.d("adrop", String.format("InterstitialAd received: %s", ad.getUnitId()));
                ad.show(InterstitialExampleActivity.this);
            }

            @Override
            public void onAdFailedToReceive(@NotNull AdropInterstitialAd ad, @NotNull AdropErrorCode errorCode) {
                Log.e("adrop", "Interstitial ad failed to load: " + ad.getUnitId());
                setError(errorCode);
            }

            @Override
            public void onAdImpression(@NotNull AdropInterstitialAd ad) {
                Log.d("adrop", String.format("InterstitialAd impression: %s", ad.getUnitId()));
            }

            @Override
            public void onAdClicked(@NotNull AdropInterstitialAd ad) {
                Log.d("adrop", String.format("InterstitialAd clicked: %s", ad.getUnitId()));
            }

            @Override
            public void onAdWillPresentFullScreen(@NotNull AdropInterstitialAd ad) {
            }

            @Override
            public void onAdDidPresentFullScreen(@NotNull AdropInterstitialAd ad) {
                tvErrorCode.setText(null);
                tvErrorDesc.setText(null);
            }

            @Override
            public void onAdWillDismissFullScreen(@NotNull AdropInterstitialAd ad) {
            }

            @Override
            public void onAdDidDismissFullScreen(@NotNull AdropInterstitialAd ad) {
            }

            @Override
            public void onAdFailedToShowFullScreen(@NotNull AdropInterstitialAd ad, @NotNull AdropErrorCode errorCode) {
                setError(errorCode);
            }
        });
        
        interstitialAd.load();
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
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }
}
