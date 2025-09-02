package io.adrop.ads.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.rewardedAd.AdropRewardedAd;
import io.adrop.ads.rewardedAd.AdropRewardedAdListener;

import org.jetbrains.annotations.NotNull;

public class RewardedAdExampleActivity extends AppCompatActivity {

    private final String PUBLIC_TEST_UNIT_ID_REWARDED = "PUBLIC_TEST_UNIT_ID_REWARDED";
    private final String INVALID_UNIT_ID = "INVALID_UNIT_ID";

    TextView tvErrorCode;
    TextView tvErrorDesc;

    Button btnShow;
    Button btnReset;
    Button btnResetInvalid;

    Boolean isLoaded = false;
    Boolean isShown = false;
    AdropRewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_ad_example);

        btnShow = findViewById(R.id.show);
        btnReset = findViewById(R.id.reset);
        btnResetInvalid = findViewById(R.id.reset_invalid);
        tvErrorCode = findViewById(R.id.rewarded_error_code);
        tvErrorDesc = findViewById(R.id.rewarded_error_code_desc);

        findViewById(R.id.load).setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_REWARDED));
        findViewById(R.id.show).setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_REWARDED));
        btnReset.setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_REWARDED));
        btnResetInvalid.setOnClickListener(v -> loadAndShowAd(INVALID_UNIT_ID));
    }

    private void loadAndShowAd(String unitId) {
        Log.d("adrop", "Starting to load rewarded ad with unitId: " + unitId);
        
        // Clear previous error state
        tvErrorCode.setText(null);
        tvErrorDesc.setText(null);
        
        if (rewardedAd != null) {
            rewardedAd.destroy();
        }
        
        rewardedAd = new AdropRewardedAd(this, unitId);
        rewardedAd.setRewardedAdListener(new AdropRewardedAdListener() {
            @Override
            public void onAdReceived(@NotNull AdropRewardedAd ad) {
                Log.d("adrop", String.format("rewarded ad received %s", ad.getUnitId()));
                ad.show(RewardedAdExampleActivity.this, (type, amount) -> {
                    Log.d("adrop", String.format("RewardedAd earn rewards / type: %d, amount: %d ", type, amount));
                    return null;
                });
            }

            @Override
            public void onAdFailedToReceive(@NotNull AdropRewardedAd ad, @NotNull AdropErrorCode errorCode) {
                Log.e("adrop", "Rewarded ad failed to load: " + ad.getUnitId());
                setError(errorCode);
            }

            @Override
            public void onAdImpression(@NotNull AdropRewardedAd ad) {
                Log.d("adrop", String.format("rewarded ad impression %s", ad.getUnitId()));
            }

            @Override
            public void onAdClicked(@NotNull AdropRewardedAd ad) {
                Log.d("adrop", String.format("rewarded ad click %s", ad.getUnitId()));
            }

            @Override
            public void onAdWillPresentFullScreen(@NotNull AdropRewardedAd ad) {
            }

            @Override
            public void onAdDidPresentFullScreen(@NotNull AdropRewardedAd ad) {
                tvErrorCode.setText(null);
                tvErrorDesc.setText(null);
            }

            @Override
            public void onAdWillDismissFullScreen(@NotNull AdropRewardedAd ad) {
            }

            @Override
            public void onAdDidDismissFullScreen(@NotNull AdropRewardedAd ad) {
                Log.d("adrop", String.format("rewarded ad dismiss screen %s", ad.getUnitId()));
            }

            @Override
            public void onAdFailedToShowFullScreen(@NotNull AdropRewardedAd ad, @NotNull AdropErrorCode errorCode) {
                setError(errorCode);
            }
        });
        
        rewardedAd.load();
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
        if (rewardedAd != null) {
            rewardedAd.destroy();
            rewardedAd = null;
        }
    }
}
