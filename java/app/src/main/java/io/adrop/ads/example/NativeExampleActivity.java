package io.adrop.ads.example;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.adrop.ads.example.adapter.PostAdapter;
import io.adrop.ads.example.helper.AdLoader;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.nativeAd.AdropNativeAd;
import io.adrop.ads.nativeAd.AdropNativeAdListener;

import java.util.ArrayList;

public class NativeExampleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<AdropNativeAd> nativeAds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_example);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        for(AdropNativeAd ad : AdLoader.nativeAds) {
            if (ad.isLoaded()) {
                nativeAds.add(ad);
                recyclerView.setAdapter(new PostAdapter(nativeAds));
            }
        }
    }

    @Override
    protected void onDestroy() {
        for(AdropNativeAd ad : nativeAds) {
            ad.destroy();
            AdLoader.nativeAds.remove(ad);
        }
        super.onDestroy();
    }
}
