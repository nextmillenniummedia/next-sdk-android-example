# App Open ads

[Back to manual mode overview](https://github.com/nextmillenniummedia/next-sdk-android-example/blob/2.x/docs/manual/Manual.md)

App open ads are a special ad format intended for publishers wishing to monetize their app load
screens. App open ads can be closed at any time, and are designed to be shown when your users bring
your app to the foreground. To show app open ad we need to capture Started state.

The general scenario of App Open ad usage is:

1. Start App Open ad load
2. When user switches to another application `onStop` callback works.
3. When user returns back to application `onStart` callback works. At that moment we need to check
   if ad is ready to show. If yes — show it. If no — skip and wait to successful load.

We created our own `AppOpenAdObserver` according to modern Android techniques. As you know Google
views often change. We will keep it actual according to changes in `androidx.lifecycle` library. In
case when you have your own observer you can modify it by adding our techniques. Source code of our
observer will be given below at the end of the article.

For using App Open ads it is needed to
use [lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) based libraries.
We built our work around
[ProcessLifecycleOwner](https://developer.android.com/reference/android/arch/lifecycle/ProcessLifecycleOwner)
so we included `process` and `runtime` libraries version `2.5.1`

Prefer to load app open ad in main application class. App Open ad will not load again and again
after every load. But in case when you don't create new provider and observer every time on every
activity/fragment load. If you can't load in main `App` class prefer to create one provider and use
it with `ViewModel`. We added all necessary checks in our `AppOpenAdObserver`. But creating new
instance of `AppOpenAdObserver` and adding it to `ProcessLifecycleOwner` is dangerous.

To show app open ad in manual mode you need to create an instance of `AppOpenAdProvider`
with passed application context and unit id:

```kotlin
val provider = AppOpenAdProvider(context, "106")
```

| param | description
| --- | --- | 
| `context` | application context | 
| `unitId` | unit id in our system | 

Simple app open ad load will be look like:

<details>
<summary>Java</summary>

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdk.initialize(this);
        loadAppOpenAd();
    }

    private void loadAppOpenAd() {
        AppOpenAdProvider provider = new AppOpenAdProvider(this, "110");
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppOpenAdObserver(provider));
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
        NextSdk.initialize(this)
        loadAppOpen()
    }

    private fun loadAppOpen() {
        val provider = AppOpenAdProvider(this, "110")
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppOpenAdObserver(provider))
    }
}
```

</details>

You can also pass unit id with `setUnitId(String)`:

<details>
<summary>Java</summary>

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdk.initialize(this);
        loadAppOpenAd();
    }

    private void loadAppOpenAd() {
        AppOpenAdProvider provider = new AppOpenAdProvider(this);
        provider.setUnitId("110");
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppOpenAdObserver(provider));
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
        NextSdk.initialize(this)
        loadAppOpen()
    }

    private fun loadAppOpen() {
        val provider = AppOpenAdProvider(this)
        provider.unitId = "110"
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppOpenAdObserver(provider))
    }
}
```

</details>

### Customize ad lifecycle events

Use `AppOpenAdListener` to provide your customized behavior for successful and failure banner loads.
You can pass it by `setListener(AppOpenAdListener)` method in `AppOpenAdProvider`.

| overridable methods | description |
| --- | --- |
| `onAdLoaded(NextAppOpenAd)` | Called when an ad successfully loads |
| `onAdImpression` | Called when an impression is recorded for an ad. |
| `onAdClicked` | Called when a click is recorded for an ad. |
| `onFullScreenAdDeclined` | Called when the ad dismissed full screen content. |
| `onFullScreenAdShowed` | Called when the ad showed the full screen content. |
| `onFullScreenAdLoadFail(NextAdError)` | Called when the ad failed to show or load full screen content. |
| `onError(Throwable)` | Called when an unexpected error occurred while handling fullscreen ad |

Usage of all listener events

<details>
<summary>Java</summary>

```java
public class App extends Application implements AppOpenAdListener {

    @Override
    public void onCreate() {
        super.onCreate();
        NextSdk.initialize(this);
        loadAppOpenAd();
    }

    private void loadAppOpenAd() {
        AppOpenAdProvider provider = new AppOpenAdProvider(this, "110");
        provider.setListener(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppOpenAdObserver(provider));
    }
    
    @Override
    public void onAdLoaded(NextAppOpenAd nextAppOpenAd) {
        // some action
    }

    @Override
    public void onAdImpression() {
        // some action
    }

    @Override
    public void onAdClicked() {
        // some action
    }

    @Override
    public void onFullScreenAdDeclined() {
        // some action
    }

    @Override
    public void onFullScreenAdShowed() {
        // some action
    }

    @Override
    public void onFullScreenAdLoadFail(NextAdError loadError) {
        // some action
    }

    @Override
    public void onError(Throwable error) {
        // some action
    }
}
```
</details>
<details>
<summary>Kotlin</summary>

```kotlin
class App : Application(), AppOpenAdListener {
    override fun onCreate() {
        super.onCreate()
        NextSdk.initialize(this)
        loadAppOpen()
    }

    private fun loadAppOpen() {
        val provider = AppOpenAdProvider(this, "110")
        provider.setListener(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppOpenAdObserver(provider))
    }

    override fun onAdLoaded(ad: NextAppOpenAd?) {
        // some action
    }

    override fun onAdImpression() {
        // some action
    }

    override fun onAdClicked() {
        // some action
    }

    override fun onFullScreenAdDeclined() {
        // some action
    }

    override fun onFullScreenAdShowed() {
        // some action
    }

    override fun onFullScreenAdLoadFail(loadError: NextAdError?) {
        // some action
    }

    override fun onError(error: Throwable?) {
        // some action
    }
}
```
</details>

Our implementation of observer:

`AppOpenAdObserver.java`

```java
public class AppOpenAdObserver implements Application.ActivityLifecycleCallbacks, DefaultLifecycleObserver {

    AppOpenAdProvider provider;

    public AppOpenAdObserver(AppOpenAdProvider provider) {
        this.provider = provider;
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        provider.setActivity(activity);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        provider.setActivity(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        provider.setActivity(null);
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        provider.prepare();
    }

}
```