package io.adrop.ads.example;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import io.adrop.ads.Adrop;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Adrop.initialize(getApplication(), false);

        setButtons();
    }

    private void setButtons() {
        findViewById(R.id.banner_example).setOnClickListener(v -> start(BannerExampleActivity.class));
        findViewById(R.id.quest_banner_example).setOnClickListener(v -> start(QuestBannerExampleActivity.class));
        findViewById(R.id.interstitial_example).setOnClickListener(v -> start(InterstitialExampleActivity.class));
        findViewById(R.id.rewarded_example).setOnClickListener(v -> start(RewardedAdExampleActivity.class));
        findViewById(R.id.native_example).setOnClickListener(v -> start(NativeExampleActivity.class));
        findViewById(R.id.property_example).setOnClickListener(v -> start(PropertyActivity.class));
        findViewById(R.id.popup_example).setOnClickListener(v -> start(PopupExampleActivity.class));
    }

    private void start(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
