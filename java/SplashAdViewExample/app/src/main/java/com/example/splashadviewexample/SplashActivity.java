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

/**
 * Example of implementing a custom splash screen with Adrop Splash Ad.
 * This approach gives you more control over the splash screen behavior
 * compared to declaring it in AndroidManifest.xml.
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final Long displayDuration = 3_000L; // Duration to display the splash ad in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);

        // Initialize Adrop SDK (set to true for production)
        Adrop.initialize(getApplication(), false);

        FrameLayout adFrame = findViewById(R.id.ad_area);

        // Create and configure the splash ad view
        AdropSplashAdView splashAd = new AdropSplashAdView(this, "PUBLIC_TEST_UNIT_ID_SPLASH", 1000);
        adFrame.addView(splashAd);
        splashAd.setDisplayDuration(displayDuration);
        splashAd.setListener(new AdropSplashAdViewListener() {
            // Called when an ad is successfully loaded
            @Override
            public void onAdReceived(AdropSplashAdView ad) {
                Log.d("SplashActivity", String.format("onAdReceived: %s, creativeId: %s", ad.getUnitId(), ad.getCreativeId()));
            }

            // Called when an ad fails to load
            @Override
            public void onAdFailedToReceive(AdropSplashAdView ad, AdropErrorCode adropErrorCode) {
                Log.d("SplashActivity", String.format("onAdFailedToReceive: %s, errorCode: %s", ad.getUnitId(), adropErrorCode));
            }

            // Called when an ad impression is recorded
            @Override
            public void onAdImpression(AdropSplashAdView ad) {
                Log.d("SplashActivity", String.format("onAdImpression: %s", ad.getUnitId()));
            }

            // Called when the splash ad is closed (either by user or after duration)
            // impressed: true if the ad was displayed, false otherwise
            @Override
            public void onAdClose(AdropSplashAdView ad, boolean impressed) {
                Log.d("SplashActivity", String.format("onAdClose: %s, impressed: %b", ad.getUnitId(), impressed));
                startMainActivity(impressed);
            }
        });
        splashAd.load();
    }

    /**
     * Navigates to the main activity.
     * If the ad was impressed, navigates immediately.
     * Otherwise, waits for displayDuration before navigating.
     */
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
