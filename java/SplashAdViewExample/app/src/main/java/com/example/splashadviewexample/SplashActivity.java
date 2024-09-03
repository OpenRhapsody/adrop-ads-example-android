package com.example.splashadviewexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import io.adrop.ads.Adrop;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.splash.AdropSplashAdView;
import io.adrop.ads.splash.AdropSplashAdViewListener;

import java.util.Timer;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final Long displayDuration = 3_000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        Adrop.INSTANCE.initialize(getApplication(), false);

        FrameLayout adFrame = findViewById(R.id.ad_area);

        AdropSplashAdView splashAd = new AdropSplashAdView(this, "PUBLIC_TEST_UNIT_ID_SPLASH");
        adFrame.addView(splashAd);
        splashAd.setDisplayDuration(displayDuration);
        splashAd.setListener(new AdropSplashAdViewListener() {
            @Override
            public void onAdReceived(AdropSplashAdView ad) {
                Log.d("SplashActivity", String.format("onAdReceived: %s, creativeId: %s", ad.getUnitId(), ad.getCreativeId()));
            }

            @Override
            public void onAdFailedToReceive(AdropSplashAdView ad, AdropErrorCode adropErrorCode) {
                Log.d("SplashActivity", String.format("onAdFailedToReceive: %s, errorCode: %s", ad.getUnitId(), adropErrorCode));
            }

            @Override
            public void onAdImpression(AdropSplashAdView ad) {
                Log.d("SplashActivity", String.format("onAdImpression: %s", ad.getUnitId()));
            }

            @Override
            public void onAdClose(AdropSplashAdView ad, boolean impressed) {
                Log.d("SplashActivity", String.format("onAdClose: %s, impressed: %b", ad.getUnitId(), impressed));
                startMainActivity(impressed);
            }
        });
        splashAd.load();
    }

    private void startMainActivity(boolean impressed) {
        Timer timer = new Timer();
        timer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, impressed ? 0 : displayDuration);

    }
}
