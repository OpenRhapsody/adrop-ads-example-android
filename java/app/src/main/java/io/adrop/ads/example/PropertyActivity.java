package io.adrop.ads.example;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.adrop.ads.metrics.AdropMetrics;
import io.adrop.ads.model.AdropKey;
import io.adrop.ads.model.AdropValue;

public class PropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        findViewById(R.id.male).setOnClickListener(v -> gender(AdropValue.AdropGender.MALE));
        findViewById(R.id.female).setOnClickListener(v -> gender(AdropValue.AdropGender.FEMALE));
        findViewById(R.id.other).setOnClickListener(v -> gender(AdropValue.AdropGender.OTHER));
        findViewById(R.id.g_unknown).setOnClickListener(v -> gender(AdropValue.UNKNOWN));

        findViewById(R.id.fixed_18).setOnClickListener(v -> age(18));
        findViewById(R.id.fixed_35).setOnClickListener(v -> age(35));
        findViewById(R.id.fixed_47).setOnClickListener(v -> age(47));

        findViewById(R.id.d2010111).setOnClickListener(v -> birth("2010111"));
        findViewById(R.id.d2005).setOnClickListener(v -> birth("2005"));
        findViewById(R.id.d199101).setOnClickListener(v -> birth("199101"));
    }

    private void gender(String value) {
        AdropMetrics.INSTANCE.setProperty(AdropKey.GENDER, value);
    }

    private void age(int value) {
        AdropMetrics.INSTANCE.setProperty(AdropKey.AGE, String.valueOf(value));
    }

    private void birth(String value) {
        AdropMetrics.INSTANCE.setProperty(AdropKey.BIRTH, value);
    }
}
