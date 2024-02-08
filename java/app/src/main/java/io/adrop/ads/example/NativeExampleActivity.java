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

public class NativeExampleActivity extends AppCompatActivity {

    private String PUBLIC_TEST_UNIT_ID_NATIVE = "PUBLIC_TEST_UNIT_ID_NATIVE";

    private String INVALID_UNIT_ID = "INVALID_UNIT_ID";

    TextView tvErrorCode;
    TextView tvErrorDesc;
    Button btnReset;
    Button btnResetInvalid;
    FrameLayout postView;

    RecyclerView recyclerView;
    AdropNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_example);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        for(AdropNativeAd ad : AdLoader.nativeAds) {
            if (ad.isLoaded()) {
                nativeAd = ad;
                recyclerView.setAdapter(new PostAdapter(nativeAd));
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
            AdLoader.nativeAds.remove(nativeAd);
        }
        super.onDestroy();
    }
}
