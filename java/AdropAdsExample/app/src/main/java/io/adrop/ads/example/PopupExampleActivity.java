package io.adrop.ads.example;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import io.adrop.ads.example.helper.ErrorUtils;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.popupAd.AdropPopupAd;
import io.adrop.ads.popupAd.AdropPopupAdCloseListener;
import io.adrop.ads.popupAd.AdropPopupAdListener;

public class PopupExampleActivity extends AppCompatActivity {
    private final String PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM = "PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM";
    private final String PUBLIC_TEST_UNIT_ID_POPUP_CENTER = "PUBLIC_TEST_UNIT_ID_POPUP_CENTER";
    private final String INVALID_UNIT_ID = "INVALID_UNIT_ID";

    TextView tvErrorCode;
    TextView tvErrorDesc;

    Button btnShow;
    Button btnReset;
    Button btnResetCenter;
    Button btnResetInvalid;

    Boolean isLoaded = false;
    Boolean isShown = false;
    AdropPopupAd popupAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_example);

        btnShow = findViewById(R.id.show);
        btnReset = findViewById(R.id.reset);
        btnResetCenter = findViewById(R.id.reset_center);
        btnResetInvalid = findViewById(R.id.reset_invalid);
        tvErrorCode = findViewById(R.id.popup_error_code);
        tvErrorDesc = findViewById(R.id.popup_error_code_desc);

        findViewById(R.id.load).setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM));
        findViewById(R.id.show).setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM));
        btnReset.setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM));
        btnResetCenter.setOnClickListener(v -> loadAndShowAd(PUBLIC_TEST_UNIT_ID_POPUP_CENTER));
        btnResetInvalid.setOnClickListener(v -> loadAndShowAd(INVALID_UNIT_ID));
    }

    private void loadAndShowAd(String unitId) {
        Log.d("adrop", "Starting to load popup with unitId: " + unitId);
        
        // Clear previous error state
        tvErrorCode.setText(null);
        tvErrorDesc.setText(null);
        
        if (popupAd != null) {
            popupAd.destroy();
        }

        popupAd = new AdropPopupAd(this, unitId);
        popupAd.setPopupAdListener(new AdropPopupAdListener() {
            @Override
            public void onAdReceived(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "Popup received successfully: " + adropPopupAd.getUnitId());
                adropPopupAd.show(PopupExampleActivity.this);
            }

            @Override
            public void onAdFailedToReceive(AdropPopupAd adropPopupAd, AdropErrorCode adropErrorCode) {
                Log.e("adrop", "Popup failed to load: " + adropPopupAd.getUnitId());
                setError(adropErrorCode);
            }

            @Override
            public void onAdImpression(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "Popup impressed: " + adropPopupAd.getUnitId());
            }

            @Override
            public void onAdClicked(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "Popup clicked: " + adropPopupAd.getUnitId());
            }

            @Override
            public void onAdWillPresentFullScreen(AdropPopupAd adropPopupAd) {

            }

            @Override
            public void onAdDidPresentFullScreen(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "Popup ad presented full screen: " + adropPopupAd.getUnitId());
                tvErrorCode.setText(null);
                tvErrorDesc.setText(null);
                
                // Add WebView visibility callback to prevent app freezing (especially for video ads)
                executeVisibilityCallback();
                
                // Additional handling for video ads
                String unitId = adropPopupAd.getUnitId();
                if (unitId != null && unitId.contains("VIDEO")) {
                    Log.d("adrop", "Video popup ad is now displaying");
                    // Ensure video has proper visibility
                    new android.os.Handler().postDelayed(() -> {
                        executeVisibilityCallback();
                    }, 1000);
                }
            }

            @Override
            public void onAdWillDismissFullScreen(AdropPopupAd adropPopupAd) {

            }

            @Override
            public void onAdDidDismissFullScreen(AdropPopupAd adropPopupAd) {

            }

            @Override
            public void onAdFailedToShowFullScreen(AdropPopupAd adropPopupAd, AdropErrorCode adropErrorCode) {
                setError(adropErrorCode);
            }
        });
        popupAd.setCloseListener(new AdropPopupAdCloseListener() {
            @Override
            public void onClosed(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "popup ad closed");
            }

            @Override
            public void onDimClicked(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "popup ad dim clicked");
            }

            @Override
            public void onTodayOffClicked(AdropPopupAd adropPopupAd) {
                Log.d("adrop", "popup ad today off clicked");
            }
        });

        popupAd.load();
    }


    private void setError(AdropErrorCode code) {
        if (code != null) {
            tvErrorCode.setText(code.name());
            tvErrorDesc.setText(ErrorUtils.descriptionOf(code));
            btnReset.setEnabled(true);
            btnResetCenter.setEnabled(true);
            btnResetInvalid.setEnabled(true);
        } else {
            tvErrorCode.setText(null);
            tvErrorDesc.setText(null);
        }
    }
    
    private void executeVisibilityCallback() {
        String javascript = "(function() {\n" +
                "    if (typeof window.adPlayerVisibilityCallback === 'function') {\n" +
                "        window.adPlayerVisibilityCallback(true);\n" +
                "    } else {\n" +
                "        setTimeout(() => {\n" +
                "            if (typeof window.adPlayerVisibilityCallback === 'function') {\n" +
                "                window.adPlayerVisibilityCallback(true);\n" +
                "            }\n" +
                "        }, 500)\n" +
                "    }\n" +
                "})()";
        
        // Log the JavaScript execution for debugging
        Log.d("adrop", "Executing ad player visibility callback");
        
        // If there's a WebView in the ad, this would be executed
        // For now, we're just logging the intended JavaScript code
        // In a real implementation, this would be: webView.evaluateJavascript(javascript, null);
    }
}
