package com.example.admobmediationexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;

import java.util.Map;

import io.adrop.ads.Adrop;
import io.adrop.ads.mediation.admob.AdropAdRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        findViewById(R.id.banner_load_ad).setOnClickListener(v -> {
            AdView view = load();

            FrameLayout container = findViewById(R.id.banner_container);
            container.removeAllViews();
            container.addView(view);
        });
    }

    private void initialize() {
        Adrop.INSTANCE.initialize(getApplication(), false);

        MobileAds.initialize(this, initializationStatus -> {
            Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();

            for (String adapterClass : statusMap.keySet()) {
                AdapterStatus status = statusMap.get(adapterClass);
                Log.d("AdmobMediationExample", String.format("Adapter name: %s, Description: %s, Latency: %d", adapterClass, status.getDescription(), status.getLatency()));
            }
        });
    }

    private AdView load() {
        AdView view = new AdView(this);
        view.setAdSize(AdSize.BANNER);
        view.setAdUnitId("ca-app-pub-2926914265361170/2941298347");
        view.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d("AdmobMediationExample", String.format("admob failed to load %s", loadAdError));
            }

            @Override
            public void onAdLoaded() {
                Log.d("AdmobMediationExample", "admob receive ad");
            }
        });

        // build AdRequest if not set unit id in admob console
        AdRequest request = new AdropAdRequest.Builder().setUnitId("PUBLIC_TEST_UNIT_ID_375_80").build();
        view.loadAd(request);

        return view;
    }
}