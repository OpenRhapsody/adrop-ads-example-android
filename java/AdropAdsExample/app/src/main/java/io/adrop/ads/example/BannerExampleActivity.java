package io.adrop.ads.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.adrop.ads.banner.AdropBanner;
import io.adrop.ads.banner.AdropBannerListener;
import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.model.AdropErrorCode;


public class BannerExampleActivity extends AppCompatActivity {

    private final String PUBLIC_TEST_UNIT_ID_320_80 = "PUBLIC_TEST_UNIT_ID_375_80";
    private final String INVALID_UNIT_ID = "INVALID_UNIT_ID";

    private TextView tvErrorCode;
    private TextView tvErrorDesc;
    private FrameLayout bannerContainer;

    private AdropBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_example);

        tvErrorCode = findViewById(R.id.banner_error_code);
        tvErrorDesc = findViewById(R.id.banner_error_code_desc);
        bannerContainer = findViewById(R.id.banner_container);

        findViewById(R.id.load).setOnClickListener(v -> load(PUBLIC_TEST_UNIT_ID_320_80));
        findViewById(R.id.load_invalid).setOnClickListener(v -> load(INVALID_UNIT_ID));
    }

    private void load(String unitId) {
        if (banner != null) {
            banner.destroy();
        }
        banner = new AdropBanner(this, unitId);
        banner.setListener(new AdropBannerListener() {
            @Override
            public void onAdReceived(AdropBanner receivedBanner) {
                Log.d("adrop", String.format("banner received: %s", receivedBanner.getUnitId()));
                bannerContainer.removeAllViews();
                bannerContainer.addView(receivedBanner);
                tvErrorDesc.setText(null);
                tvErrorCode.setText(null);
            }

            @Override
            public void onAdImpression(AdropBanner adropBanner) {
                Log.d("adrop", String.format("banner impressed: %s", adropBanner.getUnitId()));
            }

            @Override
            public void onAdClicked(AdropBanner clickedBanner) {
                Log.d("adrop", String.format("banner clicked: %s ", clickedBanner.getUnitId()));
            }

            @Override
            public void onAdFailedToReceive(AdropBanner failedBanner, AdropErrorCode errorCode) {
                Log.d("adrop", String.format("banner failed to receive: %s, %s", failedBanner.getUnitId(), errorCode));
                tvErrorCode.setText(errorCode.name());
                tvErrorDesc.setText(ErrorUtils.descriptionOf(errorCode));
            }
        });
        banner.load();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (banner != null) {
            banner.destroy();
            banner = null;
        }
    }
}
