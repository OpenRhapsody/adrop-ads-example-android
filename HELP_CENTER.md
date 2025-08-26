# Adrop 광고 SDK 개발자 가이드

## 📱 개요
Adrop 광고 SDK는 Android 앱에 다양한 형태의 광고를 통합할 수 있는 강력한 도구입니다. 이 가이드를 통해 SDK 설정부터 각 광고 유형의 구현까지 완벽하게 이해할 수 있습니다.

## 🚀 빠른 시작

### 1단계: SDK 설정
```gradle
dependencies {
    implementation 'io.adrop.ads:adrop-ads:1.4.0'
}
```

### 2단계: App Key 설정
1. [Adrop 콘솔](https://adrop.io)에서 앱 등록
2. `adrop_service.json` 다운로드
3. `app/src/main/assets/` 폴더에 배치

### 3단계: SDK 초기화
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // SDK 초기화 (Application Context 필수)
        Adrop.initialize(application, production = false, arrayOf())
    }
}
```

## 🎯 광고 유형별 구현 가이드

### 배너 광고 (Banner Ads)
```kotlin
class BannerActivity : AppCompatActivity() {
    private var bannerAd: AdropBanner? = null
    
    private fun loadBannerAd() {
        bannerAd = AdropBanner(this, "YOUR_UNIT_ID", "").apply {
            listener = object : AdropBannerListener {
                override fun onAdReceived(banner: AdropBanner) {
                    // 광고 로드 완료
                    findViewById<FrameLayout>(R.id.banner_container).addView(banner)
                }
                
                override fun onAdFailedToReceive(banner: AdropBanner, errorCode: AdropErrorCode) {
                    Log.e("adrop", "배너 광고 로드 실패: $errorCode")
                }
            }
            load()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        bannerAd?.destroy()
    }
}
```

**지원 사이즈:**
- 320x50, 375x80, 320x100 (일반 배너)
- 16:9, 9:16 비율 (동영상 배너)
- 캐러셀 배너

### 전면 광고 (Interstitial Ads)
```kotlin
class InterstitialActivity : AppCompatActivity() {
    private var interstitialAd: AdropInterstitial? = null
    
    private fun loadInterstitialAd() {
        interstitialAd = AdropInterstitial(this, "YOUR_UNIT_ID", "").apply {
            listener = object : AdropInterstitialListener {
                override fun onAdReceived(interstitial: AdropInterstitial) {
                    // 로드 완료, show() 호출 가능
                }
                
                override fun onAdOpened(interstitial: AdropInterstitial) {
                    // 광고 화면 열림
                }
                
                override fun onAdClosed(interstitial: AdropInterstitial) {
                    // 광고 화면 닫힘, 새 광고 로드 권장
                    loadInterstitialAd()
                }
            }
            load()
        }
    }
    
    private fun showAd() {
        interstitialAd?.show()
    }
}
```

### 보상형 광고 (Rewarded Video)
```kotlin
class RewardedAdActivity : AppCompatActivity() {
    private var rewardedAd: AdropRewardedAd? = null
    
    private fun loadRewardedAd() {
        rewardedAd = AdropRewardedAd(this, "YOUR_UNIT_ID", "").apply {
            listener = object : AdropRewardedAdListener {
                override fun onAdReceived(rewardedAd: AdropRewardedAd) {
                    // 로드 완료
                }
                
                override fun onUserEarnedReward(rewardedAd: AdropRewardedAd, amount: Int, currencyType: String) {
                    // 보상 지급 - 이 시점에서 실제 보상 처리
                    giveRewardToUser(amount, currencyType)
                }
                
                override fun onAdClosed(rewardedAd: AdropRewardedAd) {
                    // 광고 종료, 새 광고 로드
                    loadRewardedAd()
                }
            }
            load()
        }
    }
    
    private fun giveRewardToUser(amount: Int, currencyType: String) {
        when (currencyType) {
            "COIN" -> addCoinsToUser(amount)
            "POINT" -> addPointsToUser(amount)
        }
    }
}
```

### 네이티브 광고 (Native Ads)
```kotlin
class NativeAdActivity : AppCompatActivity() {
    private var nativeAd: AdropNativeAd? = null
    
    private fun loadNativeAd() {
        nativeAd = AdropNativeAd(this, "YOUR_UNIT_ID", "").apply {
            listener = object : AdropNativeAdListener {
                override fun onAdReceived(ad: AdropNativeAd) {
                    // RecyclerView 어댑터에 네이티브 광고 전달
                    setupRecyclerViewWithAd(ad)
                }
                
                override fun onAdClick(ad: AdropNativeAd) {
                    // 광고 클릭 처리
                }
            }
            load()
        }
    }
    
