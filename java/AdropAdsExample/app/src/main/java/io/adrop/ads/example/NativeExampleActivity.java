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
    String CONTEXT_ID = ""; // Optional context ID for the banner, can be left empty
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

    /**
     * Loads a native ad and sets up the listener for ad events.
     * The native ad is displayed in a RecyclerView using PostAdapter.
     */
    private void load() {
        nativeAd = new AdropNativeAd(this, NATIVE_UNIT_ID);
        nativeAd.setListener(new AdropNativeAdListener() {
            // Called when an ad is successfully loaded and ready to be displayed
            @Override
            public void onAdReceived(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad received");
                recyclerView.setAdapter(new PostAdapter(adropNativeAd));
                progressBar.setVisibility(View.GONE);
            }

            // Called when the user clicks on the ad
            @Override
            public void onAdClick(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad clicked");
            }

            // Called when an ad fails to load
            @Override
            public void onAdFailedToReceive(AdropNativeAd adropNativeAd, AdropErrorCode adropErrorCode) {
                Log.d("adrop", String.format("native ad failed to receive, %s", adropErrorCode));
                Toast.makeText(NativeExampleActivity.this, ErrorUtils.descriptionOf(adropErrorCode), Toast.LENGTH_SHORT).show();
            }

            // Called when an ad impression is recorded
            @Override
            public void onAdImpression(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad impression");
            }
        });

        nativeAd.load();
    }

    @Override
    protected void onDestroy() {
        // Clean up the native ad to prevent memory leaks
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }
}
