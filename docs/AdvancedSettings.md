
# Advanced Settings
[Back to repo](https://github.com/nextmillenniummedia/next-sdk-android-example/tree/2.x)

**Logging**

To see logs of library enable them:

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

**SDK Modularization**

Our main module contains modules with all needed ad formats and dynamic mode. If you don't need
injection/dynamic mode and you need only custom ad unit configuration, you can use our ads with all
appropriate modules without main module.

With the modular SDK, you can choose to include specific formats to decrease overall SDK footprint
in your app. To do so, include the line for any combination of components that you want in
your `build.gradle` file as follows:

```groovy
dependencies {
    // ... other project dependencies

    // For banners
    implementation('io.nextmillennium:nextsdk-banner:2.0.0')

    // For interstitials, rewarded and app open ads
    implementation('io.nextmillennium:nextsdk-fullscreen:2.0.0')

    // For native ads (since 2.2.0)
    implementation('io.nextmillennium:nextsdk-native:2.2.0')
}
```

**Visibility of banner in content**

Sometimes banner don't load for some reasons. By default we create transparent container for banner.
If you have special container for banner in your application there will be no problems. But in case
when it loads in content it can lead to empty space. See image below:

<p align="center">
<img src="https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/assets/empty_space.png" height="720">
</p>

To prevent this behavior use

```java 
bannerView.setCollapsible(true);
```

Thus your article won't look broken.

<p align="center">
<img src="https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/assets/collapsed.png" height="720">
</p>
