Adrop-Ads-Android
===

&nbsp;

Prerequisites
-------------
* Install or update [Android Studio](https://developer.android.com/studio) to its latest version.
* Make sure that your project meets these requirements:
  * Targets API level 23 (M) or higher
  * Uses Android 6.0 or higher
    * minSdkVersion 23
* Uses [Jetpack (AndroidX)](https://developer.android.com/jetpack/androidx/migrate), which includes meeting these version requirements:
  * ```com.android.tools.build:gradle``` v7.4.2 or later
  * ```compileSdkVersion``` 34
* Kotlin 1.8.20 or higher
* [Sign into Adrop](https://adrop.io) using your email or Google account.

---

&nbsp;

Add Adrop using the Adrop console
---
Adding Adrop to your app involves tasks both in the [Adrop console](https://adrop.io) and in your open Android project (for example, you download Adrop config files from the console, then move them into your Android project).

### Step 1: Create a Adrop project

Before you can add Adrop to your Android app, you need to [create a Adrop project](https://help.adrop.io/publisher-guide/start-ads-platform) to connect to your Android app.

### Step 2: Register your app with Adrop

To use Adrop in your Android app, you need to register your app with your Adrop project. Registering your app is often called "adding" your app to your project.

> **Note**
> 
> Make sure to enter the package name that your app is actually using. The package name value is case-sensitive, and it cannot be changed for this Adrop Android app after it's registered with your Adrop project.

1. Go to the [Adrop console](http://adrop.io).
2. In the center of the project app page, click the **Android** icon button to launch the setup workflow.
3. Enter your app's package name in the **Android package name** field
   * A [package name](https://developer.android.com/studio/build/application-id) uniquely identifies your app on the device and in the Google Play Store
   * A package name is often referred to as an application ID
   * Find your app's package name in your module (app-level) Gradle file, usually ```app/build.gradle``` (example package name: ```com.yourcompany.yourproject```)
   * Be aware that the package name value is case-sensitive, and it cannot be changed for this Firebase Android app after it's registered with your Firebase project
4. Enter other app information: **App nickname**
   * **App nickname**: An internal, convenience identifier that is only visible to you in the Firebase console
5. Click **Register app**

### Step 3: Add a Adrop configuration file

1. Download **adrop_service.json** to obtain your Adrop Android platforms config file
2. Move your config file into your assets directory
    * ```android/app/src/main/assets/adrop_service.json ```

### Step 4: Add Adrop SDK to your app

1. In your **module (app-level) Gradle file** (usually ```<project>/build.gradle.kts``` or ```<project>/build.gradle```), add the dependency for the Adrop in your app.

    Kotlin (build.gradle.kts)
    ```
    implementation("io.adrop:adrop-ads:1.3.10")
    ```

    Groovy (build.gradle)
    ```
    implementation "io.adrop:adrop-ads:1.3.10"
    ```

2. After adding the dependency, sync your Android project with Gradle files.

### Step 5: Initialize Adrop in your app

1. Import the ```Adrop```
2. Initialize a Adrop instance with your app's Application Context

    #### Kotlin
    ```kt
    import io.adrop.ads.Adrop
    // ..
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // 1. use Application Context
        // 2. true for production
        // 3. If you are using this SDK in specific countries, 
        //    pass an array of ISO 3166 alpha-2 country codes.
        Adrop.initialize(application, production = false, arrayOf())
    }
    ```
    
    #### Java
    ```java
    import io.adrop.ads.Adrop;
    // ..
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 1. use Application Context
        // 2. true for production
        // 3. If you are using this SDK in specific countries, 
        //    pass an array of ISO 3166 alpha-2 country codes.
        boolean production = true;
        String[] targetCountries = {};
        Adrop.INSTANCE.initialize(getApplication(), production, targetCountries);
    }
    ```

&nbsp;

---

&nbsp;

Create your Ad unit
---

### 

From the [Adrop console](https://adrop.io), go to project, then select Ad unit in the left navigation menu to create and manage Ad units. Ad units are containers you place in your apps to show ads to users. Ad units send ad requests to Adrop, then display the ads they receive to fill the request. When you create an ad unit, you assign it an ad format and ad type(s).

---


### Creating Ad units
To create a new Ad unit:
1. From the left navigation menu, select **Ad Units**.
2. Select **Create Ad unit** to bring up the ad unit builder.
3. Enter an Ad unit name, then select your app (iOS or Android) and [Ad format](https://help.adrop.io/publisher-guide/ads-builder) (Banner, Interstitial, Rewarded, Native, Popup, or Splash).
4. Select **Create** to save your Ad unit.

### Ad unit ID
The Ad unit’s unique identifier to reference in your code. This setting is read-only.

> **Note** These are unit ids for test
> * PUBLIC_TEST_UNIT_ID_320_50
> * PUBLIC_TEST_UNIT_ID_375_80
> * PUBLIC_TEST_UNIT_ID_320_100
> * PUBLIC_TEST_UNIT_ID_CAROUSEL
> * PUBLIC_TEST_UNIT_ID_INTERSTITIAL
> * PUBLIC_TEST_UNIT_ID_REWARDED
> * PUBLIC_TEST_UNIT_ID_NATIVE
> * PUBLIC_TEST_UNIT_ID_POPUP_BOTTOM
> * PUBLIC_TEST_UNIT_ID_POPUP_CENTER
> * PUBLIC_TEST_UNIT_ID_SPLASH
