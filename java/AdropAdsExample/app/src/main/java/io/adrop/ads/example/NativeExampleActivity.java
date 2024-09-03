package io.adrop.ads.example;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.adrop.ads.example.adapter.PostAdapter;
import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.nativeAd.AdropNativeAd;
import io.adrop.ads.nativeAd.AdropNativeAdListener;

public class NativeExampleActivity extends AppCompatActivity {

    String NATIVE_UNIT_ID = "PUBLIC_TEST_UNIT_ID_NATIVE";
    ProgressBar progressBar;
    RecyclerView recyclerView;
    AdropNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_example);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        load();
    }

    private void load() {
        nativeAd = new AdropNativeAd(this, NATIVE_UNIT_ID);
        nativeAd.setListener(new AdropNativeAdListener() {
            @Override
            public void onAdReceived(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad received");
                recyclerView.setAdapter(new PostAdapter(adropNativeAd));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAdClick(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad clicked");
            }

            @Override
            public void onAdFailedToReceive(AdropNativeAd adropNativeAd, AdropErrorCode adropErrorCode) {
                Log.d("adrop", String.format("native ad failed to receive, %s", adropErrorCode));
                Toast.makeText(NativeExampleActivity.this, ErrorUtils.descriptionOf(adropErrorCode), Toast.LENGTH_SHORT).show();
            }
        });

        nativeAd.load();
    }

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }
}
