package io.adrop.ads.example.helper;

import android.content.Context;
import android.util.Log;
import io.adrop.ads.model.AdropErrorCode;
import io.adrop.ads.nativeAd.AdropNativeAd;
import io.adrop.ads.nativeAd.AdropNativeAdListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdLoader {
    static String BANNER_UNIT_ID = "PUBLIC_TEST_UNIT_ID_320_100";
    static String INTERSTITIAL_UNIT_ID = "PUBLIC_TEST_UNIT_ID_INTERSTITIAL";
    static String REWARDED_UNIT_ID = "PUBLIC_TEST_UNIT_ID_REWARDED";
    static String NATIVE_UNIT_ID = "PUBLIC_TEST_UNIT_ID_NATIVE";
    static ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static ArrayList<AdropNativeAd> banners = new ArrayList<>();
    public static ArrayList<AdropNativeAd> interstitialAds = new ArrayList<>();
    public static ArrayList<AdropNativeAd> rewardedAds = new ArrayList<>();
    public static ArrayList<AdropNativeAd> nativeAds = new ArrayList<>();

    public static void fetchNativeAd(Context context) {
        AdropNativeAd nativeAd = new AdropNativeAd(context, NATIVE_UNIT_ID);
        nativeAd.setListener(new AdropNativeAdListener() {
            @Override
            public void onAdReceived(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad received");
                nativeAds.add(adropNativeAd);
            }

            @Override
            public void onAdClick(AdropNativeAd adropNativeAd) {
                Log.d("adrop", "native ad clicked");
            }

            @Override
            public void onAdFailedToReceive(AdropNativeAd adropNativeAd, AdropErrorCode adropErrorCode) {
                Log.d("adrop", "native ad failed to receive, " + adropErrorCode);
            }
        });
        nativeAd.load();
    }
}