    private fun setupRecyclerViewWithAd(ad: AdropNativeAd) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = YourAdapterWithNativeAd(ad)
    }
}
```

**지원 형태:**
- 일반 네이티브 광고
- 16:9, 9:16 비율 동영상 네이티브

### 팝업 광고 (Popup Ads)
```kotlin
class PopupAdActivity : AppCompatActivity() {
    
    private fun loadAndShowPopupAd() {
        val popupAd = AdropPopupAd(this, "YOUR_UNIT_ID").apply {
            popupAdListener = object : AdropPopupAdListener {
                override fun onAdReceived(adropPopupAd: AdropPopupAd) {
                    // 로드 완료 즉시 표시
                    adropPopupAd.show(this@PopupAdActivity)
                }
                
                override fun onAdFailedToReceive(adropPopupAd: AdropPopupAd, adropErrorCode: AdropErrorCode) {
                    Log.e("adrop", "팝업 광고 로드 실패: $adropErrorCode")
                }
            }
            
            closeListener = object : AdropPopupAdCloseListener {
                override fun onClosed(ad: AdropPopupAd) {
                    // 팝업 광고 닫힌 후 정리
                    ad.destroy()
                }
            }
        }
        popupAd.load()
    }
}
```

**지원 위치 및 형태:**
- 하단/중앙 팝업
- 일반/16:9/9:16 동영상 팝업

### 스플래시 광고 (Splash Ads)
스플래시 광고는 앱 시작 시 자동으로 표시됩니다. `AndroidManifest.xml`에 다음 설정이 필요합니다:

```xml
<activity
    android:name="io.adrop.ads.splash.AdropSplashAdActivity"
    android:exported="true"
    android:theme="@style/Theme.App.SplashTheme">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

## 🔧 Unit ID 설정 가이드

