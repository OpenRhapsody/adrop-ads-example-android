package io.adrop.ads.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import io.adrop.ads.banner.AdropBanner;
import io.adrop.ads.banner.AdropBannerListener;
import io.adrop.ads.banner.AdropQuestBanner;
import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.model.AdropQuestBannerFormat;

public class QuestBannerExampleActivity extends AppCompatActivity {

    TextView tvErrorCode;
    TextView tvErrorDesc;
    FrameLayout feedContainer;
    FrameLayout coverContainer;

    private String TEST_CHANNEL = "adrop";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_banner_example);

        tvErrorCode = findViewById(R.id.quest_banner_error_code);
        tvErrorDesc = findViewById(R.id.quest_banner_error_code_desc);
        feedContainer = findViewById(R.id.quest_banner_container_feed);
        coverContainer = findViewById(R.id.quest_banner_container_cover);

        loadQuestBanners();

        setInvalidQuestBanner();
    }

    private void loadQuestBanners() {
        AdropQuestBanner feedBanner = new AdropQuestBanner(this, TEST_CHANNEL, AdropQuestBannerFormat.FEED);
        feedBanner.setListener(new AdropBannerListener() {
            @Override
            public void onAdReceived(AdropBanner banner) {
                Log.d("adrop", "feed quest received");
                feedContainer.removeAllViews();
                feedContainer.addView(banner);
            }

            @Override
            public void onAdFailedToReceive(AdropBanner banner, AdropErrorCode adropErrorCode) {
                Log.d("adrop", "feed quest failed to receive, " + adropErrorCode);
            }

            @Override
            public void onAdClicked(AdropBanner adropBanner) {
                Log.d("adrop", "feed quest clicked");
            }

            @Override
            public void onAdImpression(AdropBanner adropBanner) {
                Log.d("adrop", "feed quest impressed");
            }
        });
        feedBanner.load();

        AdropQuestBanner coverBanner = new AdropQuestBanner(this, TEST_CHANNEL, AdropQuestBannerFormat.COVER);
        coverBanner.setListener(new AdropBannerListener() {
            @Override
            public void onAdReceived(AdropBanner banner) {
                Log.d("adrop", "cover quest received");
                coverContainer.removeAllViews();
                coverContainer.addView(banner);
            }

            @Override
            public void onAdFailedToReceive(AdropBanner banner, AdropErrorCode adropErrorCode) {
                Log.d("adrop", "cover quest failed to receive, " + adropErrorCode);
            }

            @Override
            public void onAdClicked(AdropBanner adropBanner) {
                Log.d("adrop", "cover quest clicked");
            }

            @Override
            public void onAdImpression(AdropBanner adropBanner) {
                Log.d("adrop", "cover quest impressed");
            }
        });
        coverBanner.load();
    }

    private void setInvalidQuestBanner() {
        findViewById(R.id.load_invalid).setOnClickListener(v -> {
            AdropQuestBanner invalidBanner = new AdropQuestBanner(this, "invalid_channel", AdropQuestBannerFormat.COVER);
            invalidBanner.setListener(new AdropBannerListener() {
                @Override
                public void onAdReceived(AdropBanner banner) {
                    Log.d("adrop", "cover quest received");
                }

                @Override
                public void onAdFailedToReceive(AdropBanner banner, AdropErrorCode errorCode) {
                    Log.d("adrop", "cover quest failed to receive, " + errorCode);
                    tvErrorCode.setText(errorCode.name());
                    tvErrorDesc.setText(ErrorUtils.descriptionOf(errorCode));
                }

                @Override
                public void onAdClicked(AdropBanner adropBanner) {
                    Log.d("adrop", "cover quest clicked");
                }

                @Override
                public void onAdImpression(AdropBanner adropBanner) {
                    Log.d("adrop", "cover quest impressed");
                }
            });
            invalidBanner.load();
        });
    }
}
