package io.adrop.ads.example;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import io.adrop.ads.Adrop;
import io.adrop.ads.example.helper.AdLoader;
import io.adrop.ads.nativeAd.AdropNativeAd;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.initialize).setOnClickListener(v -> Adrop.INSTANCE.initialize(getApplication(), false));
        findViewById(R.id.banner_example).setOnClickListener(v -> start(BannerExampleActivity.class));
        findViewById(R.id.interstitial_example).setOnClickListener(v -> start(InterstitialExampleActivity.class));
        findViewById(R.id.rewarded_example).setOnClickListener(v -> start(RewardedAdExampleActivity.class));
        findViewById(R.id.native_load).setOnClickListener (v -> AdLoader.fetchNativeAd(this) );
        findViewById(R.id.native_example).setOnClickListener(v -> {

            for(AdropNativeAd ad : AdLoader.nativeAds) {
                if (ad.isLoaded()) {
                    start(NativeExampleActivity.class);
                }
            }
        });
    }

    private void start(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
