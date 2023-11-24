package io.adrop.ads.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.adrop.ads.Adrop;
import io.adrop.ads.banner.AdropBanner;
import io.adrop.ads.banner.AdropBannerListener;
import io.adrop.ads.model.AdropErrorCode;

public class MainActivity extends AppCompatActivity {

    AdropBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Adrop.INSTANCE.initialize(getApplication(), false  /* if release then true */);

        banner = findViewById(R.id.adrop_banner_view);
        banner.setListener(new AdropBannerListener() {
            @Override
            public void onAdReceived(@NonNull AdropBanner banner) {
                Log.d("Adrop", banner.getUnitId() + ", onAdReceived");
            }

            @Override
            public void onAdClicked(@NonNull AdropBanner banner) {
                Log.d("Adrop", banner.getUnitId() + ", onAdClicked");
            }

            @Override
            public void onAdFailedToReceive(@NonNull AdropBanner banner, @NonNull AdropErrorCode error) {
                Log.d("Adrop", banner.getUnitId() + ", onAdFailedToReceive " + error);
            }
        });

        findViewById(R.id.display_button).setOnClickListener(v -> banner.load());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (banner != null) {
            banner.destroy();
        }
    }
}
