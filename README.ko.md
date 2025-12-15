# Adrop Ads Example - Android

Android에서 [Adrop Ads SDK](https://adrop.io)를 연동하는 예제 앱입니다.

Language: [English](./README.md) | 한국어

## 시작하기

- [Adrop 개발자 문서](https://help.adrop.io/adcontrol/developer-guide/adrop-sdk/android-sdk) - SDK 연동 및 고급 기능
- [Adrop 콘솔](https://adrop.io) - 앱 등록 및 광고 단위 ID 발급
- [테스트 광고 단위 ID](https://help.adrop.io/adcontrol/developer-guide/test-environment#test-unit-id) - 개발 중 테스트용 ID

## 예제

### Adrop Ads

|  | Java | Kotlin |
|--|------|--------|
| 배너 | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/BannerExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/BannerExampleActivity.kt) |
| 전면 | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/InterstitialExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/InterstitialExampleActivity.kt) |
| 보상형 | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/RewardedAdExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/RewardedAdExampleActivity.kt) |
| 네이티브 | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/NativeExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/NativeExampleActivity.kt) |
| 팝업 | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PopupExampleActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PopupExampleActivity.kt) |

### 스플래시

|  | Java | Kotlin |
|--|------|--------|
| Manifest 선언 | [Manifest](java/AdropAdsExample/app/src/main/AndroidManifest.xml) | [Manifest](kotlin/AdropAdsExample/app/src/main/AndroidManifest.xml) |
| 커스텀 액티비티 | [Java](java/SplashAdViewExample/app/src/main/java/com/example/splashadviewexample/SplashActivity.java) | [Kotlin](kotlin/SplashAdViewExample/app/src/main/java/com/example/splashadviewexample/SplashActivity.kt) |

Manifest 선언 방식은 `res/values/`에 추가 설정이 필요합니다. [adrop.xml](java/AdropAdsExample/app/src/main/res/values/adrop.xml) 예제를 참고하세요.

### 타겟팅

|  | Java | Kotlin |
|--|------|--------|
| 속성 & 이벤트 | [Java](java/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PropertyActivity.java) | [Kotlin](kotlin/AdropAdsExample/app/src/main/java/io/adrop/ads/example/PropertyActivity.kt) |


## 실행 방법

### 1. 저장소 클론

```bash
git clone https://github.com/OpenRhapsody/adrop-ads-example-android.git
```

### 2. Android Studio에서 열기

예제 프로젝트 중 하나를 엽니다:
- `java/AdropAdsExample` 또는 `kotlin/AdropAdsExample`
- `java/SplashAdViewExample` 또는 `kotlin/SplashAdViewExample`

### 3. 설정 파일 추가

[Adrop 콘솔](https://adrop.io)에서 `adrop_service.json`을 다운로드하고 다음 경로에 배치합니다:
```
app/src/main/assets/adrop_service.json
```

### 4. 빌드 및 실행

디바이스 또는 에뮬레이터에서 빌드하고 실행합니다.

