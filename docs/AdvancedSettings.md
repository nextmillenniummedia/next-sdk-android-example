
# Advanced Settings
[Back to repo](https://github.com/nextmillenniummedia/next-sdk-android-example/tree/main)

**Crash reports**


Our objective is to enhance the performance of our SDK, and therefore, we gather crash statistics which encompass the exceptions that occur within the SDK. If desired, there is an option to disable this functionality.
```kotlin
NextSdk.disableCrashReports()
```

The best approach would be to enable crash reports exclusively for the production environment. 
You can achieve this by adding the following code snippet:

```kotlin
if (BuildConfig.DEBUG) {
    NextSdk.disableCrashReports()
}
```

In application class:

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            NextSdk.disableCrashReports()
        }
        NextSdk.initialize(this, true)
    }

}
```

We store the following information:

* Package name.
* Versions of the SDK and Android. 
* Phone model.
* Error details, including the message, code, description, and stack trace.
* Timestamp when the exception occurred.
* Responses obtained from our backend API.
* Debug information regarding ad refresh but only if the `NextSdk.isCollectingRefreshStatistics` is enabled

**Logging**

To access the logs of the library, you need to enable them by following these steps:

```kotlin
NextSdk.enableLogging()
```

<details>

<summary>Java</summary>

```java
import io.nextmillennium.nextsdk.NextBannerView;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdk.enableLogging();
        NextSdk.initialize(this, true);
    }
}
```

</details>

<details>
<summary>Kotlin</summary>

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NextSdk.enableLogging()
        NextSdk.initialize(this, true)
    }

}
```

</details>

Additionally, you have the option to enable logging for ads refreshing by invoking
`NextSdk.isCollectingRefreshStatistics = true`

**SDK Modularization**

Our main module contains modules with all needed ad formats and dynamic mode. If you don't need
injection/dynamic mode and you need only custom ad unit configuration, you can use our ads with all
appropriate modules without main module.

With the modular SDK, you can choose to include specific formats to decrease overall SDK footprint
in your app. To do so, include the line for any combination of components that you want in
your `build.gradle` file as follows:

```gradle
dependencies {
    // ... other project dependencies

    // For banners and native ads
    implementation('io.nextmillennium:nextsdk-base:2.3.0')

    // For interstitials, rewarded and app open ads
    implementation('io.nextmillennium:nextsdk-fullscreen:2.3.0')

}
```

**Visibility of banner in content**

Occasionally, banners may fail to load due to various reasons. 
By default, we create a transparent container for the banner. 
If your application has a special container for banners, there should be no issues. 
However, if the banner is loaded within the content, it can result in empty space as depicted in the image below:

<p align="center">
<img src="https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/assets/empty_space.png" height="720">
</p>

To prevent this behavior use

```java 
bannerView.setCollapsible(true);
```

As a result, your article will maintain its integrity and avoid any visual disruptions.

<p align="center">
<img src="https://github.com/nextmillenniummedia/next-sdk-android-example/blob/main/docs/assets/collapsed.png" height="720">
</p>