### Unit ID 생성 방법
1. [Adrop 콘솔](https://adrop.io) → 광고 단위 메뉴
2. "광고 단위 생성" 클릭
3. 광고 유형 선택 (Banner, Interstitial, Native 등)
4. 광고 단위 이름 입력
5. Unit ID 복사하여 코드에 적용

### 테스트 Unit ID 예시
```kotlin
// 배너 광고
const val BANNER_320_50 = "PUBLIC_TEST_UNIT_ID_320_50"
const val BANNER_VIDEO_16_9 = "PUBLIC_TEST_UNIT_ID_BANNER_VIDEO_16_9"

// 팝업 광고
const val POPUP_BOTTOM = "PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM"
const val POPUP_CENTER_VIDEO = "PUBLIC_TEST_UNIT_ID_POPUP_CENTER_VIDEO_16_9"

// 전면/보상형 광고
const val INTERSTITIAL = "PUBLIC_TEST_UNIT_ID_INTERSTITIAL"
const val REWARDED = "PUBLIC_TEST_UNIT_ID_REWARDED"

// 네이티브 광고
const val NATIVE = "PUBLIC_TEST_UNIT_ID_NATIVE"
const val NATIVE_VIDEO = "PUBLIC_TEST_UNIT_ID_NATIVE_VIDEO_16_9"
```

## ⚠️ 핵심 개발 가이드라인

### 1. 필수 생명주기 관리
```kotlin
override fun onDestroy() {
    super.onDestroy()
    
    // 반드시 모든 광고 객체 정리
    bannerAd?.destroy()
    interstitialAd?.destroy()
    rewardedAd?.destroy()
    nativeAd?.destroy()
    popupAd?.destroy()
    
    // 참조 해제로 메모리 누수 방지
    bannerAd = null
    // ... 모든 광고 객체 null 처리
}
```

### 2. 에러 처리 필수 구현
```kotlin
override fun onAdFailedToReceive(ad: AdropBanner, errorCode: AdropErrorCode) {
    when (errorCode) {
        AdropErrorCode.NETWORK_ERROR -> {
            // 네트워크 오류 - 재시도 로직
            Handler(Looper.getMainLooper()).postDelayed({ 
                ad.load() 
            }, 3000)
        }
        AdropErrorCode.NO_FILL -> {
            // 광고 없음 - 대체 콘텐츠 표시
            showAlternativeContent()
        }
        AdropErrorCode.INVALID_REQUEST -> {
            // Unit ID 오류 - 설정 확인 필요
            Log.e("adrop", "Unit ID 확인 필요: ${ad.unitId}")
        }
        else -> {
            Log.e("adrop", "광고 로드 실패: $errorCode")
        }
    }
}
```

### 3. 성능 최적화
```kotlin
class OptimizedAdLoading {
    
    // ✅ 적절한 타이밍에 로드
    private fun loadAdsAtRightTime() {
        // 앱 완전 초기화 후 광고 로드
        Handler(Looper.getMainLooper()).postDelayed({
            loadInterstitialAd()
        }, 2000)
    }
    
    // ✅ 백그라운드 진입 시 로딩 중단
    override fun onPause() {
        super.onPause()
        cancelAdLoading()
    }
    
    // ❌ 피해야 할 패턴: 동시 다중 로드
    private fun avoidThis() {
        // 한 번에 너무 많은 광고 로드 시 성능 저하
        for (i in 1..10) {
            AdropBanner(this, "unit_$i", "").load() // 피하세요
        }
    }
}
```

### 4. Context ID 활용 (선택사항)
```kotlin
// 사용자 행동/속성 기반 광고 개인화
val contextId = when {
    userLevel >= 10 -> "high_level_user"
    userPurchased -> "paying_user"
    else -> "new_user"
}

val bannerAd = AdropBanner(this, unitId, contextId)
```

### 5. 개발/운영 환경 분리
```kotlin
class AdConfiguration {
    companion object {
        fun initializeAdrop(application: Application) {
            val isProduction = !BuildConfig.DEBUG
            Adrop.initialize(application, isProduction, arrayOf())
            
            if (BuildConfig.DEBUG) {
                Log.d("adrop", "개발 모드로 SDK 초기화")
            }
        }
    }
}
```

## 🔍 디버깅 및 테스트

### 로그 확인
```bash
# ADB를 통한 광고 관련 로그 모니터링
adb logcat | grep -i "adrop"
```

### 자주 발생하는 문제와 해결방법

**문제: 광고가 표시되지 않음**
```kotlin
// 체크리스트
private fun debugAdIssues() {
    // 1. 네트워크 연결 확인
    if (!isNetworkAvailable()) {
        Log.e("adrop", "네트워크 연결 없음")
        return
    }
    
    // 2. SDK 초기화 확인
    if (!Adrop.isInitialized()) {
        Log.e("adrop", "SDK 초기화되지 않음")
        return
    }
    
    // 3. Unit ID 유효성 확인
    if (unitId.isEmpty() || unitId == "YOUR_UNIT_ID") {
        Log.e("adrop", "유효하지 않은 Unit ID")
        return
    }
    
    // 4. adrop_service.json 파일 확인
    if (!checkServiceJsonExists()) {
        Log.e("adrop", "adrop_service.json 파일이 assets 폴더에 없음")
        return
    }
}
```

**문제: 메모리 누수**
```kotlin
// 해결: WeakReference 사용
class SafeAdActivity : AppCompatActivity() {
    private var adReference: WeakReference<AdropBanner>? = null
    
    private fun createAd() {
        val ad = AdropBanner(this, unitId, "")
        adReference = WeakReference(ad)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        adReference?.get()?.destroy()
        adReference = null
    }
}
```

## 📋 체크리스트

### 개발 완료 전 필수 확인사항
- [ ] SDK 초기화가 Application Context로 되어 있는가?
- [ ] 모든 광고 유형에 대해 onDestroy()에서 destroy() 호출하는가?
- [ ] 에러 처리 로직이 구현되어 있는가?
- [ ] Unit ID가 하드코딩되지 않고 설정으로 관리되는가?
- [ ] 운영 환경에서 production=true로 설정되는가?
- [ ] adrop_service.json 파일이 assets 폴더에 포함되는가?

### 성능 최적화 확인사항
- [ ] 불필요한 중복 로드가 없는가?
- [ ] 백그라운드 진입 시 로딩이 중단되는가?
- [ ] 적절한 타이밍에 광고 로드가 시작되는가?

## 📞 지원 및 문의

### 공식 리소스
- **개발자 문서**: [https://help.adrop.io](https://help.adrop.io)
- **API 레퍼런스**: [https://help.adrop.io/publisher-guide](https://help.adrop.io/publisher-guide)
- **예시 프로젝트**: 본 저장소의 코드 참조

### FAQ

**Q: 실제 수익은 언제부터 발생하나요?**
A: production=true로 설정하고 실제 Unit ID를 사용할 때부터 수익이 발생합니다.

**Q: 광고 로드 실패 시 재시도는 어떻게 하나요?**
A: exponential backoff 패턴 권장 (3초 → 6초 → 12초 간격)

**Q: 한 화면에 여러 배너 광고를 표시할 수 있나요?**
A: 가능하지만 사용자 경험과 정책을 고려하여 적절히 배치하세요.

---

**이 가이드는 Adrop SDK v1.4.0 기준으로 작성되었습니다.**
**최신 업데이트는 공식 문서를 확인해주세요.**