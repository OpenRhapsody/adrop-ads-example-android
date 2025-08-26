package io.adrop.ads.example

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HelpCenterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_center)
        
        val helpContent = findViewById<TextView>(R.id.help_content)
        helpContent.text = getHelpContent()
    }
    
    private fun getHelpContent(): String {
        return """
# Adrop 광고 SDK 도움말

## 1. App Key 구성

App Key는 Adrop 콘솔에서 앱을 등록할 때 생성됩니다.

### 설정 방법:
1. Adrop 콘솔 (https://adrop.io) 로그인
2. 새 앱 등록 또는 기존 앱 선택
3. 앱 설정에서 App Key 확인
4. `adrop_service.json` 파일을 다운로드하여 `assets/` 폴더에 배치

## 2. Unit ID 세팅

Unit ID는 각 광고 형태별로 생성해야 합니다.

### 광고 유형별 Unit ID:
- **배너 광고**: 320x50, 375x80, 320x100 등 다양한 사이즈
- **전면 광고**: 전체 화면을 덮는 광고
- **팝업 광고**: 하단/중앙 표시되는 팝업형 광고
- **네이티브 광고**: 앱 콘텐츠와 자연스럽게 융화되는 광고
- **스플래시 광고**: 앱 시작 시 표시되는 광고
- **보상형 광고**: 사용자가 광고를 시청하고 보상을 받는 광고

### 테스트용 Unit ID:
```
PUBLIC_TEST_UNIT_ID_320_50
PUBLIC_TEST_UNIT_ID_375_80
PUBLIC_TEST_UNIT_ID_320_100
PUBLIC_TEST_UNIT_ID_CAROUSEL
PUBLIC_TEST_UNIT_ID_INTERSTITIAL
PUBLIC_TEST_UNIT_ID_REWARDED
PUBLIC_TEST_UNIT_ID_NATIVE
PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM
PUBLIC_TEST_UNIT_ID_POPUP_CENTER
PUBLIC_TEST_UNIT_ID_SPLASH
```

### 동영상 광고 테스트용 Unit ID:
```
PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_16_9
PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_9_16
PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_16_9
PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM_VIDEO_9_16
PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_16_9
PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_9_16
PUBLIC_TEST_UNIT_ID_NATIVE_VIDEO_16_9
PUBLIC_TEST_UNIT_ID_NATIVE_VIDEO_9_16
```

## 3. 예시 코드

### 3.1 SDK 초기화
```kotlin
import io.adrop.ads.Adrop

// Application Context 사용 권장
// production: 실서비스시 true, 테스트시 false
// targetCountries: 특정 국가 타겟팅시 ISO 3166 alpha-2 국가 코드 배열
Adrop.initialize(application, production = false, arrayOf())
```

### 3.2 배너 광고 (권장: 미리 로드)
```kotlin
// 1. 미리 로드
private var bannerAd: AdropBanner? = null

private fun preloadBannerAd() {
    bannerAd = AdropBanner(this, "YOUR_UNIT_ID", "").apply {
        listener = object : AdropBannerListener {
            override fun onAdReceived(banner: AdropBanner) {
                // 광고 로드 완료
            }
            override fun onAdFailedToReceive(banner: AdropBanner, errorCode: AdropErrorCode) {
                // 광고 로드 실패
            }
            override fun onAdImpression(banner: AdropBanner) {
                // 광고 노출
            }
            override fun onAdClicked(banner: AdropBanner) {
                // 광고 클릭
            }
        }
        load() // 미리 로드 실행
    }
}

// 2. 표시
private fun showBannerAd() {
    bannerAd?.let { banner ->
        bannerContainer.removeAllViews()
        bannerContainer.addView(banner)
    }
}
```

### 3.3 전면 광고 (권장: 미리 로드)
```kotlin
private var interstitialAd: AdropInterstitial? = null

private fun preloadInterstitialAd() {
    interstitialAd = AdropInterstitial(this, "YOUR_UNIT_ID", "").apply {
        listener = object : AdropInterstitialListener {
            override fun onAdReceived(interstitial: AdropInterstitial) {
                // 광고 로드 완료
            }
            override fun onAdFailedToReceive(interstitial: AdropInterstitial, errorCode: AdropErrorCode) {
                // 광고 로드 실패
            }
            // 기타 콜백...
        }
        load() // 미리 로드 실행
    }
}

private fun showInterstitialAd() {
    interstitialAd?.show()
}
```

### 3.4 보상형 광고 (권장: 미리 로드)
```kotlin
private var rewardedAd: AdropRewardedAd? = null

private fun preloadRewardedAd() {
    rewardedAd = AdropRewardedAd(this, "YOUR_UNIT_ID", "").apply {
        listener = object : AdropRewardedAdListener {
            override fun onAdReceived(rewardedAd: AdropRewardedAd) {
                // 광고 로드 완료
            }
            override fun onUserEarnedReward(rewardedAd: AdropRewardedAd, amount: Int, currencyType: String) {
                // 보상 지급
            }
            // 기타 콜백...
        }
        load() // 미리 로드 실행
    }
}

private fun showRewardedAd() {
    rewardedAd?.show()
}
```

## 4. 유의사항

### 4.1 필수 사항
- **미리 로드 권장**: 광고를 미리 로드해두면 사용자 경험이 향상됩니다.
- **적절한 타이밍**: 앱 시작시나 사용자가 광고를 볼 가능성이 높은 시점에 미리 로드하세요.
- **메모리 관리**: 사용하지 않는 광고 객체는 destroy()를 호출해주세요.

### 4.2 생명주기 관리
```kotlin
override fun onDestroy() {
    super.onDestroy()
    bannerAd?.destroy()
    interstitialAd?.destroy()
    rewardedAd?.destroy()
}
```

### 4.3 네트워크 상태
- 인터넷 연결이 필요합니다.
- 광고 로드 실패시 적절한 에러 처리를 구현하세요.

### 4.4 테스트와 실서비스
- 개발/테스트시: `production = false`
- 실서비스시: `production = true`
- 실제 Unit ID로 교체 필요

### 4.5 성능 최적화
- 동일한 광고를 반복 로드하지 마세요.
- 화면에서 벗어난 광고는 적절히 정리하세요.
- 앱 백그라운드 진입시 광고 로드를 중단하세요.

## 문의사항
기술 지원: https://help.adrop.io
        """.trimIndent()
    }
}