# Adrop Ads Example - Android

Example applications demonstrating how to integrate [Adrop Ads SDK](https://adrop.io) in Android.

Language: English | [한국어](./README.ko)

## Getting Started

- [Adrop Console](https://console.adrop.io) - Register your app and issue ad unit IDs
- [Adrop Developer Docs](https://docs.adrop.io/sdk/android) - SDK integration and advanced features
- [Test Ad Unit IDs](https://docs.adrop.io/sdk#test-environment) - Use test IDs during development

## Examples

### Adrop Ads

|  | Java | Kotlin |
|--|------|--------|
| Banner | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/BannerExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/BannerExampleActivity.kt) |
| Interstitial | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/InterstitialExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/InterstitialExampleActivity.kt) |
| Rewarded | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/RewardedAdExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/RewardedAdExampleActivity.kt) |
| Native | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/NativeExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/NativeExampleActivity.kt) |
| Popup | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PopupExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PopupExampleActivity.kt) |


### Splash

|  | Java | Kotlin |
|--|------|--------|
| Manifest Declaration | [Manifest](java/AdropAdsExample/app/src/main/AndroidManifest.xml) | [Manifest](kotlin/AdropAdsExample/app/src/main/AndroidManifest.xml) |
| Custom Activity | [Java](java/SplashAdViewExample/app/src/main/java/com/example/splashadviewexample/SplashActivity.java) | [Kotlin](kotlin/SplashAdViewExample/app/src/main/java/com/example/splashadviewexample/SplashActivity.kt) |

Manifest Declaration requires additional values in `res/values/`. See [adrop.xml](java/AdropAdsExample/app/src/main/res/values/adrop.xml) for example.

### Targeting

|  | Java | Kotlin |
|--|------|--------|
| Property & Event | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PropertyActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PropertyActivity.kt) |


## How to Run

### 1. Clone the repository

```bash
git clone https://github.com/OpenRhapsody/adrop-ads-example-android.git
```

### 2. Open in Android Studio

Open one of the example projects:
- `java/AdropAdsExample` or `kotlin/AdropAdsExample`
- `java/SplashAdViewExample` or `kotlin/SplashAdViewExample`

### 3. Add configuration file

Download `adrop_service.json` from [Adrop Console](https://adrop.io) and place it in:
```
app/src/main/assets/adrop_service.json
```

### 4. Build and run

Build and run on your device or emulator.
