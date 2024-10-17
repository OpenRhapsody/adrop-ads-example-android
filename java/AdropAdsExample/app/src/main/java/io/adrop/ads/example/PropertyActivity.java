package io.adrop.ads.example;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.adrop.ads.metrics.AdropEventParam;
import io.adrop.ads.metrics.AdropMetrics;
import io.adrop.ads.model.AdropKey;
import io.adrop.ads.model.AdropValue;

public class PropertyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        findViewById(R.id.set_property).setOnClickListener(v -> sendProperty());
        findViewById(R.id.custom_event).setOnClickListener(v -> sendEvent());
        findViewById(R.id.custom_event_name).setOnClickListener(v -> sendEventNameOnly());
    }

    private void sendProperty() {
        String key = ((EditText) findViewById(R.id.key_edit)).getText().toString();
        String value = ((EditText) findViewById(R.id.value_edit)).getText().toString();

        if (value.isEmpty()) {
            AdropMetrics.setProperty(key, null);
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            AdropMetrics.setProperty(key, Boolean.parseBoolean(value));
        } else {
            try {
                AdropMetrics.setProperty(key, Long.parseLong(value));
            } catch (NumberFormatException e) {
                try {
                    AdropMetrics.setProperty(key, Integer.parseInt(value));
                } catch (NumberFormatException e1) {
                    try {
                        AdropMetrics.setProperty(key, Double.parseDouble(value));
                    } catch (NumberFormatException e2) {
                        AdropMetrics.setProperty(key, value);
                    }
                }
            }
        }
    }

    private void sendEvent() {
        String name = "event_name";
        AdropEventParam params = new AdropEventParam.Builder()
                .putInt("data_key_1", 1)
                .putFloat("data_key_2", 1.2f)
                .putBoolean("data_key_3", true)
                .putString("data_key_4", "value_text")
                .putLong("data_key_5", 100L)
                .build();

        Log.d("Adrop", String.format("Send Custom Event - name: %s, params: %s", name, params));
        AdropMetrics.logEvent(name, params);
    }

    private void sendEventNameOnly() {
        String name = "event_name";
        Log.d("Adrop", String.format("Send Custom Event - name: %s", name));
        AdropMetrics.logEvent(name, null);
    }
}
